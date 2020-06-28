package com.huangjicheng.top.vepweb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: huangjicheng
 * @Date: 2020/6/7 13:08
 * @Desc 读取配置文件
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload")
@PropertySource(value = "classpath:application.properties")
public class ConfigBeanProp {

    private String path;

}