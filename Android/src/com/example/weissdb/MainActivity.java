package com.example.weissdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity
{
	final Context context = this;
	WeissCardsDataSource datasource = new WeissCardsDataSource(context);
	private int prg = 0;
	private TextView tv;
	private ProgressBar pb;
	private boolean killKeyboard = false;
	private String currentPage = "";
	private int fileSize = 0;
	CardAdapter adapter;
	AdvancedQuery LastQuery;
	
	public boolean retainNumSearch()
	{
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		return mySharedPreferences.getBoolean("number_search_retain", false);
	}
	public boolean retainAdvSearch()
	{
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		return mySharedPreferences.getBoolean("advanced_search_retain", false);
	}
	public boolean getAdvSearchMutable()
	{
		SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		return mySharedPreferences.getBoolean("advanced_search_mutable", false);
	}
	
	/* m_name, m_rarity, m_color, m_side, m_levelComparator, m_level, m_costComparator,
	   m_cost, m_powerComparator, m_power, m_soulComparator, m_soul, m_trait1, m_trait2, 
	   m_triggers, m_flavor, m_text */
	public void restoreAdvancedSearch()
	{
		if (LastQuery != null)
		{
			String[] queryParams = LastQuery.getParams();
			((EditText) findViewById(R.id.edit_name)).setText(queryParams[0]);
			((Spinner) findViewById(R.id.rarity_spinner)).setSelection(Util.rarityToIndex(queryParams[1]));
			((Spinner) findViewById(R.id.color_spinner)).setSelection(Util.colorToIndex(queryParams[2]));
			((Spinner) findViewById(R.id.side_spinner)).setSelection(Util.sideToIndex(queryParams[3]));
			((Spinner) findViewById(R.id.level_comparison)).setSelection(Util.comparatorToIndex(queryParams[4]));
			((EditText) findViewById(R.id.edit_level)).setText(queryParams[5]);
			((Spinner) findViewById(R.id.cost_comparison)).setSelection(Util.comparatorToIndex(queryParams[6]));
			((EditText) findViewById(R.id.edit_cost)).setText(queryParams[7]);
			((Spinner) findViewById(R.id.power_comparison)).setSelection(Util.comparatorToIndex(queryParams[8]));
			((EditText) findViewById(R.id.edit_power)).setText(queryParams[9]);
			((Spinner) findViewById(R.id.soul_comparison)).setSelection(Util.comparatorToIndex(queryParams[10]));
			((EditText) findViewById(R.id.edit_soul)).setText(queryParams[11]);
			((EditText) findViewById(R.id.edit_trait1)).setText(queryParams[12]);
			((EditText) findViewById(R.id.edit_trait2)).setText(queryParams[13]);
			((Spinner) findViewById(R.id.triggers_spinner)).setSelection(Util.triggerToIndex(queryParams[14]));
			((EditText) findViewById(R.id.edit_flavor)).setText(queryParams[15]);
			((EditText) findViewById(R.id.edit_text)).setText(queryParams[16]);
		}
	}
	
	public void setUpAdvancedSearch()
	{
		String cardName = ((EditText) findViewById(R.id.edit_name)).getText().toString();
		String rarity = ((Spinner) findViewById(R.id.rarity_spinner)).getSelectedItem().toString();
		String color = ((Spinner) findViewById(R.id.color_spinner)).getSelectedItem().toString();
		String side = ((Spinner) findViewById(R.id.side_spinner)).getSelectedItem().toString();
		
		String levelComparator = ((Spinner) findViewById(R.id.level_comparison)).getSelectedItem().toString();
		String level = ((EditText) findViewById(R.id.edit_level)).getText().toString();
		
		String costComparator = ((Spinner) findViewById(R.id.cost_comparison)).getSelectedItem().toString();
		String cost = ((EditText) findViewById(R.id.edit_cost)).getText().toString();
		
		String powerComparator = ((Spinner) findViewById(R.id.power_comparison)).getSelectedItem().toString();
		String power = ((EditText) findViewById(R.id.edit_power)).getText().toString();
		
		String soulComparator = ((Spinner) findViewById(R.id.soul_comparison)).getSelectedItem().toString();
		String soul = ((EditText) findViewById(R.id.edit_soul)).getText().toString();
		
		String trait1 = ((EditText) findViewById(R.id.edit_trait1)).getText().toString();
		String trait2 = ((EditText) findViewById(R.id.edit_trait2)).getText().toString();
		String triggers = ((Spinner) findViewById(R.id.triggers_spinner)).getSelectedItem().toString();
		String flavor = ((EditText) findViewById(R.id.edit_flavor)).getText().toString();
		String text = ((EditText) findViewById(R.id.edit_text)).getText().toString();
		
		LastQuery = new AdvancedQuery (cardName, rarity, color, side, levelComparator, level, costComparator, cost, powerComparator,
													power, soulComparator, soul, trait1, trait2, triggers, flavor, text);
	}
	
	public void setUpSearch (String type)
	{
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		killKeyboard = true;
		currentPage = type;
		invalidateOptionsMenu();
	}
	
	// A derpy getter to bypass things needing to be final
	final public int getFileSize()
	{
		return fileSize;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Use below line to force a database update. Comment out if it's unnecessary
		// datasource.deleteAll();
		
		// Try to open the cards.csv file which should come packaged with the app
		final AssetManager assetManager = getAssets();
		final InputStream cardFile;
		try
		{
			cardFile = assetManager.open("cards.csv");
		}
		catch (IOException e)
		{
			Util.showDialog("Card import failed.", context);
			return;
		}
		
		// Run a SQL query on the number of rows in the user's card database
		String[] columns = {"COUNT (*)"};
		Cursor cursor = datasource.execQuery(columns, null, null, null, null, null);
		cursor.moveToFirst();
		int dbSize = cursor.getInt(0);

		// Get number of cards represented in cards.csv file
		try
		{
			fileSize = Util.count(cardFile);
		}
		catch (IOException e1)
		{
			Util.showDialog("Card import failed.", context);
			return;
		}
		
		int layoutId=0;
		// Compare cards.csv's # of cards with cards stored in user's database
		// If they're different, switch to the initialization page and start importing cards
		if (getFileSize() != dbSize)
		{
			getActionBar().hide();
			layoutId = R.layout.progress;
		}
		else
		{
		    layoutId = R.layout.activity_main;
		}
		setContentView(layoutId);

		if (getFileSize() != dbSize)
		{
			datasource.deleteAll();

			pb = (ProgressBar) findViewById(R.id.pbId);
			pb.setMax(getFileSize());
			tv = (TextView) findViewById(R.id.tvId);
			
			Runnable myThread = new Runnable()
			{
				@SuppressLint("InlinedApi")
				@Override
				public void run()
				{
					// Reset progress from older runs
					if (prg != 0)
					{
						prg = 0;
					}
					
					InputStream cardFile;
					try
					{
						cardFile = assetManager.open("cards.csv");
					}
					catch (IOException e2)
					{
						Util.showDialog("Card import failed.", context);
						return;
					}
					
					BufferedReader in = null;
					try
					{
						in = new BufferedReader(new InputStreamReader(cardFile, "UTF-8"));
					}
					catch (UnsupportedEncodingException e1)
					{
						Util.showDialog("Card import failed.", context);
						return;
					}
					
			        String reader = "";
					try
					{
						datasource.open();
						datasource.beginTransaction();
						while ((reader = in.readLine()) != null)
						{
							WeissCard tempCard = new WeissCard();
							String[] RowData = reader.split("~~\\|\\}");
							
							if (RowData.length != 14)
							{
								// System.out.println(RowData[1]);
							}
							else
							{
								tempCard.setName(RowData[0]);
								tempCard.setCardNo(RowData[1]);
								tempCard.setRarity(RowData[2]);
								tempCard.setColor(RowData[3]);
								tempCard.setSide(RowData[4]);
								tempCard.setLevel(RowData[5]);
								tempCard.setCost(RowData[6]);
								tempCard.setPower(RowData[7]);
								tempCard.setSoul(RowData[8]);
								tempCard.setTrait1(RowData[9]);
								tempCard.setTrait2(RowData[10]);
								tempCard.setTriggers(RowData[11]);
								tempCard.setFlavor(RowData[12]);
								tempCard.setText(RowData[13]);
							}
							datasource.insertCard(tempCard);
							hnd.sendMessage(hnd.obtainMessage());
						}
						datasource.performTransaction();
						datasource.close();
					}
					catch (IOException e)
					{
						Util.showDialog("Card import failed.", context);
						return;
					}
					
					try
					{
						in.close();
					}
					catch (IOException e)
					{
						Util.showDialog("Card import failed.", context);
						return;
					}

					runOnUiThread(new Runnable()
					{
						public void run()
						{
							tv.setText("All cards successfully imported.");
							Util.showDialog("All cards successfully imported.", context);
							setContentView(R.layout.activity_main);
							getActionBar().show();
						};
					});
				}

				@SuppressLint("HandlerLeak")
				Handler hnd = new Handler()
				{
					@Override
					public void handleMessage(Message msg)
					{
						prg++;
						pb.setProgress(prg);

						String perc = String.valueOf(prg).toString();
						tv.setText(perc + "/" + String.valueOf(getFileSize()) + " cards imported.");
					}
				};
			};
			new Thread(myThread).start();
		}
	}
	
	// Card Number Search
	public void findCard(View view)
	{	
		// Hide keyboard after submitting sign in
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		killKeyboard = false;
		
		EditText editID = (EditText) findViewById(R.id.cardID);
		String cardID = editID.getText().toString();
		
		// If entered string is empty string or entirely white space, complain
		if (cardID.trim().length() == 0)
		{
			Util.showDialog("Please enter a card number.", context);
			return;
		}
		
		TextView cardInfo = (TextView) findViewById(R.id.cardSummary);
		
		String whereClause = "cardNo = ? COLLATE NOCASE";
		String[] whereArgs = new String[]
		{
		    cardID
		};
		Cursor cursor = datasource.execQuery(null, whereClause, whereArgs, null, null, null);
		if (cursor.moveToFirst())
		{
			String cardSummary = "Card successfully found! Here is its information:<br><br>";
			cardSummary += Util.createCardSummary(cursor);
			cardInfo.setText(Html.fromHtml(cardSummary));
		}
		else
		{
			cardInfo.setText("No card was found for the given card number.");
		}
		
		if (!retainNumSearch())
		{
			editID.setText("");
		}
	}
	
	// Advanced search (obv)
	/* (String name, String rarity, String color, String side, String levelComparator, String level,
		String costComparator, String cost, String powerComparator, String power, String soulComparator,
		String soul, String trait1, String trait2, String triggers, String flavor, String text) */
	public void advancedSearch(View view)
	{	
		// Hide keyboard
		InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		killKeyboard = false;
		
		// Fill in LastQuery AdvancedSearch object with submitted information
		setUpAdvancedSearch();
		WhereInfo statementInfo = LastQuery.createSelection();
		
		setContentView(R.layout.advanced_search_results);
		currentPage = "Advanced Search Results";
		invalidateOptionsMenu();
		
		// Dump out contents of struct for testing purposes
		/* System.out.println(statementInfo.getSelection());
		for (int i = 0; i < statementInfo.getSelectionArgs().length; i++)
		{
			System.out.println(statementInfo.getSelectionArgs()[i]);
		} */
		
		// Get all names and card numbers of cards matching criteria
		String tableColumns[] = new String[] {MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_CARDNO};
		String whereClause = statementInfo.getSelection();
		String[] whereArgs = statementInfo.getSelectionArgs();
		
		Cursor cursor = datasource.execQuery(tableColumns, whereClause, whereArgs, null, null, null);
		int numCards = 0;
		if (cursor.moveToFirst())
		{
			final ArrayList<Spanned> cardList = new ArrayList<Spanned>();
			final ListView listview = (ListView) findViewById(R.id.advancedSearchResults);
			
			// clear previous results in the LV
			listview.setAdapter(null);

			cardList.add(Html.fromHtml(""));
			while (!cursor.isAfterLast())
			{
				numCards++;
				
				String insert = "<br><b>Name: </b>" + cursor.getString(0) + "<br>";
				insert += "<b>Card No.: </b>" + cursor.getString(1) + "<br>";
				cardList.add(Html.fromHtml(insert));
				cursor.moveToNext();
			}
			
			CardAdapter lvAdapter = new CardAdapter(context, cardList);
			listview.setAdapter(lvAdapter);

			listview.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
				{
					final String item = ((Spanned) parent.getItemAtPosition(position)).toString();
					String whereClause = "cardNo = ? COLLATE NOCASE";
					String[] whereArgs = new String[]
					{
					    Util.parseCardNumber(item)
					};
					Cursor certainCard = datasource.execQuery(null, whereClause, whereArgs, null, null, null);
					if (certainCard.moveToFirst())
					{
						Util.showDialog(Util.createCardSummary(certainCard), context);
					}
					else
					{
						Util.showDialog("Whoops! We were unable to retrieve this card's information.", context);
					}
				}
			});
			TextView search_message = (TextView) findViewById(R.id.message);
			search_message.setText("Your search returned " + numCards + " card" + Util.addS(numCards) + ". Simply click on a card for its detailed card summary.");
		}
		else
		{
			TextView search_message = (TextView) findViewById(R.id.message);
			search_message.setText("No cards were found for the given parameters.");
		}
	}
	
	// Random card (obv)
	public void randomCard(View view)
	{
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.cardinfo);
		
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linlayout1);
		TextView cardInfo = new TextView(context);
		cardInfo.setId(9001);
		cardInfo.setLayoutParams(new LayoutParams
		(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
		));

		Cursor cursor = datasource.execQuery(null, null, null, null, null, "RANDOM() LIMIT 1");
		if (cursor.moveToFirst())
		{
			String cardSummary = Util.createCardSummary(cursor);
			cardInfo.setText(Html.fromHtml(cardSummary));
			
			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		    llp.setMargins(6, 0, 6, 0); // llp.setMargins(left, top, right, bottom);
		    cardInfo.setLayoutParams(llp);
			
		    Button btn = new Button(this);
		    btn.setText("Another Random Card");
		    
		    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		    btn.setLayoutParams(buttonParams);

		    btn.setOnClickListener(new OnClickListener()
		    {
		        public void onClick(View v)
		        {
		        	TextView cardInfo = (TextView) findViewById(9001);

		    		Cursor cursor = datasource.execQuery(null, null, null, null, null, "RANDOM() LIMIT 1");
		    		if (cursor.moveToFirst())
		    		{
		    			String cardSummary = Util.createCardSummary(cursor);
		    			cardInfo.setText(Html.fromHtml(cardSummary));
		    		}
		    		else
		    		{
		    			cardInfo.setText("Random card functionality failed.");
		    		}
		        } 
		    });
		    linearLayout.addView(btn);
		    linearLayout.addView(cardInfo);
		}
		else
		{
			cardInfo.setText("Random card functionality failed.");
		}
	}
	
	// SIMPLE MOVING AROUND FUNCTIONS
	public void goToNumSearch(View view)
	{
		setContentView(R.layout.numbersearch);
		setUpSearch("Number Search");
	}
	
	public void goToAdvancedSearch(View view)
	{
		setContentView(R.layout.advancedsearch);
		if (retainAdvSearch())
		{
			restoreAdvancedSearch();
		}
		setUpSearch("Advanced Search");
	}

	public void goToSettings(View view)
	{
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, SetPreferenceActivity.class);
		startActivityForResult(intent, 0);                                         
	}
	
	// ACTION BAR FUNCTIONS
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		MenuItem item = menu.findItem(R.id.erase);
		if (currentPage.equals("Advanced Search") || currentPage.equals("Number Search"))
		{
			System.out.println("FJNDSFKSFKS");                  
			item.setVisible(true);
		}
		else
		{
			item.setVisible(false);
		}
		super.onPrepareOptionsMenu(menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			// I use the home button as a derpy back button
			case android.R.id.home:
				// Hide keyboard
				if (killKeyboard)
				{
					InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					killKeyboard = false;
				}
				if (currentPage.equals("Advanced Search Results"))
				{
					setContentView(R.layout.advancedsearch);
					if (retainAdvSearch())
					{
						restoreAdvancedSearch();
					}
					currentPage = "Advanced Search";
				}
				else
				{
					setContentView(R.layout.activity_main);
					currentPage = "";
					getActionBar().setDisplayHomeAsUpEnabled(false);
				}
				break;

			// Clears search forms through the erase button in action bar
			case R.id.erase:
				if (currentPage.equals("Advanced Search"))
				{
					((EditText) findViewById(R.id.edit_name)).setText("");
					((Spinner) findViewById(R.id.rarity_spinner)).setSelection(0);
					((Spinner) findViewById(R.id.color_spinner)).setSelection(0);
					((Spinner) findViewById(R.id.side_spinner)).setSelection(0);
					((Spinner) findViewById(R.id.level_comparison)).setSelection(0);
					((EditText) findViewById(R.id.edit_level)).setText("");
					((Spinner) findViewById(R.id.cost_comparison)).setSelection(0);
					((EditText) findViewById(R.id.edit_cost)).setText("");
					((Spinner) findViewById(R.id.power_comparison)).setSelection(0);
					((EditText) findViewById(R.id.edit_power)).setText("");
					((Spinner) findViewById(R.id.soul_comparison)).setSelection(0);
					((EditText) findViewById(R.id.edit_soul)).setText("");
					((EditText) findViewById(R.id.edit_trait1)).setText("");
					((EditText) findViewById(R.id.edit_trait2)).setText("");
					((Spinner) findViewById(R.id.triggers_spinner)).setSelection(0);
					((EditText) findViewById(R.id.edit_flavor)).setText("");
					((EditText) findViewById(R.id.edit_text)).setText("");
					LastQuery = null;
				}
				if (currentPage.equals("Number Search"))
				{
					EditText editID = (EditText) findViewById(R.id.cardID);
					editID.setText("");
				}
				break;                                                                        
			case R.id.return_home:
				// Hide keyboard
				if (killKeyboard)
				{
					InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
					inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					killKeyboard = false;
				}
				setContentView(R.layout.activity_main);
				getActionBar().setDisplayHomeAsUpEnabled(false);
				currentPage = "";
				break;
			default:
				break;
		}
		invalidateOptionsMenu();
		return super.onOptionsItemSelected(item);
	}
}
