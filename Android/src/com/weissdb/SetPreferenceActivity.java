package com.weissdb;

import com.weissdb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

public class SetPreferenceActivity extends Activity
{
	// confirm app close on back button press
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event)
		{
			if (keyCode == KeyEvent.KEYCODE_BACK)
			{
				Intent intent = new Intent(this, MainActivity.class);
				// Stop the settings activity, boot up main activity again
				SetPreferenceActivity.this.finish();
				startActivity(intent);
				return true;
			}
			else
				return super.onKeyDown(keyCode, event);
		}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
	}
	
	// ACTION BAR FUNCTIONS
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			default:
				Intent intent = new Intent(this, MainActivity.class);
				// Stop the settings activity
				SetPreferenceActivity.this.finish();
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}