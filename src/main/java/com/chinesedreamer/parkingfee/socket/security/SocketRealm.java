package com.chinesedreamer.parkingfee.socket.security;
/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
public interface SocketRealm {
	public boolean isPermitted(Object object);
}
