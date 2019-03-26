package com.heaven.website.shopping.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用于注入自定义的UserDetailService
 * @author li.xiaoxi
 * @date 2019/03/20 10:44
 * @description
 *
 * @see UserDetailsService
 */
//@Configuration
public class WebSecurityUserDetailServiceConfiguration {

	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withDefaultPasswordEncoder().username("root").password("root").roles("ADMIN").build());
		return manager;
	}


}
