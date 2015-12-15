package com.elterx.webapp.api;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;

public abstract class JSend {
	private final String status;
	
	public JSend(String status){
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public static class Success extends JSend{
		private final Object data;
		public Success(Object data){
			super("success");
			this.data = data;
		}
		public Object getData() {
			return data;
		}
	}
	
	public static class Fail extends JSend{
		private final Object data;
		public Fail(Object data){
			super("fail");
			this.data = data;
		}
		public Object getData() {
			return data;
		}
	}
	
	public static class Error extends JSend{
		private final String message;
		private final Object data;
		public Error(String message, Object data) {
			super("error");
			this.message = message;
			this.data = data;
		}
		public String getMessage() {
			return message;
		}
		public Object getData() {
			return data;
		}
	}

	public static Response success(Object data, int status){
		return Response.status(status).entity(new JSend.Success(data)).build();
	}
	
	public static Response success(Object data){
		return success(data, 200);
	}
	
	public static Response fail(Object data, int status){
		return Response.status(status).entity(new JSend.Fail(data)).build();
	}
	
	public static Response fail(Object data){
		return fail(data, 500);
	}
	
	public static Response error(String message, int status){
		return Response.status(status).entity(new JSend.Error(message, null)).build();
	}
	
	public static Response error(String message){
		return error(message, 500);
	}
	
	public static Response error(Throwable t, int status){
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return Response.status(status).entity(new JSend.Error(t.getMessage(), sw.toString())).build();
	}
	
	public static Response error(Throwable t){
		return error(t, 500);
	}
}
