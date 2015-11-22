package org.storm.android.ftp.client;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;  
/** 
 * FTPCLient回调 
 * @author fanjialing 
 * 
 * @param <T> 
 */  
public interface FTPClientCallback<T> {  
      
    public T doTransfer(FTPClient ftp)throws IOException;  
  
}  
