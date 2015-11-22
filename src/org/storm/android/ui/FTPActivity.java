package org.storm.android.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.storm.android.ftp.client.FtpHelper;
import org.storm.android.ftp.server.StormFtpServer;
import org.storm.android.handler.FTPHandler;
import org.storm.android.util.NetworkUtils;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.storage.StorageManager;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 *  FTP示例的Activtiy
 * @author zg
 *
 */
public class FTPActivity extends Activity {
	private String TAG = "MainActivity";
	private String ftpConfigDir= Environment.getExternalStorageDirectory().getAbsolutePath(); 
	private StormFtpServer stormFtpServer;
	public EditText editText;
	private FTPHandler ftpHandler = new FTPHandler(FTPActivity.this);
	
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        LinearLayout mLinearLayout = new LinearLayout(this);  
        mLinearLayout.setLayoutParams(
        		new LinearLayout.LayoutParams(  
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);  
        
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);  
        Button startServerButton = new Button(this);
        startServerButton.setText("start");
        String  ip = NetworkUtils.getWifiLocalIpAddress(this); 
        editText  = new EditText(this);
        editText.setText(ip+":"+8888+"\n");
        
        startServerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		        Log.i(TAG, ftpConfigDir);
		        Log.i(TAG, NetworkUtils.getWifiLocalIpAddress(FTPActivity.this));	        
		        stormFtpServer = new StormFtpServer();
		        stormFtpServer.run();
		        editText.append("开启状态"+ftpConfigDir+"\n");
		        editText.append(NetworkUtils.getWifiLocalIpAddress(FTPActivity.this)+":"+8888+"\n");
			}
		});
        
        
        Button stopButton = new Button(this);
        stopButton.setText("stop");
        stopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
							stormFtpServer.stop();
					        editText.append("停止状态"+"\n");
			}
		});
        
        
        Button connButton = new Button(this);
        connButton.setText("conn");
        connButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				new Thread(){  
					   @Override  
					   public void run()  
					   {  
							try {
								 FtpHelper ftpHelper = FtpHelper.getInstance();
								 FTPClient ftpClient = new FTPClient();
								 String hostname = "192.168.0.18";
								 int port = 8888;
								 String username = "admin";
								 String password = "admin";
								 boolean b =  ftpHelper.connect(ftpClient, hostname, port, username, password);
								 
									Message message = ftpHandler.obtainMessage();
									Bundle bundle = new Bundle();
									bundle.putBoolean("conn", b);
								 
								 
								 if(b){
									 String[] s =ftpHelper.GetFileNames(ftpClient,null);
									 for(String key:s){
										 Log.i(TAG, key);
											bundle.putString("key", key);

									 }
								 }
								 
								 
									message.setData(bundle);

									ftpHandler.sendMessage(message);

							} catch (IOException e) {
								e.printStackTrace();
							}    
					  }  
					}.start();  
		
			}
		});
        
        
        Button clierLog = new Button(this);
        clierLog.setText("清空日志");
        
        
        clierLog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});
        
        mLinearLayout.addView(startServerButton, mLayoutParams); 
        mLinearLayout.addView(stopButton, mLayoutParams);
        mLinearLayout.addView(connButton, mLayoutParams);
        mLinearLayout.addView(clierLog, mLayoutParams);
        mLinearLayout.addView(editText, mLayoutParams);

		setContentView(mLinearLayout);

	}   


	   
	
}
