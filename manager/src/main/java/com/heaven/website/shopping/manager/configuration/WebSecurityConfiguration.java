package com.heaven.website.shopping.manager.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * 安全配置
 * TODO 需要能进行方法级，页面甚至数据级进行处理
 * @author li.xiaoxi
 * @date 2019/03/22 13:58
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ExampleUserDetailService exampleUserDetailService;


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				// 所有请求都需要认证
				.anyRequest().authenticated()
			.and()
				.formLogin()
//				.loginPage("/sys/login.html").permitAll()
//				.loginProcessingUrl("/sys/login").permitAll()
			.and()
				.httpBasic()
			.and()
				// 设置UserDetailService。这里并不需要，因为已经注入到了Spring上下文中了
				.userDetailsService(exampleUserDetailService);
	}


}
