package com.heaven.website.shopping.discovery.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 安全配置
 * @author li.xiaoxi
 * @date 2019/03/26 14:18
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				// 全部请求都需要认证
				.anyRequest().authenticated()
			.and()
				// 开启页面登录
				.formLogin()
			.and()
				// 忽略对/eureka/**的csrf验证 TODO 理论上来说可以开启，等待服务端实现
				.csrf().ignoringAntMatchers("/eureka/**")
			.and()
				// 开启HTTP Basic authentication登陆模式
				.httpBasic()
			.and()
				// 设置UserDetailsService
				.userDetailsService(getUserDetailsService());
	}


	/**
	 * 注入密码编码器
	 * <p>TODO 需要企业级实现</p>
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return rawPassword.equals(encodedPassword);
			}
		};
	}


	/**
	 * 获取UserDetailsService
	 * <p>TODO 需要企业级实现</p>
	 */
	private UserDetailsService getUserDetailsService() {
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(User.withUsername("user").password("user").roles("test").build());
		return  userDetailsManager;
	}

}
