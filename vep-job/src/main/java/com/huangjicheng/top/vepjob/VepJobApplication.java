package com.huangjicheng.top.vepjob;


import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(groupId = "xxl-job", dataId = "vep-job", autoRefreshed = true, type = ConfigType.YAML)
@EnableDubbo
public class VepJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(VepJobApplication.class, args);
    }

}
