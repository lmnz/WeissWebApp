package com.example.weissdb;

import java.io.IOException;
import java.io.InputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.Html;

public class Util
{
	// Given a cursor (the struct resultant of a SQL query), format the pointed at
	// card data into a string with the categories bolded
	public static String createCardSummary (Cursor cursor)
	{
		String cardSummary = "";
		cardSummary += "<b>Name: </b>" + cursor.getString(0) + "<br>";
		cardSummary += "<b>Card No.: </b>" + cursor.getString(1) + "<br>";
		cardSummary += "<b>Rarity: </b>" + cursor.getString(2) + "<br>";
		cardSummary += "<b>Color: </b>" + cursor.getString(3) + "<br>";
		cardSummary += "<b>Side: </b>" + cursor.getString(4) + "<br>";
		cardSummary += "<b>Level: </b>" + cursor.getInt(5) + "<br>";
		cardSummary += "<b>Cost: </b>" + cursor.getInt(6) + "<br>";
		cardSummary += "<b>Power: </b>" + cursor.getInt(7) + "<br>";
		cardSummary += "<b>Soul: </b>" + cursor.getInt(8) + "<br>";
		cardSummary += "<b>Trait 1: </b>" + cursor.getString(9) + "<br>";
		cardSummary += "<b>Trait 2: </b>" + cursor.getString(10) + "<br>";
		cardSummary += "<b>Triggers: </b>" + cursor.getString(11) + "<br>";
		cardSummary += "<b>Flavor: </b>" + cursor.getString(12) + "<br>";
		cardSummary += "<b>Text: </b>" + cursor.getString(13);
		return cardSummary;
	}
	
	// Shows a dialog with the passed in message that has a simple "Ok" button
	// to make it go away
	public static void showDialog(String message, Context context)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set dialog message
		alertDialogBuilder.setMessage(Html.fromHtml(message)).setCancelable(false)
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
	
	public static int comparatorToIndex(String comparator)
	{
		if (comparator.equals("<")){return 0;}
		if (comparator.equals(">")){return 1;}
		if (comparator.equals("=")){return 2;}
		if (comparator.equals("<=")){return 3;}
		if (comparator.equals(">=")){return 4;}
		return 0;
	}
	
	public static int rarityToIndex(String rarity)
	{
		if (rarity.equals("Any Rarity")){return 0;}
		if (rarity.equals("C")){return 1;}
		if (rarity.equals("CC")){return 2;}
		if (rarity.equals("CR")){return 3;}
		if (rarity.equals("PR")){return 4;}
		if (rarity.equals("R")){return 5;}
		if (rarity.equals("RE")){return 6;}
		if (rarity.equals("RR")){return 7;}
		if (rarity.equals("RRR")){return 8;}
		if (rarity.equals("SP")){return 9;}
		if (rarity.equals("SR")){return 10;}
		if (rarity.equals("SSP")){return 11;}
		if (rarity.equals("TD")){return 12;}
		if (rarity.equals("U")){return 13;}
		if (rarity.equals("XR")){return 14;}
		return 0;
	}
	
	public static int colorToIndex(String color)
	{
		if (color.equals("Any Color")){return 0;}
		if (color.equals("Blue")){return 1;}
		if (color.equals("Green")){return 2;}
		if (color.equals("Red")){return 3;}
		if (color.equals("Yellow")){return 4;}
		return 0;
	}
	
	public static int sideToIndex(String side)
	{
		if (side.equals("Any Side")){return 0;}
		if (side.equals("Schwarz Character")){return 1;}
		if (side.equals("Schwarz Climax")){return 2;}
		if (side.equals("Schwarz Event")){return 3;}
		if (side.equals("Weiss Character")){return 4;}
		if (side.equals("Weiss Climax")){return 5;}
		if (side.equals("Weiss Event")){return 6;}
		return 0;
	}
	
	public static int triggerToIndex(String trigger)
	{
		if (trigger.equals("Any Trigger")){return 0;}
		if (trigger.equals("2 Soul")){return 1;}
		if (trigger.equals("Draw")){return 2;}
		if (trigger.equals("None")){return 3;}
		if (trigger.equals("Salvage")){return 4;}
		if (trigger.equals("Soul")){return 5;}
		if (trigger.equals("Soul Bounce")){return 6;}
		if (trigger.equals("Soul Shot")){return 7;}
		if (trigger.equals("Stock")){return 8;}
		if (trigger.equals("Treasure")){return 9;}
		return 0;
	}
	
	public static String addS (int number)
	{
		if (number != 1)
		{
			return "s";
		}
		return "";
	}
	
	public static int count(InputStream filename) throws IOException
	{
		try
		{
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = filename.read(c)) != -1)
			{
				empty = false;
				for (int i = 0; i < readChars; ++i)
				{
					if (c[i] == '\n')
					{
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		}
		finally
		{
			filename.close();
		}
	}
	
	public static String parseCardNumber(String cardBlurb)
	{
		String[] tokens = cardBlurb.split("Card No.: ");
		String cardNo = (tokens[1].split("[\n]"))[0];
		return cardNo;
	}
}
