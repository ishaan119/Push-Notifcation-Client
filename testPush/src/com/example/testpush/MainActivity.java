package com.example.testpush;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMBroadcastReceiver;
import com.google.android.gcm.GCMRegistrar;
import static  com.example.testpush.GlobalConstants.INTENT_FILTER_GCM;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testpush.*;
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	Context appContext;
	Button unreg,reg,sendMail;
	EditText sender_id;
	CheckBox ch1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = (Button)findViewById(R.id.button1);
        unreg = (Button)findViewById(R.id.button2);
        sendMail =(Button)findViewById(R.id.button3);
        sender_id = (EditText)findViewById(R.id.editText1);
        this.appContext=this.getApplicationContext();
        this.registerReceiver(IshaanHandleMessageReceiver, new IntentFilter(INTENT_FILTER_GCM));
        GlobalConstants.REG_ID = GCMRegistrar.getRegistrationId(appContext);
        //****************************************
        //this.registerDevice();
        
        unreg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				unregisterGCMDevice();
				
			}
		});
        
        reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				registerDevice();
			}
		});
        sendMail.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				sendRegIdEmail();
			}
		});
    }
    
    private void registerDevice() {
		GCMRegistrar.checkDevice(appContext);
        GCMRegistrar.checkManifest(appContext);
        if (GlobalConstants.REG_ID.equals("")) {
          sender_id = (EditText)findViewById(R.id.editText1);
          GCMRegistrar.register(appContext, "103052998040");
          ch1 = (CheckBox)findViewById(R.id.checkBox1);
          ch1.setChecked(true);
        } else {
       	    Toast.makeText(getApplicationContext(), "The device is already registered", Toast.LENGTH_LONG).show();
        	Log.d("registerDevice", "Already registered.") ;
        }
	}
    @Override
    protected void onDestroy() {
        unregisterReceiver(IshaanHandleMessageReceiver);
        GCMRegistrar.onDestroy(this.appContext);//the same object context must be passed.
		//Should be the one with what it was created.Nor app will crash.Don't use only 'this'
        super.onDestroy();
    }

    private final BroadcastReceiver IshaanHandleMessageReceiver =
        new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
			String msg=intent.getStringExtra(GlobalConstants.CONTENT);
			NotificationManager notifier = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

            //Get the icon for the notification
            int icon = R.drawable.ic_launcher;
			Notification notification = new Notification();
            notification.icon = icon;
            notification.tickerText = "Ayla Networks";
            notification.when = System.currentTimeMillis();

            //Setup the Intent to open this Activity when clicked
            Intent toLaunch = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, toLaunch, 0);

            //Set the Notification Info
            notification.setLatestEventInfo(context, "Ayla", "Property has been updated", contentIntent);
            notifier.notify(100, notification);

		}
    };
    
    public void registerGCMDevice(){
    	
    }
    
    public void unregisterGCMDevice(){
    	 GCMRegistrar.unregister(this);
    	 ch1 = (CheckBox)findViewById(R.id.checkBox1);
         ch1.setChecked(false);
    	 Toast.makeText(getApplicationContext(), "Unregistered the device", Toast.LENGTH_LONG).show();
    }
    
    public void sendRegIdEmail(){
    	String [] emailAddress = {"ishaan.sutaria@aylanetworks.com"};
        String emailSubject = "Registration Id";
        String emailMessage = GlobalConstants.REG_ID;
        // below is the code for sending an email
        Intent emailIntent = new Intent(
                android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                emailAddress);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                emailSubject);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                emailMessage);
        startActivity(emailIntent);
    }

    
/* @Override
    protected void onPause() {
    super.onPause();
    GCMRegistrar.unregister(this);
    }*/
    

}