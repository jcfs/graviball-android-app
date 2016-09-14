package pt.pxinxas.graviball.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.pxinxas.graviball.R;
import pt.pxinxas.graviball.game.db.DBQueries;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HighScoresActivity extends ListActivity {
	private static final String NAME_ID = "NAME_ID";
	private static final String DATE_ID = "DATE_ID";
	String[] from = new String[] { NAME_ID, DATE_ID };
	int[] to = new int[] { R.id.nameId, R.id.dateId };
	private List<HighScoreRecord> recordList = new ArrayList<HighScoreRecord>();
	private List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.highscores);
		
		
		TextView title = (TextView) findViewById(R.id.text);
		title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/novafont.ttf"));
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		recordList = new ArrayList<HighScoreRecord>();
		Cursor results = EntityHolder.getDbAdapter().executeQuery(DBQueries.SELECT_FROM_HIGHSCORE);

		results.moveToFirst();
		while (!results.isAfterLast()) {
			HighScoreRecord record = new HighScoreRecord();
			record.setLeague(results.getInt(0));
			record.setDate(results.getString(1));
			recordList.add(record);
			results.moveToNext();
		}
		results.close();

		for (HighScoreRecord record : recordList) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(NAME_ID, String.valueOf("League #" + record.getLeague()));
			map.put(DATE_ID, record.getDate());
			fillMaps.add(map);
		}


		
		
		setListAdapter(new SimpleAdapter(this, fillMaps, R.layout.list_hs, from, to));

		super.onCreate(savedInstanceState);
	}
	
	
	
	private static final class HighScoreRecord {
		private int league;
		private String date;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getLeague() {
			return league;
		}

		public void setLeague(int league) {
			this.league = league;
		}

	}
}
