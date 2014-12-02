package com.example.wificonnect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.wificonnect.MyListAdapter.OnConnectWifi;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.storage.OnObbStateChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnConnectWifi{

	private TextView allNetWork;    
    private Button scan;    
    private Button start;    
    private Button stop;    
    private Button check;  
    public WifiAdmin mWifiAdmin;
    public static Map<Integer,ScanResult> map;
    List<ScanResult> list;
    private ScanResult mScanResult;
    private StringBuffer sb = new StringBuffer();
    private ListView myListView;
    private MyListAdapter myListAdapter;
    static MainActivity mc;
    public boolean isConnected = false;
    int s[] = {0,1,2,3,4,5,6,7,8,9};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mWifiAdmin = new WifiAdmin(this);
		mc = this;
		init();
	}
	public void init(){  
        allNetWork = (TextView) findViewById(R.id.allNetWork);    
        scan = (Button) findViewById(R.id.scan);    
        start = (Button) findViewById(R.id.start);    
        stop = (Button) findViewById(R.id.stop);    
        check = (Button) findViewById(R.id.check);  
        myListView = (ListView) findViewById(R.id.listview_wifiConnect);
        scan.setOnClickListener(new MyListener());    
        start.setOnClickListener(new MyListener());    
        stop.setOnClickListener(new MyListener());    
        check.setOnClickListener(new MyListener());    
    }  
	public class MyListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.scan:
			    getAllNetWorkList();
				break;
			case R.id.start:
				mWifiAdmin.openWifi();
				break;
			case R.id.stop:
				mWifiAdmin.closeWifi();
				break;
			case R.id.check:
				 Toast.makeText(MainActivity.this, "当前wifi状态为："+mWifiAdmin.checkState(), 1).show();  
				break;
				
			default:
				break;
			}
		}
		
	}
	
	public void getAllNetWorkList(){
	
		if(sb!=null)
		{
			sb = new StringBuffer();
		}
		mWifiAdmin.startScan();
		list = mWifiAdmin.getWifiList();
		map = new HashMap<Integer,ScanResult>();
		for(int i=0;i<list.size();i++)
		{
			map.put(i, list.get(i));
		}

		myListAdapter = new MyListAdapter(MainActivity.this, map);
		myListAdapter.setInterfaceOnConnectWifi(this);
		myListView.setAdapter(myListAdapter);
		setListViewHeightBasedOnChildren(myListView);
//		if(list!=null)
//		{
//			for(int i=0;i<list.size();i++)
//			{
//				mScanResult = list.get(i);
//				sb = sb.append("SSID="+mScanResult.SSID+"\n").append("信号强度"+mScanResult.level).append("加密类型"+mScanResult.capabilities).append("\n");
//			}
//			allNetWork.setText("wifi网络  \n"+sb.toString());
//		}
	}
	/**
	* 动态设置ListView的高度
	* @param listView
	*/
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
	    if(listView == null) return;

	    ListAdapter listAdapter = listView.getAdapter(); 
	    if (listAdapter == null) { 
	        // pre-condition 
	        return; 
	    } 

	    int totalHeight = 0; 
	    for (int i = 0; i < listAdapter.getCount(); i++) { 
	        View listItem = listAdapter.getView(i, null, listView); 
	        listItem.measure(0, 0); 
	        totalHeight += listItem.getMeasuredHeight(); 
	    } 

	    ViewGroup.LayoutParams params = listView.getLayoutParams(); 
	    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
	    listView.setLayoutParams(params); 
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean tryConnect() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int i=0;
				while(isConnected==false&&i<100)
				{
					i++;
					System.out.println(i);
					boolean result =MainActivity.mc.mWifiAdmin.addNetWork(MyWifiConfiguration.getWifiConfigutaion(MyListAdapter.NEED_SSID, i+""));	
					if(result)
					{
						isConnected = true;
					}else{
						isConnected = false;
					}
				}
			}
		}).start();
		
		return false;
	}
}
