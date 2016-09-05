package org.wyzc.elt.entity;

import java.io.Serializable;

public class Response implements Serializable{
	private Exception exception;//异常，最终在客户端显示异常信息
	private Object obj;//服务器端返回的数据
	private boolean isSuccess;
	private String sessionId;
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public void setSuccess() {
		 this.isSuccess = this.exception==null;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	
	
}
