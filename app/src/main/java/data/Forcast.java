package data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by omar on 9/17/2015.
 */
public class Forcast implements JSONPopulator {
    public static int index = 0;
    private JSONObject day1;
    private JSONObject day2;
    private JSONObject day3;
    private JSONObject day4;
    private JSONObject day5;
    private int codes[] = new int[5];
    private String days[] = new String[5];
    private int high[] = new int[5];
    private int low[] = new int[5];
    private JSONObject[] ar = {day1, day2, day3, day4, day5};

    public int[] getCodes() {
        return codes;
    }

    public String[] getDays() {
        return days;
    }

    public int[] getHigh() {
        return high;
    }

    public int[] getLow() {
        return low;
    }

    public JSONObject[] getAr() {
        return ar;
    }

    @Override
    public void populate(JSONObject data) {
        if(index>=5)
            index=0;
        codes[index] = data.optInt("code");
        days[index] = data.optString("day");
        Log.d("Json days", days[index]);
        high[index] = data.optInt("high");
        low[index] = data.optInt("low");
        index++;

    }

    @Override
    public void populatear(JSONArray data) {
        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject d = (JSONObject) data.get(i);
                ar[i] = d;
                this.populate(ar[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
