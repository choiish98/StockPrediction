package techtown.org;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import static android.graphics.Color.rgb;

public class Prediction extends AppCompatActivity {

    private LineChart mChart;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        // 곡선 그래프
        mChart = (LineChart) findViewById(R.id.prediction_lineChart);

        //mChart.setOnChartGestureListener(MainActivity.this);
        //mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.getXAxis().setDrawLabels(false);
        mChart.getAxisLeft().setDrawLabels(false);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 20f));
        yValues.add(new Entry(1, 35f));
        yValues.add(new Entry(2, 46f));
        yValues.add(new Entry(3, 50f));
        yValues.add(new Entry(4, 10f));
        yValues.add(new Entry(5, 60f));
        yValues.add(new Entry(6, 30f));

        LineDataSet set1 = new LineDataSet(yValues, "Data set 1");

        set1.setFillAlpha(110);
        set1.setColor(rgb(150, 31, 47));
        set1.setLineWidth(2f);
        set1.setCircleColor(rgb(150, 31, 47));
        set1.setFillColor(rgb(150, 31, 47));
        set1.setDrawHighlightIndicators(false);
        set1.setDrawValues(false);
        set1.setValueTextColor(Color.WHITE);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
        // 곡선 그래프

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SearchFragment fragment = new SearchFragment();
        fragmentTransaction.add(R.id.prediction_frame, fragment);
        fragmentTransaction.commit();

        // 클릭 리스너
        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_notification).setOnClickListener(onClickListener);
        findViewById(R.id.go_overview).setOnClickListener(onClickListener);
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
    }

    // onClickListener 정의
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.go_home:
                    gotoActivity(MainActivity.class);
                    break;
                case R.id.go_overview:
                    gotoActivity(Item_overview.class);
                    break;
                case R.id.go_notification:
                    gotoActivity(Notification.class);
                    break;
                case R.id.go_disclosure:
                    gotoActivity(Disclosure.class);
                    break;
            }
        }
    };

    // intent Acitivity 정의
    private void gotoActivity(Class c) {
        Intent intent = new Intent(Prediction.this, c);
        startActivity(intent);
    }
}
