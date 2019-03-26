package com.heaven.website.shopping.manager.controller;

import com.heaven.website.shopping.manager.common.utils.JsonUtils;
import com.heaven.website.shopping.manager.common.utils.NetworkUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 示例用接口
 * @author li.xiaoxi
 * @date 2019/03/21 16:42
 */
@Controller
@RequestMapping("/example")
public class ExampleController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final String KEY_AUTHORIZATION_ERROR = "error";

	@Autowired
	private ExampleFeignClient exampleFeignClient;

	@Autowired
	private DiscoveryClient discoveryClient;


	/**
	 * 获取用户信息。Spring标准的对象
	 * @param principal	Spring进行注入
	 * @return	Principal对象
	 */
	@RequestMapping("/userInfo")
	@ResponseBody
	public Object userInfo(Principal principal) {
		System.out.println(principal);
		return principal;
	}

	/**
	 * 打印一些session信息。并返回SecurityContextHolder.getContext();获取的用户信息
	 * @param request	Spring进行注入
	 * @return	Principal对象
	 */
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

	/**
	 * 返回一个字符串
	 * @return	字符串
	 */
	@RequestMapping("/hasAdmin")
	@ResponseBody
	public Object hasAdmin() {
		return "ok";
	}

	/**
	 * 跳转到index页面
	 * @return	对应页面的相对路径
	 */
	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	/**
	 * 跳转到授权页面
	 * @return	对应页面的相对路径
	 */
	@RequestMapping("/authorization")
	public String authorization () {
		return "/example/authorization";
	}

	/**
	 * 应用授权成功。回调给予授权码
	 * @return	验证成功后再次返回到授权页面
	 */
	@RequestMapping("/authorization/code")
	public Object authorization (HttpServletRequest request, @RequestParam Map<String, Object> modle, ModelMap modelMap) {

		log.debug("应用授权回调。method:[{}]", request.getMethod().toUpperCase());

		String code;

		HashMap<String, Object> hashMap = new HashMap<>(modle);

		if(modle.containsKey(KEY_AUTHORIZATION_ERROR)) {
			log.error("授权异常:[{}]", hashMap);
		}else {
			code = (String) hashMap.get("code");
			log.debug("授权成功。data:[{}]", hashMap);
			log.debug("尝试获取access token...");

			//获取访问token
			HashMap<String, Object> params = new HashMap<>();
			params.put("grant_type", "authorization_code");
			params.put("code", code);
			params.put("redirect_uri", "http://localhost:9930/manager/example/authorization/code");
			HashMap readValue = NetworkUtils.form("http://localhost:9910/authorization/oauth/token", params, HashMap.class);

			log.debug("响应信息:[{}]", JsonUtils.toJson(readValue));
			//成功数据{"access_token":"5ac293db-204c-43f0-b8eb-1cf12d4a23a2","scope":"test","token_type":"bearer","expires_in":42474}
			//失败数据{"error_description":"Redirect URI mismatch.","error":"invalid_grant"}

			// 检查获取是否成功
			if(readValue.containsKey(KEY_AUTHORIZATION_ERROR)) {
				log.error("access token 获取失败:[{}]", readValue);
			}else {
				log.debug("access token 获取成功。data:[{}]", readValue);

				//尝试访问userInfo接口
				HashMap principal = getPrincipal((String) readValue.get("access_token"));

				log.debug("userInfo:[{}]", principal);

				modelMap.addAttribute("userInfo", principal);

			}
		}
		return "/example/authorization";
	}

	/**
	 * 检查token
	 * @param token	字符串
	 * @return	结果信息
	 */
	private HashMap getPrincipal(String token) {
		return NetworkUtils.get("http://localhost:9910/authorization/oauth/check_token?token=" + token, HashMap.class);
	}


	/**
	 * 获取注册中心中的已有服务
	 * <p>http://localhost:9930/manager/example/service-instances/manager</p>
	 * @param applicationName	对应服务名（eureka中）
	 * @return	注册信息集合
	 */
	@RequestMapping("/service-instances/{applicationName}")
	@ResponseBody
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}


	/**
	 * 使用feign访问access token服务
	 * @param code	授权码
	 * @return	access token以及相关信息
	 */
	@RequestMapping("/feign/test")
	@ResponseBody
	public Object feignTest(@RequestParam String code) {
		HashMap<String, Object> params = new HashMap<>(3);
		params.put("grant_type", "authorization_code");
		params.put("code", code);
		params.put("redirect_uri", "http://localhost:9930/manager/example/authorization/code");

		//方法1：使用map
//		return exampleFeignClient.obtainAccessToken(params);
		//方法2：使用对象
		return exampleFeignClient.obtainAccessToken(new AccessTokenRequest("authorization_code", code, "http://localhost:9930/manager/example/authorization/code"));

	}


	/**
	 * 获取AccessToken的请求参数实体类
	 */
	@Data
	class AccessTokenRequest {
		String grant_type;
		String code;
		String redirect_uri;

		public AccessTokenRequest(String grant_type, String code, String redirect_uri) {
			this.grant_type = grant_type;
			this.code = code;
			this.redirect_uri = redirect_uri;
		}
	}


}
