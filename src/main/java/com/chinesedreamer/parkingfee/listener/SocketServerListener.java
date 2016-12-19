package com.chinesedreamer.parkingfee.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chinesedreamer.parkingfee.socket.server.MinaTcpServer;

/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
public class SocketServerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		MinaTcpServer minaTcpServer = (MinaTcpServer)application.getBean("minaShortTcpServer",MinaTcpServer.class);
		minaTcpServer.startServer();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
