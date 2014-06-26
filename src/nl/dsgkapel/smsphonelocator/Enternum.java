package nl.dsgkapel.smsphonelocator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Enternum extends ActionBarActivity {

	private static final String TAG = "tag";
	public String newline = System.getProperty("line.separator");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enternum);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enternum, menu);
		return true;
	}

	public void save(View view){
		Context context = getApplicationContext();
		EditText nametext = (EditText) findViewById(R.id.savename);
		EditText numtext = (EditText) findViewById(R.id.savenum);
		String numstring = nametext.getText().toString() + newline + numtext.getText().toString() + newline;
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "blocked.txt");
		try {

			FileOutputStream fos;

			try {
				fos = new FileOutputStream(file,
						true);

				FileWriter fWriter;

				try {
					fWriter = new FileWriter(fos.getFD());
					fWriter.write(numstring);
					fWriter.close();
					Log.v(TAG, numstring);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.getFD().sync();
					fos.close();
					Log.v(TAG, numstring);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {

		}
		Toast.makeText(context, "number saved",
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(this, Blockednumbers.class);
		startActivity(intent);
	}
	
	public void exit(View view){
		Intent intent = new Intent(this, Blockednumbers.class);
		startActivity(intent);
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
			View rootView = inflater.inflate(R.layout.fragment_enternum,
					container, false);
			return rootView;
		}
	}

}
