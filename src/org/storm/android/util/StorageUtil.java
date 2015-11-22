package org.storm.android.util;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.storm.android.ui.FTPActivity;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.util.Log;
 
public class StorageUtil {
    private static final int ERROR = -1;
    public static int save_dir = 1;
 
    /**
     * 判断是否已经安装SD卡
     * @return
     */
    public static boolean isSDCardExist() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }
 
    /**
     * 内存剩余空间
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
 
    /**
     * 内存总空间
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }
 
    /**
     * SD卡剩余空间
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }
 
    /**
     *  SD卡总空间
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }
 
    /**
     * 判断SD卡下external_sd文件夹的总大小
     * @return
     */
    public static long getTotalExternal_SDMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            File externalSD = new File(path.getPath() + "/external_sd");
            if (externalSD.exists() && externalSD.isDirectory()) {
                StatFs stat = new StatFs(path.getPath() + "/external_sd");
                long blockSize = stat.getBlockSize();
                long totalBlocks = stat.getBlockCount();
                if (getTotalExternalMemorySize() != -1
                    && getTotalExternalMemorySize() != totalBlocks* blockSize) {
                    return totalBlocks * blockSize;
                } else {
                    return ERROR;
                }
            } else {
                return ERROR;
            }
 
        } else {
            return ERROR;
        }
    }
 
    /**
     * 判断SD卡下external_sd文件夹的可用大小
     * @return
     */
    public static long getAvailableExternal_SDMemorySize() {
        if (isSDCardExist()) {
            File path = Environment.getExternalStorageDirectory();
            File externalSD = new File(path.getPath() + "/external_sd");
            if (externalSD.exists() && externalSD.isDirectory()) {
                StatFs stat = new StatFs(path.getPath() + "/external_sd");
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                if (getAvailableExternalMemorySize() != -1
                    && getAvailableExternalMemorySize() != availableBlocks* blockSize) {
                    return availableBlocks * blockSize;
                } else {
                    return ERROR;
                }
 
            } else {
                return ERROR;
            }
 
        } else {
            return ERROR;
        }
    }
    
    /**
     * 获取多张SD卡路径
     * @param context
     * @return
     */
    @SuppressWarnings("all")
    public String[] getSDKS(Context context){
        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
		String[] paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
        return paths;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}	
        return null;
    }
    
}
