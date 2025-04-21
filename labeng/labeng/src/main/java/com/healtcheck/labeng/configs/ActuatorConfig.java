package com.healtcheck.labeng.configs;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ActuatorConfig {
    @Bean
    public InfoContributor getInfoContributor() {
        return new InfoContributor() {
            @Override
            public void contribute(Info.Builder builder) {
                Map<String, Object> details = new HashMap<>();
                details.put("name", "Health Check API");
                details.put("description", "Sistema de monitoramento de doenças");
                details.put("version", "1.0.0");

                Map<String, Object> contact = new HashMap<>();
                contact.put("team", "Health Check Team - Gabriel, Francisco, Luigi e Welder");

                details.put("contact", contact);

                builder.withDetail("application", details);
            }
        };
    }
}