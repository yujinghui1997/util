package com.yjh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "com.yjh.cros")
public class MyCrosFilterProperties {

    private Boolean cros = true;

    public Boolean getCros() {
        return cros;
    }

    public void setCros(Boolean cros) {
        this.cros = cros;
    }
}
