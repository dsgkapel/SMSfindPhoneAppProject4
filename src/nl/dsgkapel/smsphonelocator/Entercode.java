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

public class Entercode extends ActionBarActivity {

	private static final String TAG = "tag";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entercode);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entercode, menu);
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
	
	public void save(View view){
		Context context = getApplicationContext();
		EditText codetext = (EditText) findViewById(R.id.savecode);
		String codestring = codetext.getText().toString();
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "code.txt");
		try {

			FileOutputStream fos;

			try {
				fos = new FileOutputStream(file,
						false);

				FileWriter fWriter;

				try {
					fWriter = new FileWriter(fos.getFD());
					fWriter.write(codestring);
					fWriter.close();
					Log.v(TAG, codestring);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.getFD().sync();
					fos.close();
					Log.v(TAG, codestring);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {

		}
		Toast.makeText(context, "code saved",
				Toast.LENGTH_SHORT).show();
	}
	


}