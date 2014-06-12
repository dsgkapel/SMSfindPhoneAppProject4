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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		if (on == false) {
			onoff.setText("Turn on");
			on = true;
			// service aanzetten komt hier
		} else {
			onoff.setText("Turn off");
			on = false;
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
