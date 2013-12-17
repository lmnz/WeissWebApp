package com.example.weissdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity
{
	final Context context = this;
	WeissCardsDataSource datasource = new WeissCardsDataSource(context);
	private int prg = 0;
	private TextView tv;
	private ProgressBar pb;
	
	private int fileSize = 0;
	
	public void showDialog(String message, Context context)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	
	final public int getFileSize()
	{
		return fileSize;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		datasource.deleteAll();

		final AssetManager assetManager = getAssets();
		final InputStream cardFile;
		try
		{
			cardFile = assetManager.open("cards.csv");
		}
		catch (IOException e)
		{
			showDialog("Card import failed.", context);
			e.printStackTrace();
			return;
		}
		
		String[] columns = {"COUNT (*)"};
		Cursor cursor = datasource.execQuery(columns, null, null, null, null, null);
		cursor.moveToFirst();
		int dbSize = cursor.getInt(0);

		try
		{
			fileSize = Util.count(cardFile);
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int layoutId=0;
		if(getFileSize() != dbSize)
		{
		    System.out.println("HELLO!");
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
					// This loop changes all of the contacts
					
					InputStream cardFile;
					try
					{
						cardFile = assetManager.open("cards.csv");
					}
					catch (IOException e2)
					{
						showDialog("Card import failed.", context);
						e2.printStackTrace();
						return;
					}
					
					BufferedReader in = null;
					try
					{
						in = new BufferedReader(new InputStreamReader(cardFile, "UTF-8"));
					}
					catch (UnsupportedEncodingException e1)
					{
						showDialog("Card import failed.", context);
						e1.printStackTrace();
					}
					
					boolean malformedCard = false;
			        String reader = "";
					try
					{
						while ((reader = in.readLine()) != null)
						{
							WeissCard tempCard = new WeissCard();
							String[] RowData = reader.split("~~\\|\\}");
							
							if (RowData.length != 14)
							{
								System.out.println(RowData[1]);
								malformedCard = true;
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
						if (malformedCard)
						{
							System.out.println("One of the cards is malformed.");
						}
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try
					{
						in.close();
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					runOnUiThread(new Runnable()
					{
						public void run()
						{
							tv.setText("All cards successfully imported.");
							showDialog("All cards successfully imported.", context);
							setContentView(R.layout.activity_main);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
