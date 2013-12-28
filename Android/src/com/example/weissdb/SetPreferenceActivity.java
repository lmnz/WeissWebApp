package com.example.weissdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SetPreferenceActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
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
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * 	private void loadPref()
	{
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

		boolean my_checkbox_preference = mySharedPreferences.getBoolean("checkbox_preference", false);
		prefCheckBox.setChecked(my_checkbox_preference);

		String my_edittext_preference = mySharedPreferences.getString("edittext_preference", "");
		prefEditText.setText(my_edittext_preference);

	}
	 */
}