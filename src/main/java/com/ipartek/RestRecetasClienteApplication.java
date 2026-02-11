package com.ipartek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class RestRecetasClienteApplication extends SpringBootServletInitializer {

    // Este método es fundamental para que el servidor (Tomcat) pueda arrancar el cliente
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RestRecetasClienteApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RestRecetasClienteApplication.class, args);
    }

}