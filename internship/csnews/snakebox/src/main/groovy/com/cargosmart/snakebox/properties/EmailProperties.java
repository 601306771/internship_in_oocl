package com.cargosmart.snakebox.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {

    public String from;
    public String[] alertTo;
    public String alertSubject;
}
