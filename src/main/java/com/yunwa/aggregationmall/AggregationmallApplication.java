package com.yunwa.aggregationmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(basePackages = {"com.yunwa.aggregationmall.dao"})
@SpringBootApplication
@EnableScheduling
public class AggregationmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationmallApplication.class, args);
    }

}
