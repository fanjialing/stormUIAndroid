package org.storm.android.ftp.client;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class Test {
	public static void main(String[] args) throws IOException {
		 FtpHelper ftpHelper = FtpHelper.getInstance();
		 FTPClient ftpClient = new FTPClient();
		 String hostname = "192.168.1.102";
		 int port = 8888;
		 String username = "admin";
		 String password = "admin";
		 boolean b =  ftpHelper.connect(ftpClient, hostname, port, username, password);
		 System.out.println(b);
//	     if(b){
//	    	DownloadStatus downloadStatus = ftpHelper.download(ftpClient, "ssh/oracle_daabases.rar", "f:/tt.rar");
//	    	System.out.println(downloadStatus);
//	     } 
//	     
//	     
//	     if(b){
//	    	 UploadStatus uploadStatus = ftpHelper.upload(ftpClient, "F:/oracle_daabases.rar", "ssh/tests.rar");
//	    	 System.out.println(uploadStatus);
//	     }

	}
}
