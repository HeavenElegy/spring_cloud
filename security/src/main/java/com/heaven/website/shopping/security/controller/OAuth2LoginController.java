package com.heaven.website.shopping.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author li.xiaoxi
 * @date 2019/03/20 15:48
 * @description
 */
@Controller
public class OAuth2LoginController {

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

	@GetMapping("/")
	public String index(Model model,
						@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
						@AuthenticationPrincipal OAuth2User oauth2User) {
		model.addAttribute("userName", oauth2User.getName());
		model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
		model.addAttribute("userAttributes", oauth2User.getAttributes());
		return "index";
	}


	/**
	 * 打印{@link ClientRegistration}
	 * @return
	 */
	@GetMapping("/printClientRegistration")
	@ResponseBody
	public void printClientRegistration() {

		System.out.println(clientRegistrationRepository.getClass());

		ClientRegistration facebook = clientRegistrationRepository.findByRegistrationId("facebook");

		System.out.println(facebook.toString());
	}


	/**
	 * 打印授权客户端信息
	 * @param token
	 */
	@GetMapping("/print2")
	@ResponseBody
	public Object print2(OAuth2AuthenticationToken token) {

		OAuth2User principal = token.getPrincipal();
		System.out.println(principal.getAttributes());

		OAuth2AuthorizedClient oAuth2AuthorizedClient = oAuth2AuthorizedClientService.loadAuthorizedClient(
				token.getAuthorizedClientRegistrationId(),
				principal.getName());

		System.out.println(oAuth2AuthorizedClient);

		OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();

		System.out.println(accessToken);

		return oAuth2AuthorizedClient;
	}


	/**
	 * 打印授权客户端信息
	 * @param client
	 */
	@GetMapping("/print3")
	@ResponseBody
	public Object print3(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient client) {


		System.out.println(client);

		OAuth2AccessToken accessToken = client.getAccessToken();

		System.out.println(accessToken);

		return client;
	}



}