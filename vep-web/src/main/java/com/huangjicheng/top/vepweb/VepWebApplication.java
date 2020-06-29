package com.huangjicheng.top.vepweb;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDubbo
@EnableSwagger2
public class VepWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(VepWebApplication.class, args);
    }

}
