package databasetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

public class DBManager extends Service
{
	private static SQLiteDatabase	db		= null;
	private static Context			context	= null;

	public static void setContext(Context c)
	{
		context = c;
	}

	public SQLiteDatabase getDB()
	{
		if (db == null)
			db = new DatabaseHandler(context).getWritableDatabase();
		return db;
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

}
