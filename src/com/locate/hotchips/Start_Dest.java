package com.locate.hotchips;

import com.locate.hotchips.R;

public class Start_Dest {
	
	private String lat;
	private String lng;
	
	Start_Dest(String lat,String lng)
	{
		this.lat = lat;
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

}
