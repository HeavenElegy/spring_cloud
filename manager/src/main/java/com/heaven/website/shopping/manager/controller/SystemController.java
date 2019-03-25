package com.heaven.website.shopping.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xiaoxi.li
 * @date 2019/03/21 14:22
 * @description
 */
@Controller
@RequestMapping("/sys")
public class SystemController {


	@RequestMapping(path = "/login.html", method = RequestMethod.GET)
	public String toLogin() {
		return "login";
	}

}
