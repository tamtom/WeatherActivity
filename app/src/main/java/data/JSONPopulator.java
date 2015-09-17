package data;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by omar on 9/17/2015.
 */
public interface JSONPopulator {
    void populate(JSONObject data);

    void populatear(JSONArray data);

}
