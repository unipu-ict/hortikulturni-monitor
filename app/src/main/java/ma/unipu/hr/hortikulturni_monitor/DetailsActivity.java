package ma.unipu.hr.hortikulturni_monitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import ma.unipu.hr.hortikulturni_monitor.util.Flower;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerPowerConstants;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Flower flower = (Flower) getIntent().getSerializableExtra(FlowerPowerConstants.EXTRAS_FLOWER);

        TextView naziv = (TextView) findViewById(R.id.ttx_naziv);
        TextView timeStamp = (TextView) findViewById(R.id.ttx_timestamp);
        TextView opis = (TextView) findViewById(R.id.ttx_opis);
        TextView temp = (TextView) findViewById(R.id.ttx_temp);
        TextView soil = (TextView) findViewById(R.id.ttx_soil);
        TextView sun = (TextView) findViewById(R.id.ttx_sun);

        naziv.setText(flower.getName());
        timeStamp.setText("Vrijeme: " + flower.getTimeStamp());
        opis.setText(flower.getDesc());
        temp.setText("Temperatura: " + flower.getTemperature() + "°C");
        soil.setText("Vlažnost tla: " + flower.getSoil() + "%");
        sun.setText("Sunčeva vjetlost: " + flower.getSunlight() + "%");

    }
}
