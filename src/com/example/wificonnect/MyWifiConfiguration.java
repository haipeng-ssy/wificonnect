package com.example.wificonnect;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;

public class MyWifiConfiguration {
    public static WifiConfiguration  getWifiConfigutaion(String SSID,String password){
    	WifiConfiguration wc = new WifiConfiguration();
    	wc.SSID = SSID;
    	wc.preSharedKey = password;
    	wc.hiddenSSID = true;
    	wc.status = WifiConfiguration.Status.ENABLED;  
    	wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);  
    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);  
    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);  
    	wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);  
    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);  
    	wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);  
    	
    	return wc;
    }
    
    public WifiConfiguration getNoPasswordWifi(String SSID){
    	WifiConfiguration wc = new WifiConfiguration();
    	wc.SSID = SSID;
    	wc.allowedKeyManagement.set(KeyMgmt.NONE);
        return wc;
    }
    
    
}
