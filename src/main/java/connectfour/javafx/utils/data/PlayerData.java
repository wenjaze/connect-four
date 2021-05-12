package connectfour.javafx.utils.data;


import lombok.Data;

@Data
public class PlayerData {

    static PlayerData instance;
    private static String playerName1;
    private static String playerName2;
    private static String winner;

    private PlayerData() {
    }

    public static String getPlayerName1() {
        return playerName1;
    }

    public static void setPlayerName1(String playerName1) {
        PlayerData.playerName1 = playerName1;
    }

    public static String getPlayerName2() {
        return playerName2;
    }

    public static void setPlayerName2(String playerName2) {
        PlayerData.playerName2 = playerName2;
    }

    public static PlayerData getInstance() {
        if (instance != null) {
            return instance;
        }
        return new PlayerData();
    }
}
