package com.chinesedreamer.parkingfee.socket.codec.impl;

import javax.annotation.Resource;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeDecoder;
import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeEncoder;
import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeProtocolCodec;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 16, 2016
**/
@Service
public class ParkingFeeProtocolCodecImpl implements ParkingFeeProtocolCodec{
	
	@Resource
	private ParkingFeeDecoder decoder;
	@Resource
	private ParkingFeeEncoder encode;

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return this.encode;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return this.decoder;
	}

}
