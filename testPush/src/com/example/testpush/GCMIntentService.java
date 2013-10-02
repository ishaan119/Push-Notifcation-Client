package com.example.testpush;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GCMIntentService extends GCMBaseIntentService{

	public GCMIntentService()
	{
		super(GlobalConstants.SENDER_ID);//very very important,if you don't include this
		//your app might crash.
	}
	@Override
	protected void onError(Context arg0, String arg1) {
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub	
		Bundle bundle=arg1.getExtras();
		String msg=bundle.getString("key");
		System.out.println("mess");
		//This will pass the message to the main activity via broadcast receiver.
		GlobalConstants.displayMessage(arg0,msg);
	}

	@Override
	protected void onRegistered(Context arg0, String regID) {
		// TODO Auto-generated method stub
		Log.v("Inside on registered", regID);
		try
		{
			GlobalConstants.REG_ID = regID;
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	protected void onUnregistered(Context arg0, String regID) {
		// TODO Auto-generated method stub
		Log.v("Inside on unregistered", regID);
		try
		{
			GlobalConstants.REG_ID = "";
		}
		catch(Exception ex)
		{
			
		}
		
	}
	}
	

	
