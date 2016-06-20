package ma.unipu.hr.hortikulturni_monitor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import ma.unipu.hr.hortikulturni_monitor.util.FlowerPowerConstants;

public class ConfigActivity extends Activity {

    private boolean mScanning;
    private ProgressBar progressBar;
    private BluetoothAdapter mBluetoothAdapter;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private static final String DEVICE_NAME = "flower power";
    private static final int REQUEST_ENABLE_BT_START = 1;
    private static final int REQUEST_ENABLE_BT_ABOUT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            Toast.makeText(getApplicationContext(), R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == mBluetoothAdapter) {
            Toast.makeText(getApplicationContext(), R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Button start = (Button) findViewById(R.id.btn_start);
        Button povijest = (Button) findViewById(R.id.btn_povijest);
        Button about = (Button) findViewById(R.id.btn_about);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT_START);
                } else {
                    scanLeDevice(true);
                }
            }
        });

        povijest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT_ABOUT);
                } else {
                    Intent intent = new Intent(ConfigActivity.this, AboutActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == REQUEST_ENABLE_BT_START) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                scanLeDevice(true);
            } else {
                finish();
            }
        }
        if (requestCode == REQUEST_ENABLE_BT_ABOUT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(ConfigActivity.this, AboutActivity.class);
                startActivity(intent);
            } else {
                finish();
            }
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            progressBar.setVisibility(View.VISIBLE);
            // Stops scanning after a pre-defined scan period.
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (device.getName().toLowerCase().contains(DEVICE_NAME)) {
                        Intent intent = new Intent(ConfigActivity.this, MainActivity.class);
                        FlowerPowerConstants.DEVICE_ADDRESS = device.getAddress();
                        intent.putExtra(FlowerPowerConstants.EXTRAS_DEVICE_ADDRESS, device.getAddress());
                        if (mScanning) {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                        }
                        startActivity(intent);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }
    };
}
