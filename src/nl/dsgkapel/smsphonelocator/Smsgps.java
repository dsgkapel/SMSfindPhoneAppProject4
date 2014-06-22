package nl.dsgkapel.smsphonelocator;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Timer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Smsgps extends Service {

	public static boolean running = false;
	LocationManager lm;
	FileWriter coords;
	public static String latstring;
	public static String longstring;
	private static final String TAG = "coordinates";
	private Timer timer;
	String PROVIDER = LocationManager.GPS_PROVIDER;
	String code = "test";
	public static String deadstring;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = getApplicationContext();
		CharSequence text = "Service started";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		registerReceiver(receiver, filter);
		running = true;
		Location location = lm.getLastKnownLocation(PROVIDER);

		saveLoc(location);

		return START_STICKY;
	}

	public void saveLoc(Location l) {
		Context context = getApplicationContext();
		Log.v(TAG, "Method running");
		lm.requestLocationUpdates(PROVIDER, // provider
				0, // minTime in ms
				0, // minDistance in m
				ll); // LocationListener
		try {
			latstring = "Latitude: " + l.getLatitude();
			longstring = " Longitude: " + l.getLongitude();

			Log.v(TAG, latstring + longstring);

			if (!latstring.equals("null")) {
				Log.v(TAG, latstring + longstring);
				lm.removeUpdates(ll);
			}

			String txt = latstring + longstring;
			try {

				FileOutputStream fos;

				try {
					fos = new FileOutputStream("/storage/sdcard0/coord.txt",
							true);

					FileWriter fWriter;

					try {
						fWriter = new FileWriter(fos.getFD());
						fWriter.write(txt);
						fWriter.close();
						Log.v(TAG, txt);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						fos.getFD().sync();
						fos.close();
						Log.v(TAG, txt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} finally {

			}
		} catch (RuntimeException e) {
			Toast.makeText(context, "No Sim card detected, stopping service",
					Toast.LENGTH_SHORT).show();
			stopService(new Intent(this, Smsgps.class));
		}
	}

	public void onDestroy() {
		Context context = getApplicationContext();
		running = false;
		lm.removeUpdates(ll);
		CharSequence text = "Service stopped";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {

		String phonenr;
		String messageText = latstring + longstring;
		private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
		private static final String TAG = "SMSBroadcastReceiver";
		IntentFilter filter = new IntentFilter();
		SmsManager sms = SmsManager.getDefault();

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "Intent recieved: " + intent.getAction());

			if (intent.getAction() == SMS_RECEIVED) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {

					Object[] pdus = (Object[]) bundle.get("pdus");
					final SmsMessage[] messages = new SmsMessage[pdus.length];

					for (int i = 0; i < pdus.length; i++) {
						messages[i] = SmsMessage
								.createFromPdu((byte[]) pdus[i]);
						phonenr += messages[i].getOriginatingAddress();
					}
					if (messages.length > -1) {

						String messagebody = messages[0].getMessageBody();
						Log.i(TAG, "Message recieved: " + messagebody);
						CharSequence text = "SMS Received: " + messagebody;
						Toast.makeText(context, text, Toast.LENGTH_SHORT)
								.show();

						if (messagebody.equals(code)) {

							sms.sendTextMessage(phonenr, null, messageText,
									null, null);

						} else {
							clearAbortBroadcast();

						}

					}
				}
			}
		}
	};

	private LocationListener ll = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stubmessageNumber

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}