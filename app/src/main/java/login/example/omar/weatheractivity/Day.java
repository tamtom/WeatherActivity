package login.example.omar.weatheractivity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by omar on 9/18/2015.
 */
public class Day {
    public ImageView img;
    public TextView h;
    public TextView l;
    public TextView d;

    public Day(ImageView day1, TextView h, TextView l, TextView d) {
        this.img = day1;
        this.h = h;
        this.l = l;
        this.d = d;
    }

    public ImageView getImg() {
        return img;
    }

    public TextView getH() {
        return h;
    }

    public TextView getL() {
        return l;
    }

    public TextView getD() {
        return d;
    }
}
