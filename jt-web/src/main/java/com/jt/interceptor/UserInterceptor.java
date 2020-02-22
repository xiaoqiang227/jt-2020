package com.jt.interceptor;

import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.util.ObjectToJsonUtil;
import com.jt.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//拦截器
@Component //将对象交给Spring容器管理
public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JedisCluster jedisCluster;
	//private static final String JTUSER = "JT-USER";
	/**
	 * 实现目标:用户不登录,不能访问购物车页面,同时将用户信息存入 ThreadLocal 中
	 * 实现思路:
	 * 1.获取cookie
	 * 2.获取redis
	 * 都能获取说明用户已登录
	 */
	//方法执行之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//查询用户名
		String userName = CookieUtil.getCookies(request, "JT_USERNAME");

		//获取用户的IP
		String IP = IPUtil.getIpAddr(request);
		//验证用户IP
		String jt_ip = jedisCluster.hget(userName, "JT_IP");

		//获取ticket密匙
		String ticket = CookieUtil.getCookies(request,"JT_TICKET");
		//验证用户密匙ticket
		String jt_ticket = jedisCluster.hget(userName, "JT_TICKET");

		//如果用户IP和ticket密匙相等，身份验证通过
		try {
			if (jt_ip.equalsIgnoreCase(IP) && jt_ticket.equalsIgnoreCase(ticket)){
				//获取Redis中的用户信息
				String jt_userjson = jedisCluster.hget(userName, "JT_USERJSON");
				//如果身份验证通过，并且用户存在，说明用户已登录
				if (!StringUtils.isEmpty(jt_userjson)){
					//将用户由json转为对象
					User user = ObjectToJsonUtil.toObject(jt_userjson, User.class);
					//将用户对象存入ThreadLocal中
					ThreadLocalUtil.setUser(user);
					System.out.println("用户信息保存到ThreadLocal中");
					return true;
				}
			}
		}catch (NullPointerException n){
			//表示用户未登录,重定向到登录页面。一般拦截器中的false和重定向联用
			response.sendRedirect("/user/login.html");
			return false; //表示拦截
		}


		//表示用户未登录,重定向到登录页面。一般拦截器中的false和重定向联用
		response.sendRedirect("/user/login.html");
		return false; //表示拦截
	}
	
	/**
	 *  方法执行之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		
		//System.out.println("方法执行之后:post");
	}
	
	
	/**
	 * 方法完成的最后阶段
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//删除ThreadLocal(用户数据),防止内存溢出
		ThreadLocalUtil.remove();
	}
	
}
