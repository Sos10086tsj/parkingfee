package com.chinesedreamer.parkingfee.constant;
/**
 * Description:
 * Auth:Paris
 * Date:Dec 14, 2016
**/
public class ApplicationConstant {
	public static class ApplicatonProperties{
		public static String PROP_FILE = "application.properties";
		public static String PROP_MINA_TCP_SERVER_PORT = "mina.tcp.server.port";
	}
	
	public static class SOCKET{
		public static String MSG_SEPARATOR = "\n";//换行符分隔
		public static Integer MSG_HEADER_LEN = 4;//header 4 字节显示数据长度
		public static Integer SINGLE_MSG_LEGNTH = 1000;
	}
}
