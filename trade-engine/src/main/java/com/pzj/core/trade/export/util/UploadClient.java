/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pzj.core.trade.order.engine.exception.OrderException;

/**
 * 
 * @author Administrator
 * @version $Id: UploadClient.java, v 0.1 2017年2月9日 下午6:18:28 Administrator Exp $
 */
@Component
public class UploadClient {
	private final static Logger logger = LoggerFactory.getLogger(UploadClient.class);
	private FTPClient ftpClient = new FTPClient();

	private String host;

	private int port;

	private String user;

	private String password;

	private String path;

	{
		Properties pro = new Properties();
		try {
			pro.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("ftp.properties"));
		} catch (IOException e) {
			logger.error("获取FTP配置出错", e);
			throw new OrderException(15001, "配置FTP配置出错");
		}
		host = pro.getProperty("ftp.host");
		port = Integer.valueOf(pro.getProperty("ftp.port"));
		user = pro.getProperty("ftp.user");
		password = pro.getProperty("ftp.password");
		path = pro.getProperty("ftp.path");

	}

	public UploadClient() {
	}

	private void init() {
		try {
			ftpClient.connect(host, port);
			ftpClient.login(user, password);
			int reply;
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return;
			}
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("UTF-8");
		} catch (IOException e) {
			logger.error("连接FTP服务器失败");
			throw new OrderException(15001, "连接FTP服务器失败");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		String filename = "哈哈哈";
		InputStream input = new FileInputStream("d:\\我.xls");
		new UploadClient().uploadFile(filename, input);
	}

	public boolean uploadFile(String filename, InputStream input) {
		logger.info("上传文件:" + filename);
		init();
		init();
		boolean success = false;
		try {
			ftpClient.setControlEncoding("UTF-8");
			//			ftpClient.setCharset(Charset.forName("gbk"));
			ftpClient.changeWorkingDirectory(path);

			ftpClient.storeFile(new String(filename), input);
			input.close();
			ftpClient.logout();
			success = true;
		} catch (IOException e) {
			logger.error("文件上传失败" + e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					logger.error("FTP链接释放失败" + ioe);
				}
			}
		}
		return success;
	}

}
