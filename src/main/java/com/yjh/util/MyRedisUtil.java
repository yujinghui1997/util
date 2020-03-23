package com.yjh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
public class MyRedisUtil {

	/**
	 * 安全性：
	 * auth ‘登陆密码’ (认证密码)
	 * config get requirepass（获取登陆密码）
	 * config set requirepass ‘密码’（设置登陆密码）
	 * 数据类型操作：
	 * string  :  set key value(添加值)         get key(取值)            del key（删除）
	 * hash    ： hset key field value(添加值)  hget key field（取值）    hdel key field（删除值）
	 * set     ： sadd key value(添加值)        smembers key（获取所有值） srem key value（删除值）
	 * 消息发送（生产者，消费者）：
	 * 订阅（消费者） ： SUBSCRIBE ‘客户端名称’
	 * 发布（生产者） ： PUBLISH ‘客户端名称’ ‘消息’
	 * 依赖包：
	 * <dependency>
	 * <groupId>org.springframework.boot</groupId>
	 * <artifactId>spring-boot-starter-data-redis</artifactId>
	 * </dependency>
	 */
	private StringRedisTemplate stringRedisTemplate;
	public MyRedisUtil(StringRedisTemplate stringRedisTemplate){
		this.stringRedisTemplate = stringRedisTemplate;
	}
	public MyRedisUtil(){}

	/**
	 * 时间操作
	 **/
	private TimeUnit unit = TimeUnit.MINUTES;

	//---------------------String 类型操作
	public void setString(String key, String value) {
		setString(key, value, null);
	}
	public void setString(String key, String value,Long timeout) {
		setString(key, value, timeout,unit);
	}
	public void setString(String key, String value, Long timeout,TimeUnit unit) {
		stringRedisTemplate.opsForValue().set(key, value);
		setTime(key,timeout,unit);
	}
	public String getString(String key) {
		Object value = stringRedisTemplate.opsForValue().get(key);
		return value == null ? null : value.toString();
	}
	public void delString(String key) {
		delKey(key);
	}

	//-----------------------hash类型操作
	public void setHash(String key, String field, Object value) {
		setHash(key, field, value, null);
	}
	public void setHash(String key, String field, Object value,Long timeout) {
		setHash(key, field, value, timeout,unit);
	}
	public void setHash(String key, String field, Object value, Long timeout,TimeUnit unit) {
		stringRedisTemplate.opsForHash().put(key, field, JSON.toJSONString(value));
		setTime(key, timeout,unit);
	}
	public <T> T getHash(String key, String field, Class<T> clazz) {
		Object value = stringRedisTemplate.opsForHash().get(key, field);
		T data = JSONObject.parseObject(value.toString(), clazz);
		return data;
	}
	public void delHash(String key, String field) {
		stringRedisTemplate.opsForHash().delete(key, field);
	}
	//----------------------set操作
	public void setSet(String key, String value) {
		setSet(key, value, null);
	}
	public void setSet(String key, String value,Long timeout) {
		setSet(key, value, timeout,unit);
	}
	public void setSet(String key, String value, Long timeout,TimeUnit unit) {
		stringRedisTemplate.opsForSet().add(key, value);
		setTime(key, timeout,unit);
	}
	public Set<String> getSet(String key) {
		return stringRedisTemplate.opsForSet().members(key);
	}
	public void delSet(String key, String value) {
		stringRedisTemplate.opsForSet().remove(key, value);
	}

	//----------------------------发送消息用于消息队列
	public void sendMsg(String client, Object msg) {
		stringRedisTemplate.convertAndSend(client, msg);
	}

	//----------------------------辅助
	public void setTime(String key, Long timeout) {
		setTime(key,timeout,unit);
	}
	public void setTime(String key, Long timeout,TimeUnit unit) {
		// null 是防止报错 大于0 是因为 设置负整数会出现系统不报错，但是redis添加不进去数据，redis默认-1为永久
		if(null != timeout && timeout > 0){
			stringRedisTemplate.expire(key, timeout, unit);
		}
	}
	public Long getTime(String key) {
		return stringRedisTemplate.getExpire(key, unit);
	}
	public Boolean iskey(String key) {
		return stringRedisTemplate.hasKey(key);
	}
	public void delKey(String... keys) {
		if (keys != null && keys.length > 0) {
			if (keys.length == 1) {
				stringRedisTemplate.delete(keys[0]);
			} else {
				stringRedisTemplate.delete(CollectionUtils.arrayToList(keys));
			}
		}
	}
	public List<String> getKeys(String prefix) {
		StringBuilder sb = new StringBuilder();
		if (prefix.equals("") || prefix == null) {
			sb.append("*");
		} else {
			if (prefix.contains("*")) {
				if (prefix.equals("*")) {
					sb.append("*");
				} else {
					sb.append(prefix);
				}
			} else {
				sb.append(prefix).append("*");
			}
		}
		Set<String> set = stringRedisTemplate.keys(sb.toString());
		return new ArrayList<>(set);
	}

}
