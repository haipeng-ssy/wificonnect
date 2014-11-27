package com.example.wificonnect;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

public class WifiAdmin {

	/**
	 * WifiManager 
	 * WifiInfo, // MacAddress,BSSID,IpAddress,NetWordId,  
	 * Wifi.getScanResults(); // 其中把包括：BSSID、SSID、capabilities、frequency频率、level信号强度
	 * WifiConfiguration //描述wifi的连接信息，包含SSID、SSID隐藏、Password等的设置。
	 * */

	/***
	 * 
	 * 2.WifiConfiguration
	 * 
	 * Wifi网络的配置，包括安全设置等。
	 * 
	 * 3.WifiInfo
	 * 
	 * wifi无线连接的描述，包括接入点，网络连接状态，隐藏的接入点，IP地址，连接速度，MAC地址，网络ID，信号强度等信息。
	 * 这里简单介绍一下这里的方法:
	 * 
	 * getBSSID() 获取BSSID
	 * 
	 * getDetailedStateOf() 获取客户端的连通性
	 * 
	 * getHiddenSSID() 获得SSID 是否被隐藏
	 * 
	 * getIpAddress() 获取IP 地址
	 * 
	 * getLinkSpeed() 获得连接的速度
	 * 
	 * getMacAddress() 获得Mac 地址
	 * 
	 * getRssi() 获得802.11n 网络的信号
	 * 
	 * getSSID() 获得SSID
	 * 
	 * getSupplicanState() 返回具体客户端状态的信息
	 * */
	private WifiManager mWifiManager;
	private WifiInfo mWifiInfo;
	private List<ScanResult> mWifiList;
	private List<WifiConfiguration> mWifiConfigurations;
	WifiLock mWifiLock;

	public WifiAdmin(Context context) {
		mWifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
	}

	// 打开wifi
	public void openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	// close wifi
	public void closeWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	// check current wifi state
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	// lock wifi
	public void acquireWifiLock() {
		mWifiLock.acquire();
	}

	// unlock wifi
	public void releaseWifiLock() {
		if (mWifiLock.isHeld()) {
			mWifiLock.acquire();
		}
	}

	// 创建一个wifiLock
	public void createWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("test");

	}

	// 得到配置好的网络
	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfigurations;
	}

	// 指定配置好的网络进行连接
	public void connectionConfiguration(int index) {
		if (index > mWifiConfigurations.size()) {
			return;
		}
		// 连接配置好的ID的网络
		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId,
				true);
	}

	public void startScan() {
		mWifiManager.startScan();
		mWifiList = mWifiManager.getScanResults();
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();
	}

	public List<ScanResult> getWifiList() {
		return mWifiList;
	}

	public StringBuffer lookUpScan() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mWifiList.size(); i++) {
			sb.append("Index_" + new Integer(i + 1) + ":");
			// 其中把包括：BSSID、SSID、capabilities、frequency、level
			sb.append(mWifiList.get(i).toString()).append("\n");
		}
		return sb;
	}

	/**
	 * mWifiInfo
	 * */
	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	// 得到连接的ID
	public int getNetWordId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	// 得到wifiInfo的所有信息
	public String getWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// 添加一个网络并连接
	public boolean addNetWork(WifiConfiguration configuration) {
		int wcgId = mWifiManager.addNetwork(configuration);
		boolean result = mWifiManager.enableNetwork(wcgId, true);
		return result;
	}

	// 断开指定ID的网络
	public void disConnectionWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

}
