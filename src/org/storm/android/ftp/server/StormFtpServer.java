package org.storm.android.ftp.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
/**
 * 启动一个Ftp 的server
 * @author fanjialing
 *
 */
public class StormFtpServer {
	private FtpServer ftpServer; 

	public void run(){
		  FtpServerFactory serverFactory = new FtpServerFactory();
		  ListenerFactory factory = new ListenerFactory();
		  factory.setPort(8888);
		  serverFactory.addListener("default", factory.createListener());  
		  BaseUser user = new BaseUser();
		  user.setName("admin");
		  user.setPassword("admin");
		  user.setHomeDirectory("/storage");
		  
		  List<Authority> authorities = new ArrayList<Authority>();
		  authorities.add(new WritePermission());
		  user.setAuthorities(authorities);
		  
		  try {
			serverFactory.getUserManager().save(user);
			ftpServer = serverFactory.createServer();
			ftpServer.start();
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}
	
	
	public void stop(){
    	ftpServer.stop();
	}

}
