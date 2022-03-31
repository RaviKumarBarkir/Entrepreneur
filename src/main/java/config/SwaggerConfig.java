package config;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@SuppressWarnings("PMD")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final int FIVE_HUNDRED = 500;
    private static final Logger LOG = LogManager.getLogger(SwaggerConfig.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/.*";

    @Value("${server.port}")
    private String serverPort;

    @Bean
    public Docket docketApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.dlt.xilo.Entrepreneur"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()))
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                        new ResponseMessageBuilder()
                                .code(FIVE_HUNDRED).message("500 message")
                                .responseModel(new ModelRef("Error")).build()));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Entrepreneur Service API .. ")
                .description("Business API to intiate Entrepreneur process for client onboarding.")
                .license("XILO 1.0")
                .licenseUrl("http://www.dltapps.co.uk/xilo/License.md")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("JWT", authorizationScopes));
    }
}


