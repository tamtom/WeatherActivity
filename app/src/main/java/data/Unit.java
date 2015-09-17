package data;

import org.json.JSONObject;

/**
 * Created by omar on 9/17/2015.
 */
public class Unit implements JSONPopulator {
    private  String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject data) {
temperature = data.optString("temperature");
    }
}
