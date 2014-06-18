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
		registerReceiver(receiver, filter);
		running = true;
		Location location = lm.getLastKnownLocation(PROVIDER);

		saveLoc(location);

		return START_STICKY;
	}

	public void saveLoc(Location l) {
		Log.v(TAG, "Method running");

		lm.requestLocationUpdates(PROVIDER, // provider
				0, // minTime in ms
				0, // minDistance in m
				ll); // LocationListener
		latstring = "Latitude: " + l.getLatitude();
		longstring = " Longitude: " + l.getLongitude();
		String txt = latstring + longstring;
		Log.v(TAG, latstring + longstring);
		try {
			

	        FileOutputStream fos ;

	        try {
	            fos = new FileOutputStream("/storage/sdcard0/coord.txt", true);

	            FileWriter fWriter;

	            try {
	                fWriter = new FileWriter(fos.getFD());
	                fWriter.write(txt);
	                fWriter.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                fos.getFD().sync();
	                fos.close();
	            }
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	        }

	} finally {
		
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

						// Hier komt de code voor het reageren op een sms
						Log.i(TAG,
								"Message recieved: "
										+ messages[0].getMessageBody());
						CharSequence text = "SMS Received";
						Toast.makeText(context, text, Toast.LENGTH_SHORT)
								.show();

						SmsManager sms = SmsManager.getDefault();
						sms.sendTextMessage(phonenr, null, messageText, null,
								null);

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