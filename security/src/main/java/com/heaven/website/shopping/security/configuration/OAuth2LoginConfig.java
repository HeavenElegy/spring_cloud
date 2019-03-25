package com.heaven.website.shopping.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * 配合{@link HttpSecurity#oauth2Login()} 进行页面OAuth2.0登陆
 * @author xiaoxi.li
 * @date 2019/03/20 16:56
 * @description
 */
@Configuration
public class OAuth2LoginConfig {

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {

		InMemoryClientRegistrationRepository ret = new InMemoryClientRegistrationRepository(this.facebookClientRegistration());

		return ret;
	}

	private ClientRegistration facebookClientRegistration() {
		return ClientRegistration.withRegistrationId("facebook")
				.clientId("2053264478123596")
				.clientSecret("319fd94c0bc9885f5511627da56ae536")
				.clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
				.scope("public_profile", "email")
				.authorizationUri("https://www.facebook.com/dialog/oauth")
				.tokenUri("https://graph.facebook.com/oauth/access_token")
				.userInfoUri("https://graph.facebook.com/me")
				.userNameAttributeName("id")
				.clientName("Facebook").build();
	}

}
