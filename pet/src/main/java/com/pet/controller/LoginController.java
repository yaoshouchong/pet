package com.pet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.common.util.CookieUtils;
import com.pet.common.vo.SysResult;
import com.pet.pojo.User;
import com.pet.service.LoginService;



@Controller
public class LoginController {
	@Autowired
	private LoginService loginService;
	// 用户登录
	@RequestMapping("dologin")
	
	public String doLoing(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 从sso获取的ticket如何处理Cookie类可以完成
		// common工具工程，有一个工具类，CookieUtils
		String ticket = loginService.login(user);
		if (StringUtils.isNotEmpty(ticket)) {// ticket非空时候，登录成功
			// 写cookie
			CookieUtils.setCookie(request, response, "JT_TICKET", ticket);
			return "redirect:index.html";
		}else{
			return "redirect:login.html";
		}
		
	}

}
