package invaders.levels;

import invaders.ConfigReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Level {
    private final JSONObject gameInfo;
    private final JSONObject playerInfo;
    private final JSONArray bunkersInfo;
    private final JSONArray enemiesInfo;

    public Level(JSONObject gameInfo, JSONObject playerInfo,
                 JSONArray bunkersInfo, JSONArray enemiesInfo) {
        this.gameInfo = gameInfo;
        this.playerInfo = playerInfo;
        this.bunkersInfo = bunkersInfo;
        this.enemiesInfo = enemiesInfo;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getGameInfo() {
        JSONObject gameInfoClone = new JSONObject();
        JSONObject sizeJSONObject = new JSONObject();

        Long x = (Long) ((JSONObject) gameInfo.get("size")).get("x");
        Long y = (Long) ((JSONObject) gameInfo.get("size")).get("y");
        sizeJSONObject.put("x", x);
        sizeJSONObject.put("y", y);
        gameInfoClone.put("size", sizeJSONObject);

        return gameInfoClone;
    }

    @SuppressWarnings("unchecked")
    public JSONObject getPlayerInfo() {
        JSONObject playerInfoClone = new JSONObject();
        JSONObject positionJSONObject = new JSONObject();

        Long speed = (Long) playerInfo.get("speed");
        Long lives = (Long) playerInfo.get("lives");
        Long x = (Long) ((JSONObject) playerInfo.get("position")).get("x");
        Long y = (Long) ((JSONObject) playerInfo.get("position")).get("y");
        positionJSONObject.put("x", x);
        positionJSONObject.put("y", y);
        playerInfoClone.put("speed", speed);
        playerInfoClone.put("lives", lives);
        playerInfoClone.put("position", positionJSONObject);

        return playerInfoClone;
    }

    @SuppressWarnings("unchecked")
    public JSONArray getBunkersInfo() {
        JSONArray bunkersInfoClone = new JSONArray();

        // Loop through the original bunkers array
        for (Object bunkerObject: this.bunkersInfo) {
            JSONObject bunkerOriginal = (JSONObject) bunkerObject;
            JSONObject bunkerClone = new JSONObject();
            JSONObject bunkerPosition = new JSONObject();
            JSONObject bunkerSize = new JSONObject();

            bunkerPosition.put("x",
                    ((JSONObject)
                            bunkerOriginal.get("position")).get("x"));
            bunkerPosition.put("y",
                    ((JSONObject)
                            bunkerOriginal.get("position")).get("y"));
            bunkerSize.put("x",
                    ((JSONObject)
                            bunkerOriginal.get("size")).get("x"));
            bunkerSize.put("y",
                    ((JSONObject)
                            bunkerOriginal.get("size")).get("y"));
            bunkerClone.put("position", bunkerPosition);
            bunkerClone.put("size", bunkerSize);

            bunkersInfoClone.add(bunkerClone);
        }
        return bunkersInfoClone;
    }

    @SuppressWarnings("unchecked")
    public JSONArray getEnemiesInfo() {
        JSONArray enemiesInfoClone = new JSONArray();

        // Loop through the original enemies array
        for (Object enemyObject: this.enemiesInfo) {
            JSONObject enemyOriginal = (JSONObject) enemyObject;
            JSONObject enemyClone = new JSONObject();
            JSONObject enemyPosition = new JSONObject();

            enemyPosition.put("x",
                    ((JSONObject)
                            enemyOriginal.get("position")).get("x"));
            enemyPosition.put("y",
                    ((JSONObject)
                            enemyOriginal.get("position")).get("y"));
            enemyClone.put("position", enemyPosition);
            enemyClone.put("projectile", enemyOriginal.get("projectile"));

            enemiesInfoClone.add(enemyClone);
        }
        return enemiesInfoClone;
    }
}
