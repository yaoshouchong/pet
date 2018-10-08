package com.pet.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pet.common.redis.RedisService;
import com.pet.common.util.ObjectUtil;
import com.pet.common.vo.SysResult;
import com.pet.sso.mapper.UserMapper;
import com.pet.sso.pojo.User;
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
    /**
     * 查询用户名存在
     * @param param
     * @param type
     * @return
     */
	public Boolean check(String param, Integer type) {
		int count = 0;
		User user = new User();
		if (type == 1) {
			user.setUsername(param);
			count = userMapper.selectCount(user);
		} else if (type == 2) {
			user.setPhone(param);
			count = userMapper.selectCount(user);

		}else{
			user.setEmail(param);
			count = userMapper.selectCount(user);
		}
		if(count==0){
			return false;
		}
		return true;
	}
    /*
     * 注册用户
     */
	public void doRegister(User user) {
		user.setEmail(user.getUsername());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		//密码时加密的
		String password=user.getPassword();
        user.setPassword(DigestUtils.md5Hex(password));
        userMapper.insert(user);
		
	}
	@Autowired
	private RedisService redis;
	public SysResult doLogin(String u, String p) {
		User _user=new User();
		_user.setUsername(u);
		User user = userMapper.selectOne(_user);
		if(user!=null){
			String tpassword=DigestUtils.md5Hex(p);
			if(tpassword.equals(user.getPassword())){
				String ticket=DigestUtils.md2Hex("JT_TICKET"+System.currentTimeMillis());
				try{
					String userJson=ObjectUtil.mapper.writeValueAsString(user);
					redis.set(ticket, userJson,3600);
					return SysResult.oK(ticket);
				}catch(Exception e){
					return SysResult.build(201, "","");
				}
			}
		}
		return SysResult.build(201, "用户名密码不正确","");
	}
	public String checkUserJson(String ticket) {
        try {
        	String userJson = redis.get(ticket);
			String sysResultJson=ObjectUtil.mapper.writeValueAsString(SysResult.oK(userJson));
			return sysResultJson;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
