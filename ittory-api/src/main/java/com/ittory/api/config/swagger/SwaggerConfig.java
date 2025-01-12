package com.ittory.api.config.swagger;


import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.GlobalOperationCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
@OpenAPIDefinition(info = @Info(title = "Ittory", description = "Ittory API 명세서", version = "v1"),
        servers = {@Server(url = "https://dev-server.ittory.co.kr", description = "Dev 도메인"),
                @Server(url = "/", description = "기본")})
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {

            this.addSecurityRequirement(operation); // 보안 요구사항 추가
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

    private void addSecurityRequirement(Operation operation) {
        if (operation.getSecurity() == null || operation.getSecurity().isEmpty()) {
            operation.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                    .addList("bearerAuth"));
        }
    }

}

