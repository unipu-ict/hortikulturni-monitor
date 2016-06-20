package ma.unipu.hr.hortikulturni_monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ma.unipu.hr.hortikulturni_monitor.util.Flower;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerDataSource;
import ma.unipu.hr.hortikulturni_monitor.util.FlowerPowerConstants;

public class HistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView historyLv = (ListView) findViewById(R.id.lv_flowers);
        FlowerDataSource source = new FlowerDataSource(getApplicationContext());
        source.open();
        final Flower[] flowers = source.getAllFlowers();
        source.close();
        List<String> flowerList = new ArrayList<>();
        for (Flower f : flowers) {
            flowerList.add(f.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, flowerList);
        historyLv.setAdapter(adapter);

        historyLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, DetailsActivity.class);
                intent.putExtra(FlowerPowerConstants.EXTRAS_FLOWER, flowers[position]);
                startActivity(intent);
            }
        });
    }
}
