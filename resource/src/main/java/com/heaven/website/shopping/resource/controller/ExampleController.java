package com.heaven.website.shopping.resource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author xiaoxi.li
 * @date 2019/03/22 16:24
 * @description
 */
@Controller
public class ExampleController {

	public final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping("/test")
	@ResponseBody
	public Object test() {
		return "ok";
	}

	@RequestMapping("/userInfo")
	@ResponseBody
	public Object userInfo(Principal principal) {

		log.debug(principal.toString());


		return principal;
	}
}
