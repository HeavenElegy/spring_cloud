package com.heaven.website.shopping.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 启动Spring Security
 * <p>对于{@link EnableWebSecurity @EnableWebSecurity}注解。因为这个项目已经添加了starter-security依赖，所以不
 * 再需要。只需要使用{@link Configuration @Configuration}注解将此类注入Spring容器即可即可</p>
 * <p>这里还是使用了{@link EnableWebSecurity @EnableWebSecurity}注解。用于开启Security的Debug模式</p>
 *
 * @author li.xiaoxi
 * @date 2019/03/20 10:44
 * @description
 */
//@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//					.anyRequest().authenticated()
//					.and()
//				.oauth2Login();
//
//	}

}
