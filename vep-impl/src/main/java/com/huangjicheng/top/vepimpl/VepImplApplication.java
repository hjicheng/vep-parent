package com.huangjicheng.top.vepimpl;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan
@MapperScan("com.huangjicheng.top.vepdao.mapper")
public class VepImplApplication {

    public static void main(String[] args) {
        SpringApplication.run(VepImplApplication.class, args);
    }

}
