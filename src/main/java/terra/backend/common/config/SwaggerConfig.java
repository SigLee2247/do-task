package terra.backend.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().components(new Components()).info(apiInfo());
  }

  @Bean
  public GroupedOpenApi totalApi() {
    return GroupedOpenApi.builder()
        .pathsToMatch("/api/**")
        .group("F0")
        .displayName("CPU USAGE")
        .build();
  }

  private Info apiInfo() {
    return new Info()
        .title("CPU 성능 측정 API 명세서")
        .description("CPU의 사용량을 일 / 시간 / 분 단위로 조회 할 수 있다.")
        .contact(new Contact().email("siglee2247@gmail.com").name("dev-store BE developer"))
        .version("1.0.0");
  }
}
