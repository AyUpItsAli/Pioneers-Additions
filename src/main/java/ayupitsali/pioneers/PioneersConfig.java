package ayupitsali.pioneers;

import eu.midnightdust.lib.config.MidnightConfig;

public class PioneersConfig extends MidnightConfig {
    public enum DefaultColour { GREEN, YELLOW, RED }
    @Comment(category = "lives", centered = true) public static Comment functionality;
    @Entry(category = "lives") public static DefaultColour DEFAULT_COLOUR = DefaultColour.GREEN;
    @Entry(category = "lives", min = 1) public static int GREEN_LIVES = 3;
    @Entry(category = "lives", min = 1) public static int YELLOW_LIVES = 3;
    @Entry(category = "lives", min = 1) public static int RED_LIVES = 3;
    @Entry(category = "lives", min = 1) public static int LIVES_LOST_ON_DEATH = 1;
    @Entry(category = "lives", min = 1) public static int LIVES_GAINED_ON_KILL = 1;
    @Entry(category = "lives") public static boolean ENABLE_GIVE_COMMAND = true;
    @Entry(category = "lives") public static boolean ENABLE_LIFE_TOKEN = true;
    @Comment(category = "lives", centered = true) public static Comment display;
    @Entry(category = "lives") public static String TERM_FOR_LIVES_SINGULAR = "life";
    @Entry(category = "lives") public static String TERM_FOR_LIVES_PLURAL = "lives";
    @Entry(category = "lives") public static String TERM_FOR_PLAYERS_SINGULAR = "pioneer";
    @Entry(category = "lives") public static String TERM_FOR_PLAYERS_PLURAL = "pioneers";
    @Entry(category = "lives") public static String GREEN_GROUP_NAME = "Green Pioneers";
    @Entry(category = "lives") public static String YELLOW_GROUP_NAME = "Yellow Pioneers";
    @Entry(category = "lives") public static String RED_GROUP_NAME = "Red Pioneers";
    @Entry(category = "lives") public static String GHOST_GROUP_NAME = "Ghosts";

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
    public static String getGhostGroupName() {
        return GHOST_GROUP_NAME.strip();
    }
}
