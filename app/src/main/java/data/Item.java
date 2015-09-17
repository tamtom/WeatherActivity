package data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by omar on 9/17/2015.
 */
public class Item implements JSONPopulator {
    private Condition condition;
    private Forcast forcast;

    public Forcast getForcast() {
        return forcast;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
        condition.populate(data.optJSONObject("condition"));
        forcast = new Forcast();
        forcast.populatear(data.optJSONArray("forecast"));

    }

    @Override
    public void populatear(JSONArray data) {

    }
}
