package invaders;

import invaders.levels.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {
    private static JSONObject gameInfo;
    private static JSONObject playerInfo;
    private static JSONArray bunkersInfo;
    private static JSONArray enemiesInfo;
    private static Map<String, Level> levelRegistry = new HashMap<>();

    public static void parse(String configPath){
        Level level = levelRegistry.get(configPath);
        if (level == null) {
            JSONParser parser = new JSONParser();
            try {
                JSONObject configObject = (JSONObject) parser.parse(new FileReader(configPath));

                // Reading game section
                gameInfo = (JSONObject) configObject.get("Game");

                // Reading player section
                playerInfo = (JSONObject) configObject.get("Player");

                // Reading bunker section
                bunkersInfo = (JSONArray) configObject.get("Bunkers");

                // Reading enemies section
                enemiesInfo = (JSONArray) configObject.get("Enemies");

                // Save the configuration
                levelRegistry.put(configPath,
                        new Level(gameInfo, playerInfo, bunkersInfo, enemiesInfo));

                System.out.println("Load: " + configPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // Retrieve info from Level object, each info is a clone
            gameInfo = level.getGameInfo();
            playerInfo = level.getPlayerInfo();
            bunkersInfo = level.getBunkersInfo();
            enemiesInfo = level.getEnemiesInfo();
        }
    }

    public static JSONObject getGameInfo() {
        return gameInfo;
    }

    public static JSONObject getPlayerInfo() {
        return playerInfo;
    }

    public static JSONArray getBunkersInfo() {
        return bunkersInfo;
    }

    public static JSONArray getEnemiesInfo() {
        return enemiesInfo;
    }
}