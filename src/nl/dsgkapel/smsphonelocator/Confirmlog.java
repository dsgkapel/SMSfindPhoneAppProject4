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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Confirmlog extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmlog);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmlog, menu);
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

	public void no(View view){
		Intent intent = new Intent(this, Log.class);
		startActivity(intent);
	}
	
	public void yes(View view){
		Context context = getApplicationContext();
		String numstring = "";
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(path, "log.txt");
		try {

			FileOutputStream fos;

			try {
				fos = new FileOutputStream(file,
						false);

				FileWriter fWriter;

				try {
					fWriter = new FileWriter(fos.getFD());
					fWriter.write(numstring);
					fWriter.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					fos.getFD().sync();
					fos.close();
					
					
					Toast.makeText(context, "Log cleared",
							Toast.LENGTH_SHORT).show();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} finally {

		}
		Intent intent = new Intent(this, Log.class);
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
			View rootView = inflater.inflate(R.layout.fragment_confirmlog,
					container, false);
			return rootView;
		}
	}

}
