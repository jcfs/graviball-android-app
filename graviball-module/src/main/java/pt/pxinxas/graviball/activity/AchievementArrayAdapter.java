package pt.pxinxas.graviball.activity;

import java.util.List;

import pt.pxinxas.graviball.R;
import pt.pxinxas.graviball.game.achievements.AchievementRecord;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AchievementArrayAdapter extends ArrayAdapter<AchievementRecord> {

	private ImageView medalImage;
	private TextView achievementName;
	private TextView achievementDate;
	private TextView achievementDesc;
	private final List<AchievementRecord> recordList;

	public AchievementArrayAdapter(Context context, int textViewResourceId, List<AchievementRecord> recordList) {
		super(context, textViewResourceId);
		this.recordList = recordList;
	}
	
	@Override
	public int getCount() {
		return recordList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.list_achiev, parent, false);

		medalImage = (ImageView) convertView.findViewById(R.id.imageId);
		achievementName = (TextView) convertView.findViewById(R.id.nameId);
		achievementDate = (TextView) convertView.findViewById(R.id.dateId);
		achievementDesc = (TextView) convertView.findViewById(R.id.descriptionId);

		AchievementRecord achievementRecord = recordList.get(position);
		
		achievementName.setText(achievementRecord.getName());
		achievementDate.setText(achievementRecord.getDate());
		achievementDesc.setText(achievementRecord.getDescription());
		
		if (achievementRecord.getDate() == null) {
			medalImage.setVisibility(View.INVISIBLE);
		}
		
		return convertView;

	}

}
