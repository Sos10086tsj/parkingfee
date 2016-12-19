package com.chinesedreamer.parkingfee.socket.security.impl;

import javax.annotation.Resource;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.socket.security.AuthorizationFilter;
import com.chinesedreamer.parkingfee.socket.security.SocketRealm;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
@Service
public class AuthorizationFilterImpl extends IoFilterAdapter implements AuthorizationFilter{
	
	private Logger logger = LoggerFactory.getLogger(AuthorizationFilterImpl.class);
	
	@Resource
	private SocketRealm socketRealm;

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession ioSession, Object object) throws Exception {
		if (this.socketRealm.isPermitted(object.toString())) {
			nextFilter.messageReceived(ioSession, object);
		}else {
			this.logger.info("Not permitted.");
			ioSession.close(false);
			return;
		}
	}

	
}
