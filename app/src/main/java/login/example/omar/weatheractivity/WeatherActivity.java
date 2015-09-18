package login.example.omar.weatheractivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import data.Channel;
import data.Forcast;
import data.Item;
import services.YahooServiceCallBack;
import services.YahooWeatherService;

public class WeatherActivity extends AppCompatActivity implements YahooServiceCallBack {
private ImageView img ;
    private TextView temp ;
    private TextView loctaion;
    private  TextView condition;
    private YahooWeatherService service;
    private ProgressDialog dialog;
    private Day[] days;
    private ImageView im;
    private TextView h;
    private TextView l;
    private TextView d;
    private Button change;
    private Spinner li;
    Dialog dialogs ;
    public void showDialog(){
        dialogs = new Dialog(this);

        dialogs.setContentView(R.layout.select);
        change = (Button) dialogs.findViewById(R.id.change);
        li = (Spinner) dialogs.findViewById(R.id.uni);
        dialogs.setCancelable(false);
        dialogs.show();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogs.hide();
                String c = li.getSelectedItem().toString();
                Forcast.index =0;
                service = new YahooWeatherService(WeatherActivity.this);
                dialog = new ProgressDialog(WeatherActivity.this);
                dialog.setMessage("Loading...");
                dialog.show();
                 if(c.contains("المركز"))
                service.refreshWeather("Al Balqa, Jordan");
                else  if (c.contains("العقبة"))
                     service.refreshWeather("Al Aqaba, Jordan");
                else if(c.contains("الهندسية"))
                     service.refreshWeather("Amman, Jordan");
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        img = (ImageView) findViewById(R.id.img);
        temp = (TextView) findViewById(R.id.temp);
        loctaion = (TextView) findViewById(R.id.location);
        condition = (TextView) findViewById(R.id.condetion);
        days = new Day[5];
        im = (ImageView) findViewById(R.id.img1);
        h = (TextView) findViewById(R.id.max1);
        l = (TextView) findViewById(R.id.low1);
        d = (TextView) findViewById(R.id.day1);
        days[0] = new Day(im, h, l, d);
        im = (ImageView) findViewById(R.id.img2);
        h = (TextView) findViewById(R.id.max2);
        l = (TextView) findViewById(R.id.low2);
        d = (TextView) findViewById(R.id.day2);
        days[1] = new Day(im, h, l, d);
        im = (ImageView) findViewById(R.id.img3);
        h = (TextView) findViewById(R.id.max3);
        l = (TextView) findViewById(R.id.low3);
        d = (TextView) findViewById(R.id.day3);
        days[2] = new Day(im, h, l, d);
        im = (ImageView) findViewById(R.id.img4);
        h = (TextView) findViewById(R.id.max4);
        l = (TextView) findViewById(R.id.low4);
        d = (TextView) findViewById(R.id.day4);
        days[3] = new Day(im, h, l, d);
        im = (ImageView) findViewById(R.id.img5);
        h = (TextView) findViewById(R.id.max5);
        l = (TextView) findViewById(R.id.low5);
        d = (TextView) findViewById(R.id.day5);
        days[4] = new Day(im, h, l, d);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Amman, Jordan");

    }

    @Override
    public void servicesucsess(Channel channel) {
dialog.hide();
        Item item = channel.getItem();
        int resources = getResources().getIdentifier("drawable/icon_" + item.getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation") Drawable imagdrawble = getResources().getDrawable(resources);
        this.img.setImageDrawable(imagdrawble);
        temp.setText(item.getCondition().getTemp() + " \u00b0 " + channel.getUnit().getTemperature());
        condition.setText(item.getCondition().getDescription());
        loctaion.setText(service.getLocation());
        int cc[] = item.getForcast().getCodes();
        int hh[] = item.getForcast().getHigh();
        int ll[] = item.getForcast().getLow();
        String da[] = item.getForcast().getDays();

        //noinspection deprecation

        //im.setImageDrawable(imagdrawble);

        //    System.out.println(hh[0]);

        //  h.setText(hh[0] + "");
        // l.setText(ll[0]+"");
        for (int i = 0; i < 5; i++) {
            days[i].getD().setText(da[i]);
            Log.d("loop", days[i].getD().getText().toString());
            days[i].h.setText(hh[i] + "");
            Log.d("loop", days[i].getH().getText().toString());
            days[i].getL().setText(ll[i] + "");
            Log.d("loop", days[i].getL().getText().toString());
            int res1 = getResources().getIdentifier("drawable/icon_" + cc[i], null, getPackageName());
            imagdrawble = getResources().getDrawable(res1);
            days[i].getImg().setImageDrawable(imagdrawble);

        }

    }

    @Override
    public void servicefail(Exception ex) {
        Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.change_city){

 showDialog();
        }
        return  super.onOptionsItemSelected(item);
    }
}
