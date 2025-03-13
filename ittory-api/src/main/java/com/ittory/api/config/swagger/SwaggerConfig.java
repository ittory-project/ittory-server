package com.ittory.api.config.swagger;


import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        io.swagger.v3.oas.models.info.Info info = new Info()
                .version("v1.0.0")
                .title("잇토리")
                .description("잇토리 Swagger");

        // SecuritySecheme명
        String jwtSchemeName = "AccessToken";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new io.swagger.v3.oas.models.security.SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional

        return new OpenAPI()
                .info(info)
                .addServersItem(new Server().url("/"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.addResponseBodyWrapper(operation);
            return operation;
        };
    }

    private void addResponseBodyWrapper(Operation operation) {
        Content content = operation.getResponses().get("200").getContent();
        if (content != null) {
            content.forEach((mediaTypeKey, mediaType) -> {
                Schema<?> originalSchema = mediaType.getSchema();
                Schema<?> wrappedSchema = wrapSchema(originalSchema);
                mediaType.setSchema(wrappedSchema);
            });
        }
    }

    private Schema<?> wrapSchema(Schema<?> originalSchema) {
        Schema<?> wrapperSchema = new Schema<>();

        wrapperSchema.addProperty("success", new Schema<>().type("boolean").example(true));
        wrapperSchema.addProperty("status", new Schema<>().type("integer").example(200));
        wrapperSchema.addProperty("data", originalSchema);

        return wrapperSchema;
    }

    @Bean
    public GlobalOperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            Arrays.stream(handlerMethod.getMethodParameters())
                    .filter(param -> param.hasParameterAnnotation(CurrentMemberId.class))
                    .forEach(param -> operation.getParameters().remove(param.getParameterIndex()));
            return operation;
        };
    }

}
