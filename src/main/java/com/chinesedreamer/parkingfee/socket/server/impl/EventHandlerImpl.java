package com.chinesedreamer.parkingfee.socket.server.impl;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.socket.codec.SocketParkingFeeEntity;
import com.chinesedreamer.parkingfee.socket.server.EventHandler;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
@Service
public class EventHandlerImpl extends IoHandlerAdapter implements EventHandler{
	private Logger logger = LoggerFactory.getLogger(EventHandlerImpl.class);
	@Override  
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {  
        this.logger.error("{}",cause);
    }
	
	@Override  
	public void messageReceived(IoSession session, Object message){
		
		if (message instanceof SocketParkingFeeEntity) {
			SocketParkingFeeEntity entity = (SocketParkingFeeEntity)message;
			String res = "接收到：" +  entity.toString();
			System.out.println(res);
			session.write(res);
			return;
		}
		
		String str = message.toString();
		if (str.trim().equalsIgnoreCase("quit")) {  
			session.close();
            return;
        }
		Date date = new Date();  
	    System.out.println("hello"+str+session.getRemoteAddress()+date.toString());  
	    String response = "i am recived at " + System.currentTimeMillis();
	    try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    response += " response at " + System.currentTimeMillis();
	    session.write(response);  
	    System.out.println("Message written...");
	}
	
	@Override  
    public void sessionClosed(IoSession session) throws Exception {  
        super.sessionClosed(session);  
        this.logger.info("Session#{} closed.",session.getId());
    }
}
