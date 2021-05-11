package ru.itis.web.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itis.api.configs.ApiConfiguration;
import ru.itis.impl.configs.ImplConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({ImplConfiguration.class, ApiConfiguration.class})

public class WebConfiguration {

    @Bean
    public List<String> forConfirmedOnlyUris() {
        List<String> list = new ArrayList<>();
        list.add("/home");
        list.add("/boards");
        return list;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.itis.web.controllers.rest"))
                .paths(PathSelectors.any())
                    .build();
    }

}
