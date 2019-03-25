package com.heaven.website.shopping.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiaoxi.li
 * @date 2019/03/22 14:47
 * @description
 */
@Controller
public class AuthorizationController {



	@RequestMapping("/")
	@ResponseBody
	public Object getAccessToken() {
		return "ok";
	}


}
