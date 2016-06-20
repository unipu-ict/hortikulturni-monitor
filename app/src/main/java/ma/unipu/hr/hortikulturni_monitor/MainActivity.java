package ma.unipu.hr.hortikulturni_monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ma.unipu.hr.hortikulturni_monitor.model.FlowerPower;
import ma.unipu.hr.hortikulturni_monitor.service.BluetoothDeviceModel;
import ma.unipu.hr.hortikulturni_monitor.service.FlowerPowerServiceManager;
import ma.unipu.hr.hortikulturni_monitor.service.IFlowerPowerDevice;
import ma.unipu.hr.hortikulturni_monitor.service.IFlowerPowerServiceListener;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerPowerConstants;
import io.sule.gaugelibrary.GaugeView;

public class MainActivity extends Activity {

    private FlowerPowerServiceManager serviceManager;
    private GaugeView mTempGauge, mSoilGauge, mSunGauge, mBatteryGauge;
    private int temp, soil, sun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTempGauge = (GaugeView) findViewById(R.id.temperature);
        mSoilGauge = (GaugeView) findViewById(R.id.soil);
        mSunGauge = (GaugeView) findViewById(R.id.sun);
        mBatteryGauge = (GaugeView) findViewById(R.id.battery);
        mTempGauge.setTargetValue(0);
        mSoilGauge.setTargetValue(0);
        mSunGauge.setTargetValue(0);
        mBatteryGauge.setTargetValue(0);

        String deviceAddress = getIntent().getStringExtra(FlowerPowerConstants.EXTRAS_DEVICE_ADDRESS);

        serviceManager = FlowerPowerServiceManager.getInstance(deviceAddress, getApplicationContext());

        IFlowerPowerServiceListener serviceListener = new IFlowerPowerServiceListener() {

            @Override
            public void serviceConnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service connected");
            }

            @Override
            public void serviceDisconnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service disconnected");
            }

            @Override
            public void serviceFailed(RuntimeException extra) {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: service failed");
                finish();
            }

            @Override
            public void deviceDiscovered(BluetoothDeviceModel extra) {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device discovered " + extra.getName());
            }

            @Override
            public void deviceConnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device connected");
            }

            @Override
            public void deviceDisconnected() {
                Log.i(FlowerPowerConstants.TAG, "serviceListener: device disconnected");
            }

            @Override
            public void deviceReady(IFlowerPowerDevice device) {

                // lovi temp, soil, ali ne sun
                device.notifyTemperature(true, 1000);   //2000
                device.notifySunlight(true, 2000);      //1000
                device.notifySoilMoisture(true, 1000);  //1000
                //device.notifyBatteryLevel(true, 1000);

                device.readTemperature();
                device.readSunlight();
                device.readSoilMoisture();
                device.readBatteryLevel();
            }

            @Override
            public void dataAvailable(FlowerPower fp) {
                temp = (int) fp.getTemperature();
                soil = (int) fp.getSoilMoisture();
                sun = (int) fp.getSunlight();
                mTempGauge.setTargetValue(temp);
                mSoilGauge.setTargetValue(soil);
                mSunGauge.setTargetValue(sun);
                mBatteryGauge.setTargetValue(fp.getBatteryLevel());
            }
        };

        serviceManager.addServiceListener(serviceListener);
        serviceManager.bind();
    }

    @Override
    protected void onStart() {
        super.onStart();
        serviceManager.connect();

        Button spremi = (Button) findViewById(R.id.btn_spremi);
        spremi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra(FlowerPowerConstants.EXTRAS_TEMPERATURE, temp);
                if (soil == -1) soil = 0;
                intent.putExtra(FlowerPowerConstants.EXTRAS_SOIL_MOISTURE, soil);
                intent.putExtra(FlowerPowerConstants.EXTRAS_SUNLIGHT, sun);
                finish();
                startActivity(intent);
            }
        });

        /*
        Button prati = (Button) findViewById(R.id.btn_prati);
        prati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        serviceManager.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceManager.disconnect();
        serviceManager.unbind();
    }
}
