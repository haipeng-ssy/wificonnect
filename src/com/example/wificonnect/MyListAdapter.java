package com.example.wificonnect;

import java.util.List;
import java.util.Map;

import com.example.wificonnect.MainActivity.OnConnectWifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyListAdapter extends BaseAdapter {

	public static String NEED_SSID;
	Context mContext;
	Map<Integer,ScanResult> mMap;
	public MyListAdapter(Context context,Map<Integer,ScanResult> map){
		mContext = context;
		mMap = map;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMap.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		final int mPosition = position;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
			
			viewHolder.mTv_ssid = (TextView) convertView.findViewById(R.id.tv_ssid);
			viewHolder.mTv_level = (TextView) convertView.findViewById(R.id.tv_level);
			viewHolder.mBtn_connect = (Button) convertView.findViewById(R.id.btn_connect);
			convertView.setTag(viewHolder);
		}else{
			viewHolder= (ViewHolder) convertView.getTag();
		}
		
		viewHolder.mTv_ssid.setText(MainActivity.mc.map.get(position).SSID);
		viewHolder.mTv_level.setText(String.valueOf(MainActivity.mc.map.get(position).level));
		
		viewHolder.mBtn_connect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, mPosition+"", Toast.LENGTH_SHORT).show();
				NEED_SSID = MainActivity.mc.map.get(mPosition).SSID;
				onConnectWifi.tryConnect();
				
			}
		});
		return convertView;
	}

	public class ViewHolder{
		TextView mTv_ssid;
		TextView mTv_level;
		Button   mBtn_connect;
		
	}
	
	OnConnectWifi onConnectWifi;
	public void setInterfaceOnConnectWifi(OnConnectWifi onConnectWifi){
		this.onConnectWifi = onConnectWifi;
	}
	public interface OnConnectWifi{
		public boolean tryConnect();
	}
}
