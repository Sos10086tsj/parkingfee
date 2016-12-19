package com.chinesedreamer.parkingfee.socket.codec.impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeEncoder;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 16, 2016
**/
@Service
public class ParkingFeeEncoderImpl extends ProtocolEncoderAdapter implements ParkingFeeEncoder{

	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
		buf.putString((String)message, encoder);  
        buf.flip();    
        out.write(buf);
	}

}
