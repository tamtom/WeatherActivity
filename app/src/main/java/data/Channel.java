package data;

import org.json.JSONObject;

/**
 * Created by omar on 9/17/2015.
 */
public class Channel implements JSONPopulator {
    Item item;
    Unit unit;

    public Item getItem() {
        return item;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public void populate(JSONObject data) {
        unit = new Unit();
        unit.populate(data.optJSONObject("units"));
        item = new Item();
        item.populate(data.optJSONObject("item"));

    }
}
