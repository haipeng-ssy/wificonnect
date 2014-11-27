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
	 * Wifi.getScanResults(); // ���аѰ�����BSSID��SSID��capabilities��frequencyƵ�ʡ�level�ź�ǿ��
	 * WifiConfiguration //����wifi��������Ϣ������SSID��SSID���ء�Password�ȵ����á�
	 * */

	/***
	 * 
	 * 2.WifiConfiguration
	 * 
	 * Wifi��������ã�������ȫ���õȡ�
	 * 
	 * 3.WifiInfo
	 * 
	 * wifi�������ӵ���������������㣬��������״̬�����صĽ���㣬IP��ַ�������ٶȣ�MAC��ַ������ID���ź�ǿ�ȵ���Ϣ��
	 * ����򵥽���һ������ķ���:
	 * 
	 * getBSSID() ��ȡBSSID
	 * 
	 * getDetailedStateOf() ��ȡ�ͻ��˵���ͨ��
	 * 
	 * getHiddenSSID() ���SSID �Ƿ�����
	 * 
	 * getIpAddress() ��ȡIP ��ַ
	 * 
	 * getLinkSpeed() ������ӵ��ٶ�
	 * 
	 * getMacAddress() ���Mac ��ַ
	 * 
	 * getRssi() ���802.11n ������ź�
	 * 
	 * getSSID() ���SSID
	 * 
	 * getSupplicanState() ���ؾ���ͻ���״̬����Ϣ
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

	// ��wifi
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

	// ����һ��wifiLock
	public void createWifiLock() {
		mWifiLock = mWifiManager.createWifiLock("test");

	}

	// �õ����úõ�����
	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfigurations;
	}

	// ָ�����úõ������������
	public void connectionConfiguration(int index) {
		if (index > mWifiConfigurations.size()) {
			return;
		}
		// �������úõ�ID������
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
			// ���аѰ�����BSSID��SSID��capabilities��frequency��level
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

	// �õ����ӵ�ID
	public int getNetWordId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

	// �õ�wifiInfo��������Ϣ
	public String getWifiInfo() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
	}

	// ���һ�����粢����
	public boolean addNetWork(WifiConfiguration configuration) {
		int wcgId = mWifiManager.addNetwork(configuration);
		boolean result = mWifiManager.enableNetwork(wcgId, true);
		return result;
	}

	// �Ͽ�ָ��ID������
	public void disConnectionWifi(int netId) {
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}

}
