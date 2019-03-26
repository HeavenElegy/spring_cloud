package com.heaven.website.shopping.manager.controller;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 指向认证服务的Feign的实例服务
 * <p>用于实现负载均衡</p>
 * @author li.xiaoxi
 * @date 2019/03/25 16:35
 */
@FeignClient(
		name = "AUTHORIZATION",	// 应用名
		configuration = ExampleFeignClient.ExampleFeignClientConfiguration.class	// 指定配置
)
public interface ExampleFeignClient {



	/**
	 * 获取access token
	 * <p><b>使用Map作为参数</b></p>
	 * @param params map的value类型必须声明为?。否则会报错。
	 * @return access token及相关信息
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/authorization/oauth/token",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	HashMap<String, Object> obtainAccessToken(Map<String, ?>params);

	/**
	 * 获取access token
	 * <p><b>使用对象作为参数</b></p>
	 * @param params 请求参数
	 * @return access token及相关信息
	 */
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/authorization/oauth/token",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	HashMap<String, Object> obtainAccessToken(Object params);


	/**
	 * 局部的配置文件
	 */
	class ExampleFeignClientConfiguration {

		@Autowired
		private ObjectFactory<HttpMessageConverters> messageConverters;

		/**
		 * 添加对Form表单提交的解析
		 * @return
		 */
		@Bean
//		@Primary
//		@Scope(SCOPE_PROTOTYPE)
		Encoder feignFormEncoder() {
			return new FormEncoder(new SpringEncoder(this.messageConverters));
		}

		/**
		 * 添加BasicAuth登陆验证
		 * @return
		 */
		@Bean
		public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
			return new BasicAuthRequestInterceptor("client_id", "666");
		}

	}


}
