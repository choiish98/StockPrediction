package techtown.org;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.AutoText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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
import com.skydoves.elasticviews.ElasticButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.rgb;

public class ItemOverview extends AppCompatActivity {


    TextView siga, stock_info_big_left_price, jeonildaebi,
            georaeryang, georaedaegeum, goga, jeoga, sigasum, foreignper, objectjuga, pereps, ju52;

    private LineChart mChart;
    RecyclerView recyclerView;
    ImageButton searchbtn;
    EditText editText;
    ItemAdapter adapter;

    ArrayList<String> items = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_overview);

        // 변수 선언
        siga = findViewById(R.id.siga);
        stock_info_big_left_price= findViewById(R.id.stock_info_big_left_price);
        jeonildaebi = findViewById(R.id.jeonildaebi);
        georaeryang = findViewById(R.id.georaeryang);
        georaedaegeum = findViewById(R.id.georaedaegeum);
        goga = findViewById(R.id.goga);
        jeoga = findViewById(R.id.jeoga);
        sigasum = findViewById(R.id.sigasum);
        foreignper = findViewById(R.id.foreignper);
        objectjuga = findViewById(R.id.objectjuga);
        pereps = findViewById(R.id.pereps);
        ju52 = findViewById(R.id.ju52);;

        // 곡선 그래프
        mChart = (LineChart) findViewById(R.id.lineChart);

        //mChart.setOnChartGestureListener(MainActivity.this);
        //mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.getXAxis().setDrawLabels(false);
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
        //키보드 숨기기
//        public static void hideSoftKeyboard(Activity activity) {
//            InputMethodManager inputMethodManager =
//                    (InputMethodManager) activity.getSystemService(
//                            Activity.INPUT_METHOD_SERVICE);
//            if(inputMethodManager.isAcceptingText()){
//                inputMethodManager.hideSoftInputFromWindow(
//                        activity.getCurrentFocus().getWindowToken(),
//                        0
//                );
//            }
//        }

        // 아이템 추가
        try {
            String url = new GlobalApplication().getApiURL();
            String stockList = new RestAPITask(url.concat("/stocks/api/stocks/list")).execute().get();
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

        adapter = new ItemAdapter(getApplicationContext(), items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String code) {
                try {
                    String infoURL = "/stocks/api/stock_suminfo_data?code=";
                    String apiURL = new GlobalApplication().getApiURL();
                    String info = new RestAPITask(apiURL.concat(infoURL).concat(code)).execute().get();
                    JSONObject jsonObject = new JSONObject(info);
                    String stock_info_big_left_price1 = jsonObject.getString("stock_info_big_left_price");
                    String jeonildaebi1 = jsonObject.getString("jeonildaebi");
                    String georaeryang1 = jsonObject.getString("georaeryang");
                    String georaedaegeum1 = jsonObject.getString("georaedaegeum");
                    String goga1 = jsonObject.getString("goga");
                    String jeoga1 = jsonObject.getString("jeoga");
                    String sigasum1 = jsonObject.getString("sigasum");
                    String foreignper1 = jsonObject.getString("foreignper");
                    String objectjuga1 = jsonObject.getString("objectjuga");
                    String pereps1 = jsonObject.getString("pereps");
                    String ju521 = jsonObject.getString("ju52");
                    String infoSiga = jsonObject.getString("siga");
                    siga.setText(infoSiga);
                    stock_info_big_left_price.setText(stock_info_big_left_price1);
                    jeonildaebi.setText(jeonildaebi1);
                    georaeryang.setText(georaeryang1);
                    georaedaegeum.setText(georaedaegeum1);
                    goga.setText(goga1);
                    jeoga.setText(jeoga1);
                    sigasum.setText(sigasum1);
                    foreignper.setText(foreignper1);
                    objectjuga.setText(objectjuga1);
                    pereps.setText(pereps1);
                    ju52.setText(ju521);;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // 클릭 리스너
        ElasticButton elasticButton_go_home = (ElasticButton)findViewById(R.id.go_home);
        elasticButton_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });
        ElasticButton elasticButton_go_ranking = (ElasticButton)findViewById(R.id.go_ranking);
        elasticButton_go_ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });
        ElasticButton elasticButton_go_disclosure = (ElasticButton)findViewById(R.id.go_disclosure);
        elasticButton_go_disclosure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });ElasticButton elasticButton_go_predict = (ElasticButton)findViewById(R.id.go_predict);
        elasticButton_go_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something
            }
        });

        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_ranking).setOnClickListener(onClickListener);
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
        findViewById(R.id.go_predict).setOnClickListener(onClickListener);
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
                case R.id.go_disclosure:
                    gotoActivity(Disclosure.class);
                    break;
                case R.id.go_ranking:
                    gotoActivity(Ranking.class);
                    break;
                case R.id.go_predict:
                    gotoActivity(Prediction.class);
                    break;
                case R.id.edittext:
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };

    // intent Acitivity 정의
    private void gotoActivity(Class c) {
        Intent intent = new Intent(ItemOverview.this, c);
        startActivity(intent);
    }
}