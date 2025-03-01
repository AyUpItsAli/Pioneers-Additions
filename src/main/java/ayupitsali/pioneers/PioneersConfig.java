package ayupitsali.pioneers;

import eu.midnightdust.lib.config.MidnightConfig;

public class PioneersConfig extends MidnightConfig {
    private static final String FUNCTIONALITY = "functionality";
    public enum DefaultColor { GREEN, YELLOW, RED }
    @Entry(category = FUNCTIONALITY) public static DefaultColor DEFAULT_COLOR = DefaultColor.GREEN;
    @Entry(category = FUNCTIONALITY, min = 1) public static int GREEN_LIVES = 3;
    @Entry(category = FUNCTIONALITY, min = 1) public static int YELLOW_LIVES = 3;
    @Entry(category = FUNCTIONALITY, min = 1) public static int RED_LIVES = 3;
    @Entry(category = FUNCTIONALITY, min = 1) public static int LIVES_LOST_ON_DEATH = 1;
    @Entry(category = FUNCTIONALITY, min = 1) public static int LIVES_GAINED_ON_KILL = 1;
    @Entry(category = FUNCTIONALITY) public static boolean ENABLE_GIVE_COMMAND = true;
    @Entry(category = FUNCTIONALITY) public static boolean ENABLE_LIFE_TOKEN = true;

    private static final String DISPLAY = "display";
    @Entry(category = DISPLAY) public static String TERM_FOR_LIVES_SINGULAR = "life";
    @Entry(category = DISPLAY) public static String TERM_FOR_LIVES_PLURAL = "lives";
    @Entry(category = DISPLAY) public static String TERM_FOR_PLAYERS_SINGULAR = "pioneer";
    @Entry(category = DISPLAY) public static String TERM_FOR_PLAYERS_PLURAL = "pioneers";
    @Entry(category = DISPLAY) public static String GREEN_GROUP_NAME = "Green Pioneers";
    @Entry(category = DISPLAY) public static String YELLOW_GROUP_NAME = "Yellow Pioneers";
    @Entry(category = DISPLAY) public static String RED_GROUP_NAME = "Red Pioneers";
    @Entry(category = DISPLAY) public static String GRAY_GROUP_NAME = "The Fallen";

    public static String getTermForLivesSingular() {
        return TERM_FOR_LIVES_SINGULAR.strip().toLowerCase();
    }
    public static String getTermForLivesPlural() {
        return TERM_FOR_LIVES_PLURAL.strip().toLowerCase();
    }
    public static String getTermForPlayersSingular() {
        return TERM_FOR_PLAYERS_SINGULAR.strip().toLowerCase();
    }
    public static String getTermForPlayersPlural() {
        return TERM_FOR_PLAYERS_PLURAL.strip().toLowerCase();
    }
    public static String getGreenGroupName() {
        return GREEN_GROUP_NAME.strip();
    }
    public static String getYellowGroupName() {
        return YELLOW_GROUP_NAME.strip();
    }
    public static String getRedGroupName() {
        return RED_GROUP_NAME.strip();
    }
    public static String getGrayGroupName() {
        return GRAY_GROUP_NAME.strip();
    }
}
