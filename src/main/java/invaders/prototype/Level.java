package invaders.prototype;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Level implements Cloneable{
    private final JSONObject gameInfo;
    private final JSONObject playerInfo;
    private final JSONArray bunkersInfo;
    private final JSONArray enemiesInfo;

    /**
     * Level constructor.
     * @param gameInfo: gameInfo JSONObject.
     * @param playerInfo: playerInfo JSONObject.
     * @param bunkersInfo: bunkersInfo JSONObject.
     * @param enemiesInfo: enemiesInfo JSONObject.
     */
    public Level(JSONObject gameInfo, JSONObject playerInfo,
                 JSONArray bunkersInfo, JSONArray enemiesInfo) {
        this.gameInfo = gameInfo;
        this.playerInfo = playerInfo;
        this.bunkersInfo = bunkersInfo;
        this.enemiesInfo = enemiesInfo;
    }

    /**
     * Returns the gameInfo object.
     * @return the gameInfo object.
     */
    public JSONObject getGameInfo() {
        return gameInfo;
    }

    /**
     * Returns the playerInfo object.
     * @return the playerInfo object.
     */
    public JSONObject getPlayerInfo() {
        return playerInfo;
    }

    /**
     * Returns the bunkersInfo object.
     * @return the bunkersInfo object.
     */
    public JSONArray getBunkersInfo() {
        return bunkersInfo;
    }

    /**
     * Returns the enemiesInfo object.
     * @return the enemiesInfo object.
     */
    public JSONArray getEnemiesInfo() {
        return enemiesInfo;
    }

    /**
     * The method returns a deep copy of the object.
     * @return a deep copy of the object.
     */
    @Override
    public Level clone() {
        return new Level(copyGameInfo(), copyPlayerInfo(),
                copyBunkersInfo(), copyEnemiesInfo());
    }

    /**
     * The method returns a deep copy of gameInfo object.
     * @return a deep copy of gameInfo.
     */
    @SuppressWarnings("unchecked")
    private JSONObject copyGameInfo() {
        JSONObject gameInfoClone = new JSONObject();
        JSONObject sizeJSONObject = new JSONObject();

        Long x = (Long) ((JSONObject) gameInfo.get("size")).get("x");
        Long y = (Long) ((JSONObject) gameInfo.get("size")).get("y");

        sizeJSONObject.put("x", x);
        sizeJSONObject.put("y", y);
        gameInfoClone.put("size", sizeJSONObject);

        return gameInfoClone;
    }

    /**
     * The method returns a deep copy of playerInfo object.
     * @return a deep copy of playerInfo.
     */
    @SuppressWarnings("unchecked")
    private JSONObject copyPlayerInfo() {
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

    /**
     * The method returns a deep copy of bunkersInfo array.
     * @return a deep copy of bunkersInfo.
     */
    @SuppressWarnings("unchecked")
    private JSONArray copyBunkersInfo() {
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

    /**
     * The method returns a deep copy of enemiesInfo array.
     * @return a deep copy of enemiesInfo.
     */
    @SuppressWarnings("unchecked")
    private JSONArray copyEnemiesInfo() {
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
