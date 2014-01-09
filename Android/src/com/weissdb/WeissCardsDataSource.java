package com.weissdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WeissCardsDataSource
{
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	public String[] allColumns =
	{ MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_CARDNO, MySQLiteHelper.COLUMN_RARITY, MySQLiteHelper.COLUMN_COLOR,
			MySQLiteHelper.COLUMN_SIDE, MySQLiteHelper.COLUMN_LEVEL, MySQLiteHelper.COLUMN_COST,
			MySQLiteHelper.COLUMN_POWER, MySQLiteHelper.COLUMN_SOUL, MySQLiteHelper.COLUMN_TRAIT1,
			MySQLiteHelper.COLUMN_TRAIT2, MySQLiteHelper.COLUMN_TRIGGERS, MySQLiteHelper.COLUMN_FLAVOR,
			MySQLiteHelper.COLUMN_TEXT };

	// Constructor
	public WeissCardsDataSource(Context context)
	{
		dbHelper = new MySQLiteHelper(context);
	}

	// Open connection to database
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	// Terminate connection to database
	public void close()
	{
		dbHelper.close();
	}
	
	public void beginTransaction()
	{
		database.beginTransaction();
	}
	
	public void performTransaction()
	{
		database.setTransactionSuccessful();
		database.endTransaction();
	}

	// This does an insert
	public void insertCard(WeissCard card)
	{
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_NAME, card.getName());
		values.put(MySQLiteHelper.COLUMN_CARDNO, card.getCardNo());
		values.put(MySQLiteHelper.COLUMN_RARITY, card.getRarity());
		values.put(MySQLiteHelper.COLUMN_COLOR, card.getColor());
		values.put(MySQLiteHelper.COLUMN_SIDE, card.getSide());
		values.put(MySQLiteHelper.COLUMN_LEVEL, String.valueOf(card.getLevel()));
		values.put(MySQLiteHelper.COLUMN_COST, String.valueOf(card.getCost()));
		values.put(MySQLiteHelper.COLUMN_POWER, String.valueOf(card.getPower()));
		values.put(MySQLiteHelper.COLUMN_SOUL, String.valueOf(card.getSoul()));
		values.put(MySQLiteHelper.COLUMN_TRAIT1, card.getTrait1());
		values.put(MySQLiteHelper.COLUMN_TRAIT2, card.getTrait2());
		values.put(MySQLiteHelper.COLUMN_TRIGGERS, card.getTriggers());
		values.put(MySQLiteHelper.COLUMN_FLAVOR, card.getFlavor());
		values.put(MySQLiteHelper.COLUMN_TEXT, card.getText());

		database.insert(MySQLiteHelper.TABLE_NAME, null, values);
	}

	public void deleteAll()
	{
		open();
		database.delete(MySQLiteHelper.TABLE_NAME, null, null);
		close();
	}
	
	public Cursor execQuery(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
	{
		open();
		return database.query(MySQLiteHelper.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
	}
}
