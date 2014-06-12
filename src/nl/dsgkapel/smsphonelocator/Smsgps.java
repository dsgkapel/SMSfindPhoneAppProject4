package nl.dsgkapel.smsphonelocator;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Smsgps extends Service {
	public Smsgps() {

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Context context = getApplicationContext();
		CharSequence text = "Service started";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		
		registerReceiver(receiver, filter);

		return START_STICKY;
	}

	private final BroadcastReceiver receiver = new BroadcastReceiver() {
		
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
	                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
	                }
	                if (messages.length > -1) {
	                    Log.i(TAG,
	                            "Message recieved: " + messages[0].getMessageBody());
	                    CharSequence text = "SMS Received";
	                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	                }
	            }
	        }
		}
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
