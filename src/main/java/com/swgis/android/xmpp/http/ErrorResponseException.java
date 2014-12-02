package com.swgis.android.xmpp.http;

import org.apache.http.StatusLine;

public class ErrorResponseException extends Exception {

	/**
	 * http response 返回错误异常  状态码不为200的都触发此异常
	 */
	private static final long serialVersionUID = 1L;
	private String mExtra;
	public ErrorResponseException(String msg){
		super(msg);
	}
	public ErrorResponseException(String msg,String extra){
		super(msg);
		this.mExtra = extra;
	}
	public ErrorResponseException(StatusLine sl){
		super("response error!!  error code:"+sl.getStatusCode()
				+"    "+sl.getReasonPhrase());
	}
	
	
}
