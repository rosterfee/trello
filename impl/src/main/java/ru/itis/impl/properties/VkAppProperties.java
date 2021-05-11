package ru.itis.impl.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.vk.auth.client")
public class VkAppProperties {

    private String id;
    private String secret;
    private String redirect_uri;
    private String token_receive_url;
}
