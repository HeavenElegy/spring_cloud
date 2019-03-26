package com.heaven.website.shopping.authorization.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 授权服务配置
 *
 * @author li.xiaoxi
 * @date 2019/03/21 17:46
 * @description
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {


	/**
	 * 客户端信息服务配置
	 * <p>主要用于配置"访问者"数据源</p>
	 * <p><b>这里的访问者是指调用方而非C端客户。此项目中为manager端与resource端</b></p>
	 * <p>目前的配置方式。是使用HTTP Basic authentication方式给予客户机登陆功能的</p>
	 * @param clients	系统注入
	 * @throws Exception	顶层进行捕获
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		// TODO: 2019/3/26 数据来源需要动态化
		clients.inMemory()
				// 客户端id TODO 是否需要区别manager服务与resource服务呢？
				.withClient("client_id")
				// 客户端密码 TODO 同上
				.secret("666")
				// 自动授权。为false时，进行授权时会进行二次确认
				.autoApprove(false)
				// 授权类型 - 授权码 TODO 需要实例化其他类型
				.authorizedGrantTypes("authorization_code")
				// 访问范围 TODO 貌似在resource端并没有对此数据进行验证
				.scopes("test")
				// TODO 作用未知，需要确认
				.authorities("abc")
				// 重定向地址
				.redirectUris("http://localhost:9930/manager/example/authorization/code");
	}


	/**
	 * 安全配置
	 * @param security	系统注入
	 * @throws Exception	顶层进行捕获
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

		// 开启token验证功能，默认状态下为禁用全部。供resource端使用
		security.checkTokenAccess("permitAll()");
	}

	/**
	 * 端点配置
	 * @param endpoints	系统注入
	 * @throws Exception	顶层进行捕获
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	}

}
