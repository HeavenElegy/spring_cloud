package com.heaven.website.shopping.resource.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器相关配置
 * <p>TODO 后期将配置放入这个类中。现在都在yml文件里</p>
 * @author li.xiaoxi
 * @date 2019/03/22 16:10
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

//	@Autowired
//	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication()
//				.withUser("user").password("user").roles("ROLE");
//	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//				.anyRequest().authenticated()
//			.and()
//				.formLogin()
//			.and()
//				.httpBasic();
//	}

}
