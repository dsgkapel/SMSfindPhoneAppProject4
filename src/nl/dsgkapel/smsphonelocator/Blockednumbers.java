package nl.dsgkapel.smsphonelocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class Blockednumbers extends ActionBarActivity {

	public String newline = System.getProperty("line.separator");
	private static final String TAG = "tag";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blockednumbers);

		shownum();

	}
	
	public void addnum(View view){
		Intent intent = new Intent(this, Enternum.class);
		startActivity(intent);
	}
	
	public void clearnum(View view){
		Intent intent = new Intent(this, Confirmblock.class);
		startActivity(intent);
	}
	
	public void editnum(View view){
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "blocked.txt");
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_EDIT);

		intent.setDataAndType(Uri.fromFile(file), "text/plain");
		startActivity(intent); 
	}
	
	public void shownum(){
	TextView text;
	int i = 0;
	
	LinearLayout layout = (LinearLayout) findViewById(R.id.scrollblock);
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
		getMenuInflater().inflate(R.menu.blockednumbers, menu);
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
	
	public void exit(View view) {
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_blockednumbers,
					container, false);
			return rootView;
		}
	}

}
