package com.sagar.instafeed;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotosActivity extends Activity {

	private static final String CLIENT_ID = "fd8fac93a91f46148335eea211e8590f";
	private ArrayList<InstagramPhoto> photos;
	private InstagramPhotosAdapter aPhotos;
	 private SwipeRefreshLayout swipeContainer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photos);
		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			
			public void onRefresh() {
				// TODO Auto-generated method stub
				fetchPopularPhotos();		
			}
		});
		fetchPopularPhotos();	
		swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}

	private void fetchPopularPhotos() {
		
		photos = new ArrayList<InstagramPhoto>();
		// https://api.instagram.com/v1/media/popular?client_id=<<<client_id>>>
		
		//Create an Adapter and bind.
		aPhotos = new InstagramPhotosAdapter(this, photos);
		
		// Populate the data into list view.
		ListView listView = (ListView) findViewById(R.id.lvPhotos);
		listView.setAdapter(aPhotos);
		
		String instaURL = "https://api.instagram.com/v1/media/popular?client_id=fd8fac93a91f46148335eea211e8590f";
		
		
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(instaURL, new JsonHttpResponseHandler(){
			// success or failure 
			@Override
			public void onSuccess(int statusCode, Header[] headers,JSONObject response) {
				// TODO Auto-generated method stub
				super.onSuccess(statusCode, headers, response);
//				Log.i("INFO","Response success.."+response.toString());

				swipeContainer.setRefreshing(false);
				loadJSONData(response);
				
			}

			/**
			 * @param response
			 */
			private void loadJSONData(JSONObject response) {
				// URL, 
				// Data -->[x] -->"images"-->"Standard Resulation"-->"url"
				JSONArray photosJSON = new JSONArray();
				try
				{
					photos.clear();
					photosJSON = response.getJSONArray("data");
					for(int i = 0; i < photosJSON.length() ; i++)
					{
						InstagramPhoto photo = new InstagramPhoto();
						JSONObject photoJSON = photosJSON.getJSONObject(i);
						
						photo.username = photoJSON.getJSONObject("user").getString("username");
						photo.userProfilePicUrl = photoJSON.getJSONObject("user").getString("profile_picture");
						
						if (!photoJSON.isNull("caption") &&  photoJSON.getJSONObject("caption")!= null ){
							photo.caption = photoJSON.getJSONObject("caption").getString("text");
							photo.createdTime = photoJSON.getJSONObject("caption").getLong("created_time");
						}
							
						photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
						photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
						photo.likeCount = photoJSON.getJSONObject("likes").getInt("count");
						
						if (!photoJSON.isNull("location") &&  photoJSON.getJSONObject("location") != null ){
							photo.userLocation = photoJSON.getJSONObject("location").isNull("name")!=true?photoJSON.getJSONObject("location").getString("name"):"";
							photo.latitude = photoJSON.getJSONObject("location").isNull("latitude")!=true?photoJSON.getJSONObject("location").getDouble("latitude"):0;
							photo.longitude = photoJSON.getJSONObject("location").isNull("longitude")!=true?photoJSON.getJSONObject("location").getDouble("longitude"):0;
						}
						
						photos.add(photo);
					}
					//Notified the adapter
					aPhotos.notifyDataSetChanged();
					
				}catch(Exception  ex)
				{
					ex.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,String responseString, Throwable throwable) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseString, throwable);
				Log.i("INFO","Response Fail.."+responseString);
			}
			@Override
			public void onFailure(int statusCode, Header[] headers,Throwable throwable, JSONObject errorResponse) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, throwable, errorResponse);
				Log.i("INFO","Response Fail.."+throwable);
				String json = "{\"data\":[{\"tags\":[],\"location\":null,\"link\":\"http://instagram.com/p/s362v0BRn6/\",\"caption\":{\"id\":\"808373507485669842\",\"created_time\":\"1410585653\",\"text\":\"DASH\",\"from\":{\"id\":\"208560325\",\"profile_picture\":\"http://photos-d.ak.instagram.com/hphotos-ak-xpf1/10413196_279366092236675_1246478884_a.jpg\",\"username\":\"khloekardashian\",\"full_name\":\"Khloé\"}},\"type\":\"image\",\"id\":\"808373507024296442_208560325\",\"likes\":{\"data\":[{\"id\":\"223055874\",\"profile_picture\":\"http://photos-c.ak.instagram.com/hphotos-ak-xaf1/10684347_554928041279890_705153759_a.jpg\",\"username\":\"meganwhitecross\",\"full_name\":\"meganwhitecross\"},{\"id\":\"182965411\",\"profile_picture\":\"http://photos-f.ak.instagram.com/hphotos-ak-xaf1/10561170_669636779797285_810467139_a.jpg\",\"username\":\"josiemugridge1\",\"full_name\":\"josie\"},{\"id\":\"293868573\",\"profile_picture\":\"http://photos-f.ak.instagram.com/hphotos-ak-xaf1/10661019_698605166854861_410348683_a.jpg\",\"username\":\"mayaverma\",\"full_name\":\"maya\"},{\"id\":\"364039815\",\"profile_picture\":\"http://photos-f.ak.instagram.com/hphotos-ak-xaf1/10665625_1466254773647253_1807416627_a.jpg\",\"username\":\"x_aileen_\",\"full_name\":\"x_aileen_\"}],\"count\":162288},\"images\":{\"low_resolution\":{\"url\":\"http://scontent-b.cdninstagram.com/hphotos-xaf1/t51.2885-15/10654920_1530835733795093_390309992_a.jpg\",\"height\":306,\"width\":306},\"standard_resolution\":{\"url\":\"http://scontent-b.cdninstagram.com/hphotos-xaf1/t51.2885-15/10654920_1530835733795093_390309992_n.jpg\",\"height\":640,\"width\":640},\"thumbnail\":{\"url\":\"http://scontent-b.cdninstagram.com/hphotos-xaf1/t51.2885-15/10654920_1530835733795093_390309992_s.jpg\",\"height\":150,\"width\":150}},\"users_in_photo\":[],\"created_time\":\"1410585653\",\"user\":{\"id\":\"208560325\",\"profile_picture\":\"http://photos-d.ak.instagram.com/hphotos-ak-xpf1/10413196_279366092236675_1246478884_a.jpg\",\"username\":\"khloekardashian\",\"bio\":\"\",\"website\":\"\",\"full_name\":\"Khloé\"},\"filter\":\"Normal\",\"comments\":{\"data\":[{\"id\":\"808430705024768380\",\"created_time\":\"1410592471\",\"text\":\"Lb\",\"from\":{\"id\":\"1464420740\",\"profile_picture\":\"http://photos-a.ak.instagram.com/hphotos-ak-xaf1/10608078_272527406269112_445019912_a.jpg\",\"username\":\"ornellamsr\",\"full_name\":\"Ornella Msr\"}},{\"id\":\"808430715644746110\",\"created_time\":\"1410592472\",\"text\":\"Lol @dashkathuberoucayrol\",\"from\":{\"id\":\"683203524\",\"profile_picture\":\"http://photos-c.ak.instagram.com/hphotos-ak-xfp1/10535139_335178313312010_705281568_a.jpg\",\"username\":\"andreabrnj\",\"full_name\":\"Andrea|13|Bordeaux|Chloe ^^\"}},{\"id\":\"808430901922175372\",\"created_time\":\"1410592495\",\"text\":\"@the.frontrow hahaha??\",\"from\":{\"id\":\"1191023205\",\"profile_picture\":\"http://images.ak.instagram.com/profiles/profile_1191023205_75sq_1399286933.jpg\",\"username\":\"evyhaantje\",\"full_name\":\"Evy Luyckx\"}},{\"id\":\"808431050668972434\",\"created_time\":\"1410592512\",\"text\":\"KIM AND KHLOE FOLLOWED 'KARDASHIAN_VINE' CHECK MY LAST PHOTO, BEST ACCOUNT EVER????????\",\"from\":{\"id\":\"1491422963\",\"profile_picture\":\"http://photos-f.ak.instagram.com/hphotos-ak-xap1/928365_818840781480189_159540746_a.jpg\",\"username\":\"madisonbeer_19990\",\"full_name\":\"Madison Beer\"}},{\"id\":\"808431104842602904\",\"created_time\":\"1410592519\",\"text\":\"Bull^^^\",\"from\":{\"id\":\"896076863\",\"profile_picture\":\"http://images.ak.instagram.com/profiles/profile_896076863_75sq_1388506710.jpg\",\"username\":\"queenofsassxo\",\"full_name\":\"Izzy Oakley\"}},{\"id\":\"808431190171523484\",\"created_time\":\"1410592529\",\"text\":\"@c_kapa @kourtneykardash @khloekardashian @kimkardashian SECOND THAT!\",\"from\":{\"id\":\"233007622\",\"profile_picture\":\"http://images.ak.instagram.com/profiles/profile_233007622_75sq_1384504435.jpg\",\"username\":\"gaalindsay\",\"full_name\":\"18 ? New Zealand\"}},{\"id\":\"808431355812977059\",\"created_time\":\"1410592549\",\"text\":\"@paigeeyybabeyy I wish I worked for this dash! ??\",\"from\":{\"id\":\"321407623\",\"profile_picture\":\"http://photos-e.ak.instagram.com/hphotos-ak-xfa1/10598756_1457255791220924_204216480_a.jpg\",\"username\":\"lisarosemccann\",\"full_name\":\"Lisa McCann\"}},{\"id\":\"808431687917967800\",\"created_time\":\"1410592588\",\"text\":\"Haha  @lisarosemccann\",\"from\":{\"id\":\"645987592\",\"profile_picture\":\"http://photos-a.ak.instagram.com/hphotos-ak-xaf1/10693774_778847315491656_238690142_a.jpg\",\"username\":\"paigeeyybabeyy\",\"full_name\":\"Paige??nicole?? Hedges\"}}],\"count\":692},\"attribution\":null}]}";
				try {
					JSONObject response = new JSONObject(json);
					loadJSONData(response);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photos, menu);
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
}
