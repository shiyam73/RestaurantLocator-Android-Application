package com.locate.hotchips;

import java.util.ArrayList;

import com.locate.hotchips.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


public class HotelListBaseAdapter extends BaseAdapter {

private static ArrayList<HotelDetails> hotelDetailsrrayList;
	
	
	
	private LayoutInflater l_Inflater;

	public HotelListBaseAdapter(Context listViewActivity, ArrayList<HotelDetails> results) {
		hotelDetailsrrayList = results;
		l_Inflater = LayoutInflater.from(listViewActivity);
	}

	public int getCount() {
		return hotelDetailsrrayList.size();
	}

	public Object getItem(int position) {
		return hotelDetailsrrayList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.item_details_view, null);
			holder = new ViewHolder();
			holder.txt_branch = (TextView) convertView.findViewById(R.id.branch);
			holder.txt_address = (TextView) convertView.findViewById(R.id.address);
			

			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.txt_branch.setText(hotelDetailsrrayList.get(position).getName());
		holder.txt_address.setText(hotelDetailsrrayList.get(position).getItemDescription());
		
		return convertView;
	}

	static class ViewHolder {
		TextView txt_branch;
		TextView txt_address;
		
	}

	
	
}
