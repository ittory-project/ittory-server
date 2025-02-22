package com.ittory.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.persister.entity.EntityPersister;

import java.util.*;

@Slf4j
public class LazyLoadingDetector implements LoadEventListener {
    private static final ThreadLocal<Map<String, Integer>> nPlusOneCounter = ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<Set<String>> initialLoadTracker = ThreadLocal.withInitial(HashSet::new);

    @Override
    public void onLoad(LoadEvent event, LoadType loadType) {
        String entityClassName = event.getEntityClassName();
        PersistenceContext persistenceContext = event.getSession().getPersistenceContext();
        EntityPersister entityPersister = event.getSession().getEntityPersister(entityClassName, null);
        EntityKey entityKey = new EntityKey(event.getEntityId(), entityPersister);

        // 현재 실행 중인 스택 트레이스 분석
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean isLazyInitialization = isLazyInitialization(stackTrace);

        // 영속성 컨텍스트에 엔티티가 없고, 지연 로딩으로 인한 초기화인 경우에만 카운트
        Object entity = persistenceContext.getEntity(entityKey);
        if (entity == null && isLazyInitialization) {
            // 초기 로딩이 아닌 경우에만 N+1 카운트 증가
            if (!isInitialLoad(entityClassName)) {
                incrementNPlusOneCount(entityClassName);
//                log.warn("N+1 Query detected for entity: {} (Count: {})",
//                        entityClassName,
//                        getNPlusOneCount().get(entityClassName));
            }
        }
    }

    private boolean isLazyInitialization(StackTraceElement[] stackTrace) {
        return Arrays.stream(stackTrace)
                .anyMatch(element ->
                        element.getClassName().contains("org.hibernate.proxy") ||
                                element.getClassName().contains("org.hibernate.collection.internal"));
    }

    private void incrementNPlusOneCount(String entityClassName) {
        Map<String, Integer> counts = getNPlusOneCount();
        counts.merge(entityClassName, 1, Integer::sum);
    }

    private boolean isInitialLoad(String entityClassName) {
        Set<String> initialLoads = initialLoadTracker.get();
        if (!initialLoads.contains(entityClassName)) {
            initialLoads.add(entityClassName);
            return true;
        }
        return false;
    }

    public static Map<String, Integer> getNPlusOneCount() {
        return nPlusOneCounter.get();
    }

    public static void resetNPlusOneCount() {
        nPlusOneCounter.remove();
        initialLoadTracker.remove();
    }
}




