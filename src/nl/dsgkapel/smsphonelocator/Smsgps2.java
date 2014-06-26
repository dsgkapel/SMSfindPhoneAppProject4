package nl.dsgkapel.smsphonelocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import android.os.Environment;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Smsgps2 extends Service {

	public static boolean running = false;
	LocationManager lm;
	FileWriter coords;
	public String latstring;
	public String longstring;
	private static final String TAG = "coordinates";
	private static final String TAG2 = "codestring";
	private Timer timer;
	String PROVIDER = LocationManager.GPS_PROVIDER;
	public String code;
	public ArrayList<String> blockednumbers = new ArrayList<String>();
	public String newline = System.getProperty("line.separator");
	


	
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
		registerReceiver(receiver2, filter);
		running = true;
		Location location = lm.getLastKnownLocation(PROVIDER);

		getCode();
		saveLoc(location);

		return START_STICKY;
	}
	
	
	public void getCode(){
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "code.txt");

		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuffer fileContent = new StringBuffer("");

			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader buffreader = new BufferedReader(isr);

			String readString = "";
			while (readString != null) {
				fileContent.append(readString);
				readString = buffreader.readLine();
				if(readString != null){
				code = readString;
				}
			}
			fis.close();
			Log.v(TAG2, "Code = "+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void getBlocked(){
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "blocked.txt");

		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuffer fileContent = new StringBuffer("");

			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader buffreader = new BufferedReader(isr);
			
			String readString = "";
			while (readString != null) {
				fileContent.append(readString);
				readString = "null"+ buffreader.readLine();
				if(readString != null){
				blockednumbers.add(readString);
				}
			}
			fis.close();
			Log.v(TAG2, "Code = "+ code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

		} catch (RuntimeException e) {
			e.printStackTrace();
			Toast.makeText(context, "No Signal detected, stopping service",
					Toast.LENGTH_SHORT).show();
			stopService(new Intent(this, Smsgps2.class));
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
		unregisterReceiver(receiver2);
		context.startService(new Intent(context, Smsgps.class));

	}


	
	private final BroadcastReceiver receiver2 = new BroadcastReceiver() {

		String phonenr;
		
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "log.txt");
		
		private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
		private static final String TAG = "SMSBroadcastReceiver";
		IntentFilter filter = new IntentFilter();
		SmsManager sms = SmsManager.getDefault();

		
		@Override
		 
		public void onReceive(Context context, Intent intent) {
			boolean blocked = false;
			Log.i(TAG, "Intent recieved: " + intent.getAction());
			String[] blockarray = blockednumbers.toArray(new String[blockednumbers.size()]);
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

						for(int i = 0; i < blockarray.length; i++){
							if(blockarray[i] == phonenr){
								blocked = true;
							}
							else{
								blocked = false;
							}
						}
						
						if (messagebody.equals(code) && blocked == false) {

							
							sms.sendTextMessage(phonenr, null, latstring + longstring,
									null, null);
							SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							String format = s.format(new Date());
							String ts = "SMS sent to " + phonenr + " at " + format + newline;
							try {

								FileOutputStream fos;

								try {
									fos = new FileOutputStream(file,
											true);

									FileWriter fWriter;

									try {
										fWriter = new FileWriter(fos.getFD());
										fWriter.write(ts);
										fWriter.close();
										Log.v(TAG, ts);
									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										fos.getFD().sync();
										fos.close();
										Log.v(TAG, ts);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							} finally {

							}
							
							context.stopService(new Intent(context, Smsgps2.class));
							}
							

						} else {
							clearAbortBroadcast();
							context.stopService(new Intent(context, Smsgps2.class));
							
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