package com.chinesedreamer.parkingfee.socket.codec.impl;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chinesedreamer.parkingfee.constant.ApplicationConstant;
import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeDecoder;
import com.chinesedreamer.parkingfee.socket.codec.SocketParkingFeeEntity;
import com.chinesedreamer.parkingfee.util.ByteUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 16, 2016
**/
@Service
public class ParkingFeeDecoderImpl extends CumulativeProtocolDecoder implements ParkingFeeDecoder{
	private Logger logger = LoggerFactory.getLogger(ParkingFeeDecoderImpl.class);

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		CharsetDecoder cd = Charset.forName("UTF-8").newDecoder();
		
//		SocketParkingFeeEntity entity = new SocketParkingFeeEntity();
//		int len = in.remaining();
//		IoBuffer buffer = IoBuffer.allocate(len).setAutoExpand(true);
//		
//		int textLineNum = 0;
//		int columnNum = 0;
//		
//		while (in.hasRemaining()) {
//			byte b = in.get();
//			buffer.put(b);
//			
//			columnNum ++;
//			
//			if (b == 10 && textLineNum < 3) {
//				if (textLineNum == 0) {
//					buffer.flip();
//					entity.setApi(buffer.getString(columnNum, cd));
//				}else if (textLineNum == 1) {
//					buffer.flip();
//					entity.setToken(buffer.getString(columnNum, cd));
//				}else if (textLineNum == 2) {
//					buffer.flip();
//					entity.setData(buffer.getString(columnNum, cd));
//					break;
//				}
//				columnNum = 0;
//				buffer.clear();
//				textLineNum ++;
//			}
//		}
//		out.write(entity);
		
		int len = in.remaining();
		if (len > ApplicationConstant.SOCKET.MSG_HEADER_LEN) {//钱4个是字节长度
			in.mark();
			byte[] l = new byte[ApplicationConstant.SOCKET.MSG_HEADER_LEN];
			in.get(l);
			int dataLen = ByteUtil.byteArrayToInt(l);
			
			if (in.remaining() < dataLen) {
				in.reset();
				return false;
			}else if (dataLen > ApplicationConstant.SOCKET.SINGLE_MSG_LEGNTH) {
				int col = 0;
				while (in.hasRemaining() && col < dataLen) {
					byte b = in.get();
					if (b == 10) {
						break;
					}
					col ++;
				}
				this.logger.error("Message too long. over limit:{}, actual:{}",ApplicationConstant.SOCKET.SINGLE_MSG_LEGNTH,dataLen);
				return false;
			}else {
				IoBuffer buffer = IoBuffer.allocate(dataLen).setAutoExpand(true);
				int col = 0;
				while (in.hasRemaining() && col < dataLen) {
					byte b = in.get();
					buffer.put(b);
					if (b == 10) {
						break;
					}
					col ++;
				}
				//实际数据比预期值少
				if (col < dataLen) {
					this.logger.error("Not enough data. Expect:{},actual:{}.",dataLen,col);
					return false;
				}
				if (in.hasRemaining()) {
					byte endTag = in.get();
					if (endTag == 10) {//追加的结尾符号
						in.mark();
						buffer.flip();
						String socketData = buffer.getString(dataLen, cd);
						SocketParkingFeeEntity entity = JSON.toJavaObject(JSON.parseObject(socketData), SocketParkingFeeEntity.class);
						out.write(entity);
						buffer.free();
					}else {
						int overLength = 1;
						while (in.hasRemaining()) {
							byte b = in.get();
							if (b == 10) {
								this.logger.error("Not too long. Expect:{},actual:{}.",dataLen, col + overLength);
								return false;
							}
							overLength++;
						}
					}
				}else {
					this.logger.error("Missing end tag.");
					return false;
				}
			}
		}
		return false;
	}
	
}
