package com.sagar.instafeed;

import android.text.format.DateUtils;

public class InstagramPhoto {

	String username, caption, imageUrl, userProfilePicUrl, userLocation;
	int imageHeight,likeCount;
	long createdTime;
	double latitude,longitude;
	
	public String getCreatedtime()
	{
		return DateUtils.getRelativeTimeSpanString(createdTime,System.currentTimeMillis(),DateUtils.FORMAT_ABBREV_RELATIVE).toString();
	}
	
}
