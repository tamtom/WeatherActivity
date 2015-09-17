package login.example.omar.weatheractivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import data.Channel;
import data.Item;
import services.YahooServiceCallBack;
import services.YahooWeatherService;

public class WeatherActivity extends Activity implements YahooServiceCallBack {
private ImageView img ;
    private TextView temp ;
    private TextView loctaion;
    private  TextView condition;
    private YahooWeatherService service;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        img = (ImageView) findViewById(R.id.img);
        temp = (TextView) findViewById(R.id.temp);
        loctaion = (TextView) findViewById(R.id.location);
        condition = (TextView) findViewById(R.id.condetion);
        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather("Austin, TX");

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
    }

    @Override
    public void servicefail(Exception ex) {
        Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();

    }
}
