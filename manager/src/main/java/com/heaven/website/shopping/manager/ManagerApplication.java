package com.heaven.website.shopping.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * 启动类
 * @author li.xiaoxi
 */
@EnableFeignClients	//开启负载均衡
@EnableDiscoveryClient	// 开启发现服务客户端模式
@SpringBootApplication
public class ManagerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

	@Bean
	public HttpMessageConverter httpMessageConverter() {
		return new FormHttpMessageConverter();
	}
}
