package com.heaven.website.shopping.resource.controller;

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
@RequestMapping("/example")
public class ExampleController {

	@RequestMapping("/test")
	@ResponseBody
	public Object test() {
		return "ok";
	}


	@RequestMapping("/userInfo")
	@ResponseBody
	public Object userInfo(Principal principal) {
		return principal;
	}
}
