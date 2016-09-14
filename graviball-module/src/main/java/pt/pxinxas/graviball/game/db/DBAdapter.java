package pt.pxinxas.graviball.game.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	private Context context;
	private SQLiteHelper liteHelper;

	public DBAdapter(Context context) {
		liteHelper = new SQLiteHelper(context);
	}

	public void insertHighScore(ContentValues values) {
		liteHelper.getWritableDatabase().insert(DBTables.TABLE_HIGHSCORES, null, values);
	}

	public void insertAchiev(ContentValues values) {
		liteHelper.getWritableDatabase().insert(DBTables.TABLE_ACHIEVS, null, values);
	}

	public void updateIfBetter(ContentValues values) {
		Cursor result = liteHelper.getReadableDatabase().rawQuery("select * from achievs where id=" + values.getAsInteger(DBTables.TABLE_ACHIEVS_ID),
				null);
		if (!result.moveToFirst()) {
			insertAchiev(values);
		} else {
			if (values.getAsInteger(DBTables.TABLE_ACHIEVS_COUNTER) > result.getInt(4)) {
				liteHelper.getWritableDatabase().update(DBTables.TABLE_ACHIEVS, values, "where id=" + values.getAsInteger(DBTables.TABLE_ACHIEVS_ID),
						null);
			}
		}
	}

	public Cursor executeQuery(String sql) {
		Cursor result = liteHelper.getReadableDatabase().rawQuery(sql, null);
		return result;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	private static class SQLiteHelper extends SQLiteOpenHelper {

		private SQLiteHelper(Context context) {
			super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DBQueries.CREATE_TB_HIGHSCORES);
			db.execSQL(DBQueries.CREATE_TB_ACHIEVS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}
	}

}