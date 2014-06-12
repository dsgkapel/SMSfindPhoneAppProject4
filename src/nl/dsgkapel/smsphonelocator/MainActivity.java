package nl.dsgkapel.smsphonelocator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	public static boolean on;
	public static String a = "Turn app on";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button onoff = (Button) findViewById(R.id.turnon);

		if (Smsgps.running == false || a == "turn app on") {
			a = "Turn app on";
			onoff.setText(a);
			
		} else {
			a = "Turn app on";
			onoff.setText(a);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void turnon(View view) {
		Button onoff = (Button) findViewById(R.id.turnon);
		if (Smsgps.running == false || a == "turn app on") {
			startService(new Intent(this, Smsgps.class));
			a = "Turn app off";
			onoff.setText(a);
		}
		else{
			
			stopService(new Intent(this, Smsgps.class));
			a = "Turn app on";
			onoff.setText(a);
		}
	}

	public void sponsorus(View view) {

		Intent intent = new Intent(this, Sponsorus.class);
		startActivity(intent);
	}

	public void settings(View view) {

		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
	}

	public void helpinfo(View view) {

		Intent intent = new Intent(this, Helpinfo.class);
		startActivity(intent);
	}

}
