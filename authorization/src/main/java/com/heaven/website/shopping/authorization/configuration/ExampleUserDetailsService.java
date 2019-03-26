package com.heaven.website.shopping.authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用户信息源service
 * <p>主要用于加载用户信息与密码匹配校验等</p>
 *
 * TODO 需要动态化
 * TODO 因为作为Component注入到spring上下文中。可能会出现被意外注入到别的Bean中的情况
 * TODO 需要解决角色与权限获取问题
 * @author li.xiaoxi
 * @date 2019/03/22 14:08
 *
 * @see org.springframework.security.provisioning.JdbcUserDetailsManager
 */
@Component
public class ExampleUserDetailsService implements UserDetailsService {

	/**
	 * 根据用户名加载用户信息
	 * TODO 需要修改为产品级实现
	 *
	 * @param username	不能为空
	 * @return	有用户信息时不应为空
	 * @throws UsernameNotFoundException	无法根据用户信息找到用户时，抛出此异常
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + username);
		authorities.add(grantedAuthority);

		return new ExampleUserDetail(username, username, authorities);
	}


	/**
	 * 注入密码解析器
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ExamplePasswordEncoder();
	}


	/**
	 * 对User的扩展。考虑添加更多信息
	 */
	public class ExampleUserDetail extends User {


		public ExampleUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}
	}


	/**
	 * 自定义的密码编码器
	 * TODO 需要修改为企业级实现
	 */
	public class ExamplePasswordEncoder implements PasswordEncoder{

		@Override
		public String encode(CharSequence rawPassword) {
			return rawPassword.toString();
		}

		@Override
		public boolean matches(CharSequence rawPassword, String encodedPassword) {
			return rawPassword.equals(encodedPassword);
		}
	}
}
