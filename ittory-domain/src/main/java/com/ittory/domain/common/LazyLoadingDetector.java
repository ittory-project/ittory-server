package com.ittory.domain.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.spi.EntityKey;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.persister.entity.EntityPersister;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LazyLoadingDetector implements LoadEventListener {

    private static final ThreadLocal<Map<String, Integer>> nPlusOneCounter = ThreadLocal.withInitial(HashMap::new);

    @Override
    public void onLoad(LoadEvent event, LoadType loadType) {
        PersistenceContext persistenceContext = event.getSession().getPersistenceContext();

        // EntityKey 생성
        EntityPersister entityPersister = event.getSession().getEntityPersister(event.getEntityClassName(), null);
        EntityKey entityKey = new EntityKey(event.getEntityId(), entityPersister);

        // 영속성 컨텍스트에서 엔티티 검색
        Object entity = persistenceContext.getEntity(entityKey);

        if (entity == null) { // 엔티티가 영속성 컨텍스트에 없으면 (DB에서 로드 발생)
            if (getNPlusOneCount().containsKey(event.getEntityClassName())) {
                Integer nowCount = getNPlusOneCount().get(event.getEntityClassName());
                getNPlusOneCount().put(event.getEntityClassName(), nowCount + 1);
            } else {
                getNPlusOneCount().put(event.getEntityClassName(), 1);
            }
        }
    }

    // N+1 카운터 값을 가져오는 메서드
    public static Map<String, Integer> getNPlusOneCount() {
        return nPlusOneCounter.get();
    }

    // N+1 카운터를 초기화하는 메서드
    public static void resetNPlusOneCount() {
        nPlusOneCounter.remove();
    }
}




