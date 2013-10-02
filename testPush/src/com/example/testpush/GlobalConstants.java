package com.example.testpush;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GlobalConstants {

	public static String REG_ID;
	public static String SENDER_ID="103052998040";//must obtain it from developers site of android.
	//http://developer.android.com/guide/google/gcm/index.html
	static final String INTENT_FILTER_GCM =
        "com.example.testpush.displayMessage";
	static final String CONTENT="data";
	
	/**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String msg) {
        Intent intent = new Intent(INTENT_FILTER_GCM);
        System.out.println("as");
        
        intent.putExtra(CONTENT,msg);
        context.sendBroadcast(intent);
    }
}
