package com.heaven.website.shopping.manager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用户获取用户信息
 * @author xiaoxi.li
 * @date 2019/03/21 16:25
 * @description
 *
 * @see WebSecurityConfiguration
 */
@Component
public class ExampleUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		Collection<GrantedAuthority> authorities = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + username);
		authorities.add(grantedAuthority);

		return new ExampleUserDetail(username, username, authorities);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ExamplePasswordEncoder();
	}


	public class ExampleUserDetail extends User {


		public ExampleUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}
	}

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
