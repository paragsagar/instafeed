package com.sagar.instafeed;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
 public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> photos){
	 super(context, R.layout.photo_list,photos);
 }
 
 @Override
	public View getView(int position, View convertedView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	 InstagramPhoto photo = getItem(position);
	 if(convertedView == null)
	 {
		 convertedView = LayoutInflater.from(getContext()).inflate(R.layout.photo_list, parent,false);
	 }
	 
	 TextView tvUserName = (TextView )convertedView.findViewById(R.id.tvUserName);
	 TextView tvUserNameCaption = (TextView )convertedView.findViewById(R.id.tvUsername_caption);
	 TextView tvLikeCount = (TextView )convertedView.findViewById(R.id.tvLikeCount);
	 ImageView imgUserProfile = (ImageView)convertedView.findViewById(R.id.ivUserProfileImage);
	 ImageView imgInstaPhoto = (ImageView)convertedView.findViewById(R.id.ivInstaPhoto);
	 TextView tvCaption = (TextView )convertedView.findViewById(R.id.tvCaption);
	 TextView tvUserLocation = (TextView )convertedView.findViewById(R.id.tvUserLocation);
	 TextView tvDateStamp = (TextView )convertedView.findViewById(R.id.tvTimeStamp);
	 
	 
	 tvUserName.setText(photo.username);
	 tvUserNameCaption.setText(photo.username);
	 tvCaption.setText(photo.caption);
	 tvUserLocation.setText(photo.userLocation);
	 tvDateStamp.setText(photo.getCreatedtime());
	 tvLikeCount.setText(photo.likeCount+" likes");
	 
//	 imgUserProfile.getLayoutParams().height = photo.imageHeight;
	 imgUserProfile.setImageResource(0);
	 Picasso.with(getContext()).load(photo.userProfilePicUrl).into(imgUserProfile);
	 
	 imgInstaPhoto.getLayoutParams().height = photo.imageHeight;
	 imgInstaPhoto.setImageResource(0);
	 Picasso.with(getContext()).load(photo.imageUrl).into(imgInstaPhoto);
	 
	 
	 return convertedView;

 }
}
