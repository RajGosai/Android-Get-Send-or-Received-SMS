package raj.getsendmsg;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class SmsSentObserver extends ContentObserver {

	private static final String TAG = "SMSTRACKER";
	private static final Uri STATUS_URI = Uri.parse("content://sms");
	boolean isSend = false;
	private Context mContext;

	public SmsSentObserver(Handler handler, Context ctx) {
		super(handler);
		mContext = ctx;
		isSend = true;
	}

	public boolean deliverSelfNotifications() {
		return true;
	}

	public void onChange(boolean selfChange) {
		if (isSend) {

			try {
				// ---Get Time---
				Calendar cal = Calendar
						.getInstance(TimeZone.getTimeZone("GMT"));
				Date currentLocalTime = cal.getTime();
				DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
				date.setTimeZone(TimeZone.getTimeZone("GMT"));
				String localTime = date.format(currentLocalTime);

				Log.i("onChange", "onChange.................");
				Cursor sms_sent_cursor = mContext.getContentResolver().query(
						STATUS_URI, null, null, null, null);
				if (sms_sent_cursor != null) {
					if (sms_sent_cursor.moveToFirst()) {
						String protocol = sms_sent_cursor
								.getString(sms_sent_cursor
										.getColumnIndex("protocol"));
						Log.e(TAG, "protocol : " + protocol);
						if (protocol == null) {
							// String[] colNames =
							// sms_sent_cursor.getColumnNames();
							int type = sms_sent_cursor.getInt(sms_sent_cursor
									.getColumnIndex("type"));
							Log.e(TAG, "SMS Type : " + type);
							if (type == 2) {
								Log.i("InSendLoop", "InSendLoop");

								// Log.i(TAG,
								// "Id : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("_id")));
								// Log.i(TAG,
								// "Thread Id : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("thread_id")));
								Log.i(TAG,
										"Address : "
												+ sms_sent_cursor
														.getString(sms_sent_cursor
																.getColumnIndex("address")));

								Log.i(TAG,
										"Body : "
												+ sms_sent_cursor
														.getString(sms_sent_cursor
																.getColumnIndex("body")));
								Log.i(TAG, "Date : " + localTime);
								Log.i(TAG, "Type : " + "Send Message");
								// Log.i(TAG,
								// "Person : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("person")));

								// Log.i(TAG,
								// "Read : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("read")));
								// Log.i(TAG,
								// "Status : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("status")));
								// Log.i(TAG,
								// "Type : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("type")));
								// Log.i(TAG,
								// "Rep Path Present : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("reply_path_present")));
								// Log.i(TAG,
								// "Subject : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("subject")));
								Log.i(TAG,
										"Body : "
												+ sms_sent_cursor
														.getString(sms_sent_cursor
																.getColumnIndex("body")));
								// Log.i(TAG,
								// "Err Code : "
								// + sms_sent_cursor
								// .getString(sms_sent_cursor
								// .getColumnIndex("error_code")));
								/*
								 * if(colNames != null){ for(int k=0;
								 * k<colNames.length; k++){ Log.e(TAG,
								 * "colNames["+k+"] : " + colNames[k]); } }
								 */
								isSend = false;
							}
						}
					}
				} else
					Log.i(TAG, "Send Cursor is Empty");
			} catch (Exception sggh) {
				Log.i(TAG, "Error on onChange : " + sggh.toString());
			}

		}
		super.onChange(selfChange);
	}// fn onChange

}// End of class SmsSentObserver