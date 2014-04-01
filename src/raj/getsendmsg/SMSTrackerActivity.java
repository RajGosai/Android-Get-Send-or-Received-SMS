package raj.getsendmsg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSTrackerActivity extends BroadcastReceiver {
	private Context mContext;
	private Bundle mBundle;
	private Intent mIntent;

	private static final String TAG = "SMSTRACKER";
	private static final Uri STATUS_URI = Uri.parse("content://sms");

	private SmsSentObserver smsSentObserver = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		try{
			mContext = context;
			mIntent = intent;
			mBundle = intent.getExtras();  
			Log.i(TAG, "Intent Action : "+intent.getAction());
		    if (mBundle != null){
		    	getSMSDetails();
		    }
		    else
		    	Log.e(TAG, "Bundle is Empty!");
		    
		    if(smsSentObserver == null){
			    smsSentObserver = new SmsSentObserver(new Handler(), mContext);
			    mContext.getContentResolver().registerContentObserver(STATUS_URI, true, smsSentObserver);
		    }
		}
		catch(Exception sgh){
			Log.e(TAG, "Error in Init : "+sgh.toString());
		}
	}//fn onReceive

	private void getSMSDetails(){	     
	    SmsMessage[] msgs = null;
		try{
			Object[] pdus = (Object[]) mBundle.get("pdus");
			if(pdus != null){
				msgs = new SmsMessage[pdus.length];
				Log.e(TAG, "pdus length : "+pdus.length);
				for(int k=0; k<msgs.length; k++){
					msgs[k] = SmsMessage.createFromPdu((byte[])pdus[k]);  
					Log.i(TAG, "getDisplayMessageBody : "+msgs[k].getDisplayMessageBody());
					Log.i(TAG, "getDisplayOriginatingAddress : "+msgs[k].getDisplayOriginatingAddress());
					Log.i(TAG, "getMessageBody : "+msgs[k].getMessageBody());
					Log.i(TAG, "getOriginatingAddress : "+msgs[k].getOriginatingAddress());
					Log.i(TAG, "getProtocolIdentifier : "+msgs[k].getProtocolIdentifier());
					Log.i(TAG, "getStatus : "+msgs[k].getStatus());
					Log.i(TAG, "getStatusOnIcc : "+msgs[k].getStatusOnIcc());
					Log.i(TAG, "getStatusOnSim : "+msgs[k].getStatusOnSim());
				}
			}
		}
		catch(Exception sfgh){
			Log.e(TAG, "Error in getSMSDetails : "+sfgh.toString());
		}
	}//fn getSMSDetails
	
	
}//End of class SMSTrackerActivity
