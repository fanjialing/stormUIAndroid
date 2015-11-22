/**
 * 
 */
package org.storm.android.handler;


import org.storm.android.ui.FTPActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author fanjialing
 *
 */
public class FTPHandler extends Handler{
	
	private String TAG = "FTPHandler";
	
	private FTPActivity activity;
	
	public FTPHandler(FTPActivity activity){ 
		this.activity = activity;
	}
	
	/* (non-Javadoc)
	 * @see android.os.Handler#handleMessage(android.os.Message)
	 */
	@Override
	public void handleMessage(Message msg) {
		
		Bundle bundle = msg.getData();
		boolean bool = bundle.getBoolean("conn");
		String key = (String) bundle.getString("key");
		activity.editText.append("客户端连接状态:"+bool+"\n");
		activity.editText.append(key+"\n");
	}
	
}
