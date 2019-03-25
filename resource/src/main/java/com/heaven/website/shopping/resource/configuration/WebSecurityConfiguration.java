// package com.heaven.website.shopping.resource.configuration;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
// /**
//  * @author xiaoxi.li
//  * @date 2019/03/22 13:58
//  * @description
//  */
// // @EnableWebSecurity(debug = true)
// public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
// 	@Autowired
// 	private ExampleUserDetailsService exampleUserDetailsService;
//
// 	@Override
// 	protected void configure(HttpSecurity http) throws Exception {
// 		http
// 			.authorizeRequests()
// 				.anyRequest().authenticated()
// 			.and()
// 				.httpBasic()
// 			.and()
// 				.formLogin()
// 			.and()
// 				.csrf().disable()
// 			.userDetailsService(exampleUserDetailsService);
// 	}
// }
