package org.storm.android.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

public class ResUtil {
	  
	 /**
	  * 	File f=new File(ftpConfigDir);  
	  *     copyResourceFile(R.raw.users, ftpConfigDir+"users.properties"); 
	  * 复制 res/raw 下的文件 到指定目录
	  * @param rid        资源id
	  * @param targetFile  目标文件
	  */
	 public void copyResourceFile(Context context,int rid, String targetFile){  
      InputStream fin = context.getResources().openRawResource(rid);  
      FileOutputStream fos=null;  
      int length;  
      try {  
          fos = new FileOutputStream(targetFile);  
          byte[] buffer = new byte[1024];   
          while( (length = fin.read(buffer)) != -1){  
              fos.write(buffer,0,length);  
          }  
      } catch (FileNotFoundException e) {  
          e.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      } finally{  
          if(fin!=null){  
              try {  
                  fin.close();  
              } catch (IOException e) {  
                  e.printStackTrace();  
              }  
          }  
           if(fos!=null){  
               try {  
                  fos.close();  
              } catch (IOException e) {  
                  e.printStackTrace();  
              }  
           }  
      }  
  } 
}
