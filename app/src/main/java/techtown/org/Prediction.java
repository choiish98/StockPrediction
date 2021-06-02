package techtown.org;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.rgb;

public class Prediction extends AppCompatActivity {

    private LineChart mChart;
    RecyclerView recyclerView;
    RecyclerView stock_recyclerView;
    EditText editText;
    PredictionAdapter adapter;
    StockAdapter stockAdapter;

    TextView total_text;
    Button order_btn;

    ArrayList<String> items = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        // 변수 초기화
        total_text = (TextView) findViewById(R.id.total_text);
        order_btn = (Button) findViewById(R.id.order_btn);


        // 곡선 그래프
        mChart = (LineChart) findViewById(R.id.prediction_lineChart);

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

        // 종목 검색
        recyclerView = (RecyclerView)findViewById(R.id.searchRecylcerview);
        editText = (EditText)findViewById(R.id.edittext);
        editText.setOnClickListener(onClickListener);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 아이템 추가
        try {
            String url = new APIURL().getApiURL();
            String stockList = new ItemOverview.RestAPITask(url.concat("/stocks/api/stocks/list")).execute().get();
            JSONArray jsonArray = new JSONArray(stockList);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String a = jsonObject.optString("company_name");
                items.add(a);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new PredictionAdapter(getApplicationContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String code) {
                // code에 종목 코드 존재
                // 그래프 구현 필요
            }
        });

        // 보유 주식 recycler
        stock_recyclerView = (RecyclerView)findViewById(R.id.stockRecylcerview);
        stock_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        // dumy
        Stock[] stock_items = new Stock[1];
        stock_items[0] = new Stock();
        stock_items[0].setStock_name("삼성전자");
        stock_items[0].setAmount("123");
        stock_items[0].setSize(1);

        stockAdapter = new StockAdapter(stock_items);
        stock_recyclerView.setAdapter(stockAdapter);

        // 클릭 리스너
        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_notification).setOnClickListener(onClickListener);
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
        findViewById(R.id.go_overview).setOnClickListener(onClickListener);
        findViewById(R.id.buy_btn).setOnClickListener(onClickListener);
        findViewById(R.id.sell_btn).setOnClickListener(onClickListener);
        findViewById(R.id.order_btn).setOnClickListener(onClickListener);
    }

    // 백그라운드 동작을 위한 asyncTask
    public static class RestAPITask extends AsyncTask<Integer, Void, String> {
        // Variable to store url
        protected String mURL;

        // Constructor
        public RestAPITask(String url) {
            mURL = url;
        }

        // Background work
        protected String doInBackground(Integer... params) {
            String result = null;
            try {
                // Open the connection
                URL url = new URL(mURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();

                // Get the stream
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                // Set the result
                result = builder.toString();
                return result;
            }
            catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
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
                    gotoActivity(ItemOverview.class);
                    break;
                case R.id.go_notification:
                    gotoActivity(Ranking.class);
                    break;
                case R.id.go_disclosure:
                    gotoActivity(Disclosure.class);
                    break;
                case R.id.edittext:
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                    break;
                case R.id.buy_btn:
                    total_text.setText("총 주문 금액");
                    order_btn.setText("매수하기");
                    break;
                case R.id.sell_btn:
                    total_text.setText("총 판매 금액");
                    order_btn.setText("매도하기");
                    break;
                case R.id.order_btn:
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