package com.yunwa.taobaoke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = {"com.yunwa.taobaoke.dao"})
@SpringBootApplication
public class TaobaokeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaobaokeApplication.class, args);
    }

}
