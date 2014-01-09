package com.weissdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper
{
	// Table name
	public static final String TABLE_NAME = "WeissCards";

	// COLUMNS
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_CARDNO = "cardNo";
	public static final String COLUMN_RARITY = "rarity";
	public static final String COLUMN_COLOR = "color";
	public static final String COLUMN_SIDE = "side";
	public static final String COLUMN_LEVEL = "m_level";
	public static final String COLUMN_COST = "cost";
	public static final String COLUMN_POWER = "m_power";
	public static final String COLUMN_SOUL = "soul";
	public static final String COLUMN_TRAIT1 = "trait1";
	public static final String COLUMN_TRAIT2 = "trait2";
	public static final String COLUMN_TRIGGERS = "triggers";
	public static final String COLUMN_FLAVOR = "flavor";
	public static final String COLUMN_TEXT = "text";

	// Some random things fed to a super's method
	private static final String DATABASE_NAME = "weiss.db";
	private static final int DATABASE_VERSION = 1;

	// Database creation sql statement
	static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_NAME
			+ " TEXT, " + COLUMN_CARDNO + " TEXT, " 
			+ COLUMN_RARITY + " TEXT, " + COLUMN_COLOR  + " TEXT, " + COLUMN_SIDE 
			+ " TEXT, " + COLUMN_LEVEL + " INT, " + COLUMN_COST
			+ " INT, " + COLUMN_POWER + " INT, " + COLUMN_SOUL
			+ " INT, " + COLUMN_TRAIT1 + " TEXT, " + COLUMN_TRAIT2
			+ " TEXT, " + COLUMN_TRIGGERS + " TEXT, " + COLUMN_FLAVOR
			+ " TEXT, " + COLUMN_TEXT + " TEXT);";
	
	static final String[] indexes = {"CREATE INDEX name_index ON " + TABLE_NAME + " (" + COLUMN_NAME + ");",
									 "CREATE INDEX number_index ON " + TABLE_NAME + " (" + COLUMN_CARDNO + ");",
									 "CREATE INDEX rarity_index ON " + TABLE_NAME + " (" + COLUMN_RARITY + ");",	
									 "CREATE INDEX color_index ON " + TABLE_NAME + " (" + COLUMN_COLOR + ");",
									 "CREATE INDEX side_index ON " + TABLE_NAME + " (" + COLUMN_SIDE + ");",
									 "CREATE INDEX level_index ON " + TABLE_NAME + " (" + COLUMN_LEVEL + ");",
									 "CREATE INDEX cost_index ON " + TABLE_NAME + " (" + COLUMN_COST + ");",
									 "CREATE INDEX power_index ON " + TABLE_NAME + " (" + COLUMN_POWER + ");",
									 "CREATE INDEX soul_index ON " + TABLE_NAME + " (" + COLUMN_SOUL + ");",
									 "CREATE INDEX trait1_index ON " + TABLE_NAME + " (" + COLUMN_TRAIT1 + ");",
									 "CREATE INDEX trait2_index ON " + TABLE_NAME + " (" + COLUMN_TRAIT2 + ");",
									 "CREATE INDEX triggers_index ON " + TABLE_NAME + " (" + COLUMN_TRIGGERS + ");",
									 "CREATE INDEX flavor_index ON " + TABLE_NAME + " (" + COLUMN_FLAVOR + ");",
									 "CREATE INDEX text_index ON " + TABLE_NAME + " (" + COLUMN_TEXT + ");"};
	
	static final String DATABASE_DROP = "DROP TABLE " + TABLE_NAME;
	
	public MySQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
		for (int i = 0; i < indexes.length; i++)
		{
			database.execSQL(indexes[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
