package pt.pxinxas.graviball.game.db;

public class DBQueries {

	// Highscores
	public static final String CREATE_TB_HIGHSCORES = "CREATE TABLE HIGHSCORE (league INTEGER,	date TEXT);";
	public static final String SELECT_FROM_HIGHSCORE = "SELECT distinct league, date FROM HIGHSCORE order by league desc limit 10";
	public static final String CREATE_TB_ACHIEVS = "CREATE TABLE ACHIEVS (ID INTEGER, NAME TEXT, DESC TEXT, date TEXT, counter INTEGER);";
	public static final String SELECT_FROM_ACHIEVS = "SELECT * FROM ACHIEVS order by ID";

}
