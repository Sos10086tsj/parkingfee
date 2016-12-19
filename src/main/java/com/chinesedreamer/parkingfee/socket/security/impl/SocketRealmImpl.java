package com.chinesedreamer.parkingfee.socket.security.impl;

import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.socket.codec.SocketParkingFeeEntity;
import com.chinesedreamer.parkingfee.socket.security.SocketRealm;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
@Service
public class SocketRealmImpl implements SocketRealm{

	@Override
	public boolean isPermitted(Object object) {
		if (object instanceof SocketParkingFeeEntity) {
			SocketParkingFeeEntity entity = (SocketParkingFeeEntity)object;
			//TODO
			return true;
		}
		//TODO
		return true;
	}

}
