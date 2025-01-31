package com.madhav.iShoutStudent;
import android.annotation.SuppressLint;
import android.content.*;
import android.net.wifi.*;
import java.lang.reflect.*;

public class ApManager {

//check whether wifi hotspot on or off
public static boolean isApOn(Context context) {
    WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);     
    try {
        Method method = wifimanager.getClass().getDeclaredMethod("isWifiApEnabled");
        method.setAccessible(true);
        return (Boolean) method.invoke(wifimanager);
    }
    catch (Throwable ignored) {}
    return false;
}

// toggle wifi hotspot on or off
@SuppressLint("NewApi")
public static boolean configApState(Context context , String str) {
    WifiManager wifimanager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
    WifiConfiguration wificonfiguration = new WifiConfiguration();
    	
    	if(str.isEmpty() == true)
    	{
    		str="Roll no Not Present";
    	}
    	wificonfiguration.SSID = str;
    	
    	
    try {  
        // if WiFi is on, turn it off
        if(isApOn(context)) {               
            wifimanager.setWifiEnabled(false);
        }    
        
        Method method = wifimanager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);                   
        method.invoke(wifimanager, wificonfiguration, !isApOn(context));
        return true;
    } 
    catch (Exception e) {
        e.printStackTrace();
    }       
    return false;
}
} // end of class