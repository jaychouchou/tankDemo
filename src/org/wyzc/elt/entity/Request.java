package org.wyzc.elt.entity;

import java.io.Serializable;

/**
 * 封装请求数据信息
 * @author caoxuesong
 *
 */
public class Request implements Serializable{
	//请求要发送的数据（其实就是服务器端调调用业务所需要的参数)
	private Object[] obj;
	//服务器获取方法的参数
	private Class[] paramTypes;
	//服务器端调用方法的名称
	private String methodName;
	//用户的唯一标识
	private String sessionId;
	public Request(){}
	public Request(Object[] obj, Class[] paramTypes, String methodName) {
		super();
		this.obj = obj;
		this.paramTypes = paramTypes;
		this.methodName = methodName;
	}
	public Object[] getObj() {
		return obj;
	}
	public void setObj(Object[] obj) {
		this.obj = obj;
	}
	public Class[] getParamTypes() {
		return paramTypes;
	}
	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
