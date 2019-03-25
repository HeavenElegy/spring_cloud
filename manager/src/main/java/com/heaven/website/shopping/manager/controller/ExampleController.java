package com.heaven.website.shopping.manager.controller;

import okhttp3.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxi.li
 * @date 2019/03/21 16:42
 * @description
 */
@Controller
@RequestMapping("/example")
public class ExampleController {

	private Logger log = LoggerFactory.getLogger(getClass());


	@RequestMapping("/userInfo")
	@ResponseBody
	public Object userInfo(Principal principal) {
		System.out.println(principal);
		return principal;
	}

	@RequestMapping("/session")
	@ResponseBody
	public Object session(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println(session.getId());
		Enumeration<String> attributeNames = session.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String element = attributeNames.nextElement();
			System.out.println(element);
			Object attribute = session.getAttribute(element);
			System.out.println(attribute.getClass());
		}


		SecurityContextImpl context = (SecurityContextImpl) SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		System.out.println(principal);

		return principal;
	}

	@RequestMapping("/hasAdmin")
	@ResponseBody
	public Object hasAdmin() {
		return "ok";
	}

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/authorization")
	public String toAuthorization() {
		return "/example/authorization";
	}

	@RequestMapping("/authorizationSuccess")
	@ResponseBody
	public Object authorizationSuccess(@RequestParam String code) throws IOException {

		log.info("应用授权code:[{}]", code);
		if(!StringUtils.hasText(code)) {
			log.error("授权失败。停止后续操作");
			return "err";
		}

		log.info("开始获取访问token");

		OkHttpClient okHttpClient = new OkHttpClient
				.Builder()
				.authenticator((route, response) -> {
					String credential = Credentials.basic("client_id", "666");
					return response.request().newBuilder().header("Authorization", credential).build();
				})
				.build();

		FormBody formBody = new FormBody.Builder()
				.add("grant_type", "authorization_code")
				.add("scopes", "test")
				.add("code", code)
				.build();

		Request request = new Request.Builder().url("http://localhost:9910/authorization/oauth/token")
				.post(formBody)
				.build();

		Call call = okHttpClient.newCall(request);
		Response execute = call.execute();
		okhttp3.ResponseBody body = execute.body();

		String result = body.string();
		log.info("access token 获取情况:[{}]", result);


		ObjectMapper objectMapper = new ObjectMapper();

		HashMap hashMap = objectMapper.readValue(result, HashMap.class);

		if(!hashMap.containsKey("access_token")) {
			return "access token 获取失败！";
		}

		String access_token = (String) hashMap.get("access_token");

		log.info("尝试访问资源");

		HttpUrl httpUrl = HttpUrl.parse("http://localhost:9920/resource/example/userInfo")
				.newBuilder()
				.addQueryParameter("access_token", access_token)
				.build();

		request = new Request.Builder().url(httpUrl)
				.build();

		call = okHttpClient.newCall(request);
		execute = call.execute();
		body = execute.body();
		result = body.string();

		log.info("资源获取情况:[{}]", result);

		return result;
	}

}
