package com.pet.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.common.service.HttpClientService;
import com.pet.common.util.ObjectUtil;
import com.pet.common.vo.HttpResult;
import com.pet.common.vo.SysResult;
import com.pet.pojo.EntityBean;
import com.pet.pojo.User;


@Service
public class LoginService {
	@Autowired
	private HttpClientService client;
	public String login(User user) throws Exception {
		//httpclient发起请求，获取返回值，解析ticket返回给controller
		String url=EntityBean.ssoUrl;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("u", user.getUsername());
		params.put("p", user.getPassword());
		//client发起请求，获取sysResult的json字符串
		HttpResult result = client.doPost(url, params);//获取响应体
		String jsonData = result.getBody();//sysResult的json
		SysResult sysresult = ObjectUtil.mapper.
				readValue(jsonData, SysResult.class);
		String ticket=(String)sysresult.getData();
		return ticket;
	}

}
