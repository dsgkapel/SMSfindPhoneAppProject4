package nl.dsgkapel.smsphonelocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Log extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log);

		TextView text;
		int i = 0;
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.loglayout);
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "log.txt");

		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuffer fileContent = new StringBuffer("");

			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader buffreader = new BufferedReader(isr);

			String readString = "";
			while (readString != null) {
				fileContent.append(readString);
				readString = buffreader.readLine();

				text = new TextView(this);
				text.setText(readString);
				text.setGravity(Gravity.CENTER);
				text.setTextColor(Color.parseColor("#FFFFFF"));
				text.setId(i);
				layout.addView(text);
				i++;
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_log, container,
					false);
			return rootView;
		}
	}

	public void exit(View view) {

		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);

	}
}
