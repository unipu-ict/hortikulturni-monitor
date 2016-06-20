package ma.unipu.hr.hortikulturni_monitor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ma.unipu.hr.hortikulturni_monitor.util.Flower;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerDataSource;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerPowerConstants;

public class DataActivity extends Activity {

    private int temperature, soilMoisture,sunlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        temperature = getIntent().getIntExtra(FlowerPowerConstants.EXTRAS_TEMPERATURE, 0);
        soilMoisture = getIntent().getIntExtra(FlowerPowerConstants.EXTRAS_SOIL_MOISTURE, 0);
        sunlight = getIntent().getIntExtra(FlowerPowerConstants.EXTRAS_SUNLIGHT, 0);

        TextView tempTextView = (TextView) findViewById(R.id.ttx_temp);
        TextView soilTextView = (TextView) findViewById(R.id.ttx_soil);
        TextView sunTextView = (TextView) findViewById(R.id.ttx_sun);

        tempTextView.setText("- temperatura: " + temperature + "°C");
        soilTextView.setText("- vlažnost tla: " + soilMoisture + "%");
        sunTextView.setText("- sunčeva svjetlost: " + sunlight + "%");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button btnSpremi = (Button) findViewById(R.id.btn_spremi);
        btnSpremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText naziv = (EditText) findViewById(R.id.etx_naziv);
                EditText opis = (EditText) findViewById(R.id.etx_opis);
                if (!naziv.getText().toString().equals("")) {
                    FlowerDataSource source = new FlowerDataSource(getApplicationContext());
                    source.open();
                    source.create(new Flower(naziv.getText().toString(), opis.getText().toString(), getTimeStamp(),
                            temperature, soilMoisture, sunlight));
                    source.close();
                    Toast.makeText(DataActivity.this, "Podatci su uspješno spremljeni.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DataActivity.this, "Molim unesite naziv biljke.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTimeStamp() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hours = c.get(Calendar.HOUR);
        int mins = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        return "" + day + "." + month + "." + year + " " + hours + ":" + mins + ":" + seconds;
    }
}


