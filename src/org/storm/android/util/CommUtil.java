package org.storm.android.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.conn.util.InetAddressUtils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

public class CommUtil {
    /**
     * 获取屏幕宽度高度密度
     * 
     *  如果屏幕密度低,需要在工程的AndroidManifest.xml文件中，加入supports-screens节点 
     * <supports-screens
        android:smallScreens="true"
        android:normalScreens="true"
        android:largeScreens="true"
        android:resizeable="true"
        android:anyDensity="true"/>
     * @param activity 屏幕界面
     * @param type     类型1:宽度,2:高度,3:密度,4dpi密度
     * @return 屏幕宽度或高度或密度
     */
    public Object getWindowSize(Activity activity,int type){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        if (type == 1) {
            int width = metric.widthPixels;  // 屏幕宽度（像素）
            return width;
        }else if (type == 2) {
            int height = metric.heightPixels;  // 屏幕高度（像素）
            return height;
        }else if (type == 3) {
            float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
            return density;
        }else if (type == 4) {
            int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
            return densityDpi;
        }
        return 0;
    }
     
    /**
     * 获取MAC地址    
     * @param context 上下文
     * @param replaceSymbol 替换字符,默认替换字符为""
     * @return 返回MAC地址     错误返回12个0
     */
    public String getMacAddress(Context context,String replaceSymbol) {
        String macAddress = "000000000000";
        if (replaceSymbol == null) {
            replaceSymbol = "";
        }
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", replaceSymbol);
                else
                    return macAddress;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }
     
    /**
     *  获取当前时间
     * @param type 日期时间格式
     * @param locale 地区默认为 Locale.CHINA
     * @return 按照格式返回当前时间
     */
    public String getCurrentTime(String type,Locale locale) {
        if (locale == null) {
            locale = Locale.CHINA;
        }
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(type,locale);
        return sdf.format(curDate);
    }
     
    /**
     * 日期格式转换
     * 
     * @param date 待转换日期
     * @param type 格式
     * @param locale 地区 默认为 Locale.CHINA
     * @return 日期
     */
    public String formatDate(String date, String type,Locale locale) {
        String fmDate = "";
        if (date != null) {
            if (locale == null) {
                locale = Locale.CHINA;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(type,locale);
            fmDate = sdf.format(new Date(Long.parseLong(date)));
        }
        return fmDate;
    }
     
    /**
     * 获取当前版本名,版本号
     * @param context 上下文
     * @param type 1:版本名称,2:版本号
     * @return 版本名或版本号
     */
    public Object getCurrentVersionName(Context context,int type){
        PackageManager manager = context.getPackageManager();
        String packageName = context.getPackageName();
        String versionName = null;
        int versionCode = 0;
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            if (type == 1) {
                versionName = info.versionName;
                return versionName;
            }else if (type == 2) {
                versionCode = info.versionCode;
                return versionCode;
            }
             
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
     
    /**
     * 网络检测 
     * @param context 上下文
     * @return false:无网络,true:有网络
     */
    public boolean isOnline(Context context) {
        boolean isOnline = false;
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            isOnline = networkInfo.isAvailable();
        }
        // String netType = "当前网络类型为：" + networkInfo.getTypeName();
        return isOnline;
    }
    /**
     * 比较时间
     * 
     * @return true courseTime 大于当前时间
     */
    public boolean compareTime(String curTime, String courseTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
        boolean boo = true;
        try {
            boo = sdf.parse(courseTime).getTime()
                    - sdf.parse(curTime).getTime() > 0;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return boo;
    }
    /**
     * 获取有网线下的Ip地址
     * 需要添加权限:<uses-permission android:name="android.permission.INTERNET" />
     * @param context 上下文
     * @return IP地址
     */
    public String getWXLocalIpAddress(Context context) {
        String ipv4 = "0.0.0.0";
        try {
            boolean boo = true;
            List<NetworkInterface> nilist = Collections.list(NetworkInterface
                    .getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                List<InetAddress> ialist = Collections.list(ni
                        .getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress()
                            && InetAddressUtils.isIPv4Address(ipv4 = address
                                    .getHostAddress())) {
                        boo = false;
                        break;
                    }
                    if (!boo) {
                        break;
                    }
                }
 
            }
 
        } catch (SocketException ex) {
            Log.e("WangLuo", ex.toString());
        }
        return ipv4;
    }
     
    /**
     * 获取Wifi下的Ip地址
     * 需要添加权限: <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     * @param context 上下文
     * @return IP地址
     */
    public String getWifiLocalIpAddress(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        int ipAddress = info.getIpAddress();
        return intToIp(ipAddress);
    }
    private String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + ((i >> 24) & 0xFF);
 
    }
    /**
     * SD卡是否存在
     * @return
     */
    public boolean isSDexist(){
        //SD卡是否存在
        boolean isExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        return isExist;
    }
 
    /**
     * 获取网路连接类型
     * @param context 上下文
     * @return 网络类型
     * 需要添加权限<uses-permission android:name="android.permission.INTERNET"/>
     * 需要添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public String getNetType(Context context){
        ConnectivityManager conn = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        String result = null;
        if (info != null && info.isAvailable()) {
            if (info.isConnected()) {
                int type = info.getType();
                String typeName = info.getTypeName();
                switch (type) {
                case ConnectivityManager.TYPE_BLUETOOTH:
                    result = "蓝牙连接   :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_DUMMY:
                    result = "虚拟数据连接    :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_ETHERNET:
                    result = "以太网数据连接    :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    result = "移动数据连接   : "+typeName;
                    break;
                case ConnectivityManager.TYPE_MOBILE_DUN:
                    result = "网络桥接 :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_MOBILE_HIPRI:
                    result = "高优先级的移动数据连接 :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_MOBILE_MMS:
                    result = "运营商的多媒体消息服务  : "+typeName;
                    break;
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                    result = "平面定位特定移动数据连接  :  "+typeName;
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    result = "Wifi数据连接   : "+typeName;
                    break;
                case ConnectivityManager.TYPE_WIMAX:
                    result = "全球微波互联   : "+typeName;
                    break;
                default:
                    break;
                }
            }
        }
        return result;
    }
}
