package com.yunwa.aggregationmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.yunwa.aggregationmall.dao"})
@SpringBootApplication
public class AggregationmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregationmallApplication.class, args);
    }

}
