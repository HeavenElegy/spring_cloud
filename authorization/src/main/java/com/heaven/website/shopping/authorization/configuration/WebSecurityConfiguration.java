package com.heaven.website.shopping.authorization.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.time.Period;

/**
 * 安全配置
 * TODO 需要能进行方法级，页面甚至数据级进行处理
 * @author li.xiaoxi
 * @date 2019/03/22 13:58
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ExampleUserDetailsService exampleUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// 授权请求
				.authorizeRequests()
					// 所有请求都需要认证
					.anyRequest().authenticated()
				.and()
					// 开启HTTP Basic authentication模式 TODO 作为页面服务时并不需要
					.httpBasic()
				.and()
					// 开启csrf验证
					.csrf()
				.and()
					// 开启SpringSecurity自带的登陆页面与登陆功能
					.formLogin()
				.and()
					// 设置UserDetailService。这里并不需要，因为已经注入到了Spring上下文中了
					.userDetailsService(exampleUserDetailsService);
	}
}
