package org.storm.android.ui;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.storm.android.ftp.client.FtpHelper;
import org.storm.android.ftp.server.StormFtpServer;
import org.storm.android.handler.FTPHandler;
import org.storm.android.util.NetworkUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class FileFrament extends BaseFragment implements OnClickListener{
	private String ftpConfigDir= Environment.getExternalStorageDirectory().getAbsolutePath(); 
	private StormFtpServer stormFtpServer;
	public EditText editText;private Activity activity;
	private Handler handler = new Handler();  
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	@Override
	public String getFragmentName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_html, container, false);
//        return rootView;
        LinearLayout mLinearLayout = new LinearLayout(activity);  
        mLinearLayout.setLayoutParams(
        		new LinearLayout.LayoutParams(  
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);  
        
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(  
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);  
        Button startServerButton = new Button(activity);
        startServerButton.setText("start");
        String  ip = NetworkUtils.getWifiLocalIpAddress(activity); 
        editText  = new EditText(activity);
        editText.setText(ip+":"+8888+"\n");
        
        startServerButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
		        stormFtpServer = new StormFtpServer();
		        stormFtpServer.run();
		        editText.append("开启状态"+ftpConfigDir+"\n");
		        editText.append(NetworkUtils.getWifiLocalIpAddress(activity)+":"+8888+"\n");
			}
		});
        
        
        Button stopButton = new Button(activity);
        stopButton.setText("stop");
        stopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
							stormFtpServer.stop();
					        editText.append("停止状态"+"\n");
			}
		});
        
        
        Button connButton = new Button(activity);
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
								 
									Message message = handler.obtainMessage();
									Bundle bundle = new Bundle();
									bundle.putBoolean("conn", b);
								 
								 
								 if(b){
									 String[] s =ftpHelper.GetFileNames(ftpClient,null);
									 for(String key:s){
											bundle.putString("key", key);

									 }
								 }
								 
								 
									message.setData(bundle);

									handler.sendMessage(message);

							} catch (IOException e) {
								e.printStackTrace();
							}    
					  }  
					}.start();  
		
			}
		});
        
        
        Button clierLog = new Button(activity);
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
        
        return mLinearLayout;
    }
}
