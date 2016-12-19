package com.chinesedreamer.parkingfee.socket.codec;
/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
public class SocketParkingFeeEntity {
	protected String token;
	protected String api;
	protected String data;
	protected String errorMessage;
	public String getToken() {
		return token;
	}
	public String getApi() {
		return api;
	}
	public String getData() {
		return data;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "SocketParkingFeeEntity [token=" + token + ", api=" + api + ", data=" + data + ", errorMessage="
				+ errorMessage + "]";
	}
	
	
}
