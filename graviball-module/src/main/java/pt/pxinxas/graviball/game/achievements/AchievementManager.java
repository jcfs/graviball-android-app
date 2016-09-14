package pt.pxinxas.graviball.game.achievements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pt.pxinxas.graviball.game.achievements.campaign.BeatTheKingAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.EasterRoundAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.EasyLeagueAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.FirstForeverAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.FirstFourAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.FirstOneAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.FirstThreeAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.FirstTwoAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.HardLeagueAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.LotsBloodAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.NeverDropAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.NoBloodAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.NotHardLeagueAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.OwnTheKingAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReactingFiveAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReactingFourAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReactingOneAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReactingThreeAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReactingTwoAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.ReflexMasterAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.RightTimingAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.StillEasyLeagueAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.WinWithBloodAchievement;
import pt.pxinxas.graviball.game.achievements.campaign.WrongTimingAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalFiftyAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalFiveAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalOneHundredAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalSeventyFiveAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalTenAchievement;
import pt.pxinxas.graviball.game.achievements.survival.SurvivalTwentyFiveAchievement;
import pt.pxinxas.graviball.game.db.DBQueries;
import pt.pxinxas.graviball.game.db.DBTables;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.scene.CampaignKingLeagueScene;
import pt.pxinxas.graviball.game.scene.CampaignLeagueScene;
import pt.pxinxas.graviball.game.scene.EasterEggRoundScene;
import pt.pxinxas.graviball.game.scene.SurvivalLeagueScene;
import android.content.ContentValues;
import android.database.Cursor;

public class AchievementManager {

	private static final AchievementManager instance = new AchievementManager();
	// Campaign Achievements
	private final List<Achievement> campaignAchievements = new ArrayList<Achievement>();
	private final List<Achievement> survivalAchievements = new ArrayList<Achievement>();

	private AchievementManager() {
		// campaign
		campaignAchievements.add(new EasterRoundAchievement());
		campaignAchievements.add(new BeatTheKingAchievement());
		campaignAchievements.add(new NoBloodAchievement());
		campaignAchievements.add(new EasyLeagueAchievement());
		campaignAchievements.add(new StillEasyLeagueAchievement());
		campaignAchievements.add(new NotHardLeagueAchievement());
		campaignAchievements.add(new HardLeagueAchievement());
		campaignAchievements.add(new LotsBloodAchievement());
		campaignAchievements.add(new OwnTheKingAchievement());
		campaignAchievements.add(new ReactingOneAchievement());
		campaignAchievements.add(new ReactingTwoAchievement());
		campaignAchievements.add(new ReactingThreeAchievement());
		campaignAchievements.add(new ReactingFourAchievement());
		campaignAchievements.add(new ReactingFiveAchievement());
		campaignAchievements.add(new ReflexMasterAchievement());
		campaignAchievements.add(new NeverDropAchievement());
		campaignAchievements.add(new RightTimingAchievement());
		campaignAchievements.add(new WrongTimingAchievement());
		campaignAchievements.add(new FirstOneAchievement());
		campaignAchievements.add(new FirstTwoAchievement());
		campaignAchievements.add(new FirstThreeAchievement());
		campaignAchievements.add(new FirstFourAchievement());
		campaignAchievements.add(new FirstForeverAchievement());
		campaignAchievements.add(new WinWithBloodAchievement());
		// survival
		survivalAchievements.add(new SurvivalFiveAchievement());
		survivalAchievements.add(new SurvivalTenAchievement());
		survivalAchievements.add(new SurvivalTwentyFiveAchievement());
		survivalAchievements.add(new SurvivalFiftyAchievement());
		survivalAchievements.add(new SurvivalSeventyFiveAchievement());
		survivalAchievements.add(new SurvivalOneHundredAchievement());
	}

	public List<Achievement> checkCampaignAchievements(GameState state) {
		List<Achievement> result = new ArrayList<Achievement>();
		for (Achievement achievement : campaignAchievements) {
			if (achievement.getStatus() == AchievementStatus.RUNNING) {
				achievement.validateAchievement(state);
				if (achievement.getStatus() == AchievementStatus.ACHIEVED) {
					insertAchievement(achievement);
					result.add(achievement);
				}
			}
		}
		return result;
	}

	public List<Achievement> checkSurvivalAchievements(GameState state) {
		List<Achievement> result = new ArrayList<Achievement>();
		for (Achievement achievement : survivalAchievements) {
			if (achievement.getStatus() == AchievementStatus.RUNNING) {
				achievement.validateAchievement(state);
				if (achievement.getStatus() == AchievementStatus.ACHIEVED) {
					insertAchievement(achievement);
					result.add(achievement);
				}
			}
		}
		return result;
	}

	private List<Achievement> checkEasterEggAchievement(GameState state) {
		List<Achievement> result = new ArrayList<Achievement>();
		Achievement achievement = campaignAchievements.get(0);
		if (achievement.getStatus() == AchievementStatus.RUNNING) {
			achievement.validateAchievement(state);
			if (achievement.getStatus() == AchievementStatus.ACHIEVED) {
				insertAchievement(achievement);
				result.add(achievement);
			}
		}
		return result;
	}

	public List<Achievement> checkAchievements(GameState state) {
		if (state.getCurrentLeague() == null && state.getCurrentRound() instanceof EasterEggRoundScene) {
			return checkEasterEggAchievement(state);
		} else if (state.getCurrentLeague() instanceof SurvivalLeagueScene) {
			return checkSurvivalAchievements(state);
		} else if (state.getCurrentLeague() instanceof CampaignLeagueScene || state.getCurrentLeague() instanceof CampaignKingLeagueScene) {
			return checkCampaignAchievements(state);
		}
		return null;
	}

	private void insertAchievement(Achievement achievement) {
		ContentValues values = new ContentValues();
		values.put(DBTables.TABLE_ACHIEVS_ID, achievement.getAchievementId());
		values.put(DBTables.TABLE_ACHIEVS_NAME, achievement.getAchievementName());
		values.put(DBTables.TABLE_ACHIEVS_DESC, achievement.getAchievementDesc());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		values.put(DBTables.TABLE_ACHIEVS_DATE, formatter.format(Calendar.getInstance().getTime()));
		values.put(DBTables.TABLE_ACHIEVS_COUNTER, 0);
		EntityHolder.getDbAdapter().insertAchiev(values);
	}

	public void init() {
		Cursor results = EntityHolder.getDbAdapter().executeQuery(DBQueries.SELECT_FROM_ACHIEVS);
		results.moveToFirst();
		while (!results.isAfterLast()) {
			for (Achievement achievement : campaignAchievements) {
				if (achievement.getAchievementId() == results.getInt(0)) {
					achievement.setStatus(AchievementStatus.ACHIEVED);
				}
			}
			for (Achievement achievement : survivalAchievements) {
				if (achievement.getAchievementId() == results.getInt(0)) {
					achievement.setStatus(AchievementStatus.ACHIEVED);
				}
			}
			results.moveToNext();
		}
		results.close();
		for (Achievement achievement : campaignAchievements) {
			if (achievement.getStatus() != AchievementStatus.ACHIEVED) {
				achievement.initAchievement();
			}
		}
		for (Achievement achievement : survivalAchievements) {
			if (achievement.getStatus() != AchievementStatus.ACHIEVED) {
				achievement.initAchievement();
			}
		}
	}

	public boolean isGameBeaten() {
		Cursor results = EntityHolder.getDbAdapter().executeQuery(DBQueries.SELECT_FROM_ACHIEVS);
		results.moveToFirst();

		while (!results.isAfterLast()) {
			if (results.getInt(0) == AchievementConstants.ACHIEVE_BEAT_THE_KING_ID) {
				results.close();
				return true;
			}
			results.moveToNext();
		}
		results.close();

		return false;
	}

	public static AchievementManager getInstance() {
		return instance;
	}

	public List<Achievement> getCampaignAchievements() {
		return campaignAchievements;
	}

	public List<Achievement> getSurvivalAchievements() {
		return survivalAchievements;
	}

	public List<Achievement> getAllAchievements() {
		List<Achievement> result = new ArrayList<Achievement>();
		result.addAll(campaignAchievements);
		result.addAll(survivalAchievements);
		return result;
	}

}
