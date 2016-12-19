package com.chinesedreamer.parkingfee.socket.server.impl;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.parkingfee.constant.ApplicationConstant;
import com.chinesedreamer.parkingfee.socket.codec.ParkingFeeProtocolCodec;
import com.chinesedreamer.parkingfee.socket.security.AuthorizationFilter;
import com.chinesedreamer.parkingfee.socket.server.EventHandler;
import com.chinesedreamer.parkingfee.socket.server.MinaTcpServer;
import com.chinesedreamer.parkingfee.util.PropertiesUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
@Service("minaShortTcpServer")
public class MinaShortTcpServerImpl implements MinaTcpServer{
	
	private Logger logger = LoggerFactory.getLogger(MinaShortTcpServerImpl.class);
	
	@Resource
	private EventHandler eventHandler;
	@Resource
	private AuthorizationFilter authorizationFilter;
	@Resource
	private ParkingFeeProtocolCodec codec;
	
	private Integer port;
	public Integer getPort() {
		if (null == this.port) {
			PropertiesUtil pu = new PropertiesUtil(ApplicationConstant.ApplicatonProperties.PROP_FILE);
			try {
				this.port = Integer.valueOf(pu.getProperty(ApplicationConstant.ApplicatonProperties.PROP_MINA_TCP_SERVER_PORT));
			} catch (Exception e) {
				this.logger.error("{}",e);
			}
		}
		if (null == this.port) {
			this.setDefaultPort();
		}
		return port;
	}
	
	private void setDefaultPort() {
		this.port = 5678;
	}
	public void setPort(Integer port) {
		this.port = port;
	}


	@Override
	public void startServer() {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(this.codec));
		acceptor.getFilterChain().addLast("realm", this.authorizationFilter);
		acceptor.setHandler(this.eventHandler);
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setWriteTimeout(1000);
		try {
			acceptor.bind(new InetSocketAddress(getPort()));
		} catch (IOException e) {
			this.logger.error("{}",e);
		}
	}

}
