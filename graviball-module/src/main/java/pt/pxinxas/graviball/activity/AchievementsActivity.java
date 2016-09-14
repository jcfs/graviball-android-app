package pt.pxinxas.graviball.activity;

import java.util.ArrayList;
import java.util.List;

import pt.pxinxas.graviball.R;
import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementManager;
import pt.pxinxas.graviball.game.achievements.AchievementRecord;
import pt.pxinxas.graviball.game.db.DBQueries;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AchievementsActivity extends ListActivity implements AdapterView.OnItemClickListener {
	private List<AchievementRecord> recordList = new ArrayList<AchievementRecord>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.achievements);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		TextView title = (TextView) findViewById(R.id.text);
		title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/novafont.ttf"));

		for (Achievement achievement : AchievementManager.getInstance().getAllAchievements()) {
			AchievementRecord record = new AchievementRecord();
			record.setId(achievement.getAchievementId());
			record.setName(achievement.getAchievementName().replace("\n", " "));
			record.setDescription(achievement.getAchievementDesc());
			record.setDifficulty(achievement.getAchievementDifficulty().toString());
			recordList.add(record);
		}

		Cursor results = EntityHolder.getDbAdapter().executeQuery(DBQueries.SELECT_FROM_ACHIEVS);

		for (AchievementRecord records : recordList) {
			results.moveToFirst();
			while (!results.isAfterLast()) {
				if (results.getInt(0) == records.getId()) {
					records.setDate(results.getString(3));
				}
				results.moveToNext();
			}
		}
		results.close();

		ListView listView = getListView();
		listView.setAdapter(new AchievementArrayAdapter(this, R.layout.list_achiev, recordList));
		listView.setOnItemClickListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
		Toast.makeText(getApplicationContext(), ((TextView) ((RelativeLayout) view).getChildAt(3)).getText(), Toast.LENGTH_SHORT).show();
	}
	
}
