package com.heaven.website.shopping.manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;


/**
 * 进行安全性相关配置
 * @author xiaoxi.li
 * @date 2019/03/21 14:38
 * @description
 */
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/hasAdmin").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
			.and()
				.formLogin()
//				.loginPage("/sys/login.html").permitAll()
//				.loginProcessingUrl("/sys/login").permitAll()
			.and()
//				.csrf()
//					.disable()
			.httpBasic();
	}


}
