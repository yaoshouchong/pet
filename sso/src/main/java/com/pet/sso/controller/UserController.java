package com.pet.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.common.util.ObjectUtil;
import com.pet.common.vo.SysResult;
import com.pet.sso.pojo.User;
import com.pet.sso.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	/**
	 * 注册数据校验
	 * @throws Exception 
	 */
	@RequestMapping("check/{param}/{type}")
	@ResponseBody
	public String check(@PathVariable String param,@PathVariable Integer type,String callback) throws Exception{
		Boolean exists=userService.check(param,type);
		String jsonData = ObjectUtil.mapper.writeValueAsString(SysResult.oK(exists));
		//返回jsonP数据
		String jsonPData=callback+"("+jsonData+")";
		return jsonPData;
		
	}
	/*
	 * 用户注册
	 */
	@RequestMapping("register")
	@ResponseBody
	public SysResult doRegister(User user){
		userService.doRegister(user);
		return SysResult.oK(user.getUsername());
	}
	/*
	 * 登陆
	 */
	@RequestMapping("login")
	@ResponseBody
	public SysResult doLogin(String u,String p){
 		SysResult result=userService.doLogin(u,p);
		return result;
	}
	/*
	 * 接受前台ticket查询用户信息
	 */
	@RequestMapping("query/{ticket}")
	@ResponseBody
	public  String checkUserJson(String callback,@PathVariable String ticket){
		String sysResultJson=userService.checkUserJson(ticket);
		if(StringUtils.isNotEmpty(callback)){
			return callback+"("+sysResultJson+")";
		}
		return sysResultJson;
		/*String userJson=userService.checkUserJSon(ticket);
		if(StringUtils.isNotEmpty(userJson)){
			//判断返回的结构是json还是jsonp
			if(StringUtils.isNoneEmpty(callback)){
				
			}
		}*/
	}

}
