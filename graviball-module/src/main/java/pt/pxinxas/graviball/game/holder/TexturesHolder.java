package pt.pxinxas.graviball.game.holder;

import java.util.List;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

public class TexturesHolder {
	/* Background Textures */
	private static ITextureRegion roundBackgroundBack;
	private static ITextureRegion roundBackgroundBackKing;
	private static ITextureRegion roundBackgroundFront;
	private static ITextureRegion leagueBackground;
	private static ITextureRegion scoreBoardBackground;
	private static ITextureRegion grassBackground;
	private static ITextureRegion treeBackground;
	private static ITextureRegion achievesBackground;
	private static TiledTextureRegion menuItemBack;
	/* Font */
	private static Font font;
	private static Font fontMini;
	private static Font fontMicro;

	/* Animated entities Textures (sprites) */
	private static ITextureRegion playerTexture;
	private static List<ITextureRegion> opponentTextures;
	private static ITextureRegion kingTexture;

	/* Icons */
	private static ITextureRegion medKit;
	private static ITextureRegion nextButton;
	private static ITextureRegion chute;
	private static ITextureRegion medal;
	private static ITextureRegion heart;
	private static ITextureRegion diamond;
	private static ITextureRegion healthBack;
	private static ITextureRegion healthFront;
	private static ITextureRegion bubble;
	private static ITextureRegion logo;
	private static ITextureRegion help;



	public static Font getFont() {
		return font;
	}

	public static void setFont(Font font) {
		TexturesHolder.font = font;
	}

	public static ITextureRegion getLeagueBackgroundBack() {
		return roundBackgroundBack;
	}

	public static void setLeagueBackgroundBack(ITextureRegion leagueBackgroundBack) {
		TexturesHolder.roundBackgroundBack = leagueBackgroundBack;
	}

	public static ITextureRegion getLeagueBackgroundFront() {
		return roundBackgroundFront;
	}

	public static void setLeagueBackgroundFront(ITextureRegion leagueBackgroundFront) {
		TexturesHolder.roundBackgroundFront = leagueBackgroundFront;
	}

	public static Font getFontMini() {
		return fontMini;
	}

	public static void setFontMini(Font fontMini) {
		TexturesHolder.fontMini = fontMini;
	}

	public static ITextureRegion getLeagueBackground() {
		return leagueBackground;
	}

	public static void setLeagueBackground(ITextureRegion leagueBackground) {
		TexturesHolder.leagueBackground = leagueBackground;
	}

	public static ITextureRegion getScoreBoardBackground() {
		return scoreBoardBackground;
	}

	public static void setScoreBoardBackground(ITextureRegion scoreBoardBackground) {
		TexturesHolder.scoreBoardBackground = scoreBoardBackground;
	}

	public static ITextureRegion getMedKit() {
		return medKit;
	}

	public static void setMedKit(ITextureRegion medKit) {
		TexturesHolder.medKit = medKit;
	}

	public static ITextureRegion getNextButton() {
		return nextButton;
	}

	public static void setNextButton(ITextureRegion nextButton) {
		TexturesHolder.nextButton = nextButton;
	}


	public static ITextureRegion getRoundBackgroundBackKing() {
		return roundBackgroundBackKing;
	}

	public static void setRoundBackgroundBackKing(ITextureRegion roundBackgroundBackKing) {
		TexturesHolder.roundBackgroundBackKing = roundBackgroundBackKing;
	}

	public static ITextureRegion getChute() {
		return chute;
	}

	public static void setChute(ITextureRegion chute) {
		TexturesHolder.chute = chute;
	}

	public static TiledTextureRegion getMenuItemBack() {
		return menuItemBack;
	}

	public static void setMenuItemBack(TiledTextureRegion menuItemBack) {
		TexturesHolder.menuItemBack = menuItemBack;
	}

	public static ITextureRegion getGrassBackground() {
		return grassBackground;
	}

	public static void setGrassBackground(ITextureRegion grassBackground) {
		TexturesHolder.grassBackground = grassBackground;
	}

	public static ITextureRegion getMedal() {
		return medal;
	}

	public static void setMedal(ITextureRegion medal) {
		TexturesHolder.medal = medal;
	}

	public static Font getFontMicro() {
		return fontMicro;
	}

	public static void setFontMicro(Font fontMicro) {
		TexturesHolder.fontMicro = fontMicro;
	}

	public static ITextureRegion getTreeBackground() {
		return treeBackground;
	}

	public static void setTreeBackground(ITextureRegion treeBackground) {
		TexturesHolder.treeBackground = treeBackground;
	}

	public static ITextureRegion getAchievesBackground() {
		return achievesBackground;
	}

	public static void setAchievesBackground(ITextureRegion achievesBackground) {
		TexturesHolder.achievesBackground = achievesBackground;
	}

	public static ITextureRegion getHeart() {
		return heart;
	}

	public static void setHeart(ITextureRegion heart) {
		TexturesHolder.heart = heart;
	}

	public static ITextureRegion getDiamond() {
		return diamond;
	}

	public static void setDiamond(ITextureRegion diamond) {
		TexturesHolder.diamond = diamond;
	}

	public static ITextureRegion getHealthBack() {
		return healthBack;
	}

	public static void setHealthBack(ITextureRegion healthBack) {
		TexturesHolder.healthBack = healthBack;
	}

	public static ITextureRegion getHealthFront() {
		return healthFront;
	}

	public static void setHealthFront(ITextureRegion healthFront) {
		TexturesHolder.healthFront = healthFront;
	}

	public static ITextureRegion getPlayerTexture() {
		return playerTexture;
	}

	public static void setPlayerTexture(ITextureRegion playerTexture) {
		TexturesHolder.playerTexture = playerTexture;
	}

	public static List<ITextureRegion> getOpponentTextures() {
		return opponentTextures;
	}

	public static void setOpponentTextures(List<ITextureRegion> opponentTextures) {
		TexturesHolder.opponentTextures = opponentTextures;
	}

	public static ITextureRegion getKingTexture() {
		return kingTexture;
	}

	public static void setKingTexture(ITextureRegion kingTexture) {
		TexturesHolder.kingTexture = kingTexture;
	}

	public static ITextureRegion getBubble() {
		return bubble;
	}

	public static void setBubble(ITextureRegion bubble) {
		TexturesHolder.bubble = bubble;
	}

	public static ITextureRegion getLogo() {
		return logo;
	}

	public static void setLogo(ITextureRegion logo) {
		TexturesHolder.logo = logo;
	}

	public static ITextureRegion getHelp() {
		return help;
	}

	public static void setHelp(ITextureRegion help) {
		TexturesHolder.help = help;
	}

}
