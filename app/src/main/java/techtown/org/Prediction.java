package techtown.org;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.graphics.Color.rgb;

public class Prediction extends AppCompatActivity {

    private LineChart mChart;
    RecyclerView recyclerView;
    RecyclerView stock_recyclerView;
    EditText editText;
    PredictionAdapter adapter;
    StockAdapter stockAdapter;

    LinearLayout invest;

    TextView point;
    TextView total_text;
    TextView total;
    TextView now_price;
    Button order_btn;

    ArrayList<String> items = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        // ?????? ?????????
        now_price = (TextView) findViewById(R.id.now_price);
        total = (TextView) findViewById(R.id.total);
        total_text = (TextView) findViewById(R.id.total_text);
        order_btn = (Button) findViewById(R.id.order_btn);
        point = (TextView) findViewById(R.id.point);
        invest = (LinearLayout) findViewById(R.id.invest);

        // ?????? ??????
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

        // textWatcher
        EditText editText2 = findViewById(R.id.amount);
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence != null) {
                    if (editText2.getText() != null) {
                        String temp = charSequence.toString().replace(",", "");
                        String temp2 = now_price.getText().toString().replace(",", "");
                        int price = Integer.parseInt(temp) * Integer.parseInt(temp2);
                        total.setText(NumberFormat.getInstance(Locale.getDefault()).format(price));
                    } else {
                        total.setText("");
                    }
                } else {
                    total.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // ????????? ??????
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
                // code??? ?????? ?????? ??????
                try {
                    // ????????? ????????????
                    String url = new APIURL().getApiURL();
                    String now_price = new asyncTask(url.concat("/stocks/api/stocks/now_price?code=").concat(code)).execute().get();
                    JSONObject jsonObject = new JSONObject(now_price);
                    ((TextView) findViewById(R.id.now_price)).setText(jsonObject.getString("stock_info_big_left_price"));

                    // ?????????
                    mChart = (LineChart) findViewById(R.id.prediction_lineChart);
                    findViewById(R.id.predict_layout).setVisibility(View.VISIBLE);

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

                    String graph = new asyncTask(url.concat("/stocks/api/graph_html?companyName=").concat("LG")).execute().get();
                    JSONObject valueObject = new JSONObject(graph);
                    JSONArray valueDataObject = valueObject.getJSONArray("data");
                    JSONObject realObject = valueDataObject.getJSONObject(0);
                    JSONArray realdata = realObject.getJSONArray("y");
                    Log.e("realdata", realdata.toString());

                    for(int i = 0; i < realdata.length(); i++) {
                        yValues.add(new Entry(i, Float.parseFloat(realdata.getString(i))));
                    }

                    LineDataSet set1 = new LineDataSet(yValues, "LG".concat("??????"));

                    set1.setFillAlpha(110);
                    set1.setColor(rgb(170, 26, 26));
                    set1.setLineWidth(2f);
                    set1.setCircleColor(rgb(170, 26, 26));
                    set1.setFillColor(rgb(170, 26, 26));
                    set1.setDrawHighlightIndicators(false);
                    set1.setDrawValues(false);
                    set1.setValueTextColor(Color.WHITE);


                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1);

                    LineData data = new LineData(dataSets);

                    mChart.setData(data);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // ?????? ?????? recycler
        stock_recyclerView = (RecyclerView)findViewById(R.id.stockRecylcerview);
        stock_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        // data
        // ????????? ?????? ????????????
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                String nickname = result.getKakaoAccount().getProfile().getNickname();
                try {
                    // ??? ?????? ?????????
                    String url = new APIURL().getApiURL();
                    String pointString = new asyncTask(url.concat("/users/get_user_point_api?nickname=").concat(nickname)).execute().get();
                    JSONObject urlObject = new JSONObject(pointString);
                    int temp = Integer.parseInt(urlObject.getString("point"));
                    point.setText(NumberFormat.getInstance(Locale.getDefault()).format(temp));

                    // ??? ?????? ??????
                    String stock_data = new asyncTask(url.concat("/stocks/api/stocks/simul_stock_list?nickname=").concat(nickname).concat("(?????????)")).execute().get();
                    JSONArray jsonArray = new JSONArray(stock_data);
                    Stock[] stock_items = new Stock[jsonArray.length()];
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject object = jsonObject.getJSONObject("fields");
                        Log.e("jsonObject", object.toString());

                        stock_items[i] = new Stock();
                        stock_items[i].setStock_name(object.getString("stock_name"));
                        stock_items[i].setAmount(object.getString("amount"));
                        stock_items[i].setSize(jsonArray.length());

                        stockAdapter = new StockAdapter(stock_items);
                        stock_recyclerView.setAdapter(stockAdapter);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // ?????? ?????????
        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_notification).setOnClickListener(onClickListener);
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
        findViewById(R.id.go_overview).setOnClickListener(onClickListener);
        findViewById(R.id.buy_btn).setOnClickListener(onClickListener);
        findViewById(R.id.sell_btn).setOnClickListener(onClickListener);
        findViewById(R.id.order_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // http://stockpredict.kr/stocks/buy_stock
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        TpScret tpScret = new TpScret();
                        String url = new APIURL().getApiURL();
                        String urlplus;
                        if(order_btn.getText().toString().equals("????????????")) {
                            urlplus = "/stocks/buy_stock?";
                        } else {
                            urlplus = "/stocks/sell_stock?";
                        }
                        new RedirectAPI(url.concat(urlplus)
                                .concat("tp_secret=").concat(tpScret.getScret())
                                .concat("&nickname=").concat(result.getKakaoAccount().getProfile().getNickname())
                                .concat("&amount=").concat(editText2.getText().toString())
                                .concat("&current_price=").concat(now_price.getText().toString().replace(",",""))
                                .concat("&company_name=").concat(editText.getText().toString())).execute();

                        finish();
                        startActivity(getIntent());
                    }
                });
            }
        });
    }

    // ??????????????? ????????? ?????? asyncTask
    public static class asyncTask extends AsyncTask<Integer, Void, String> {
        // Variable to store url
        protected String mURL;

        // Constructor
        public asyncTask(String url) {
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

    // onClickListener ??????
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
                    invest.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(adapter);
                    break;
                case R.id.buy_btn:
                    total_text.setText("??? ?????? ??????");
                    order_btn.setText("????????????");
                    break;
                case R.id.sell_btn:
                    total_text.setText("??? ?????? ??????");
                    order_btn.setText("????????????");
                    break;
            }
        }
    };

    // intent Acitivity ??????
    private void gotoActivity(Class c) {
        Intent intent = new Intent(Prediction.this, c);
        startActivity(intent);
    }

    // ??????????????? ????????? ?????? asyncTask
    public static class RedirectAPI extends AsyncTask<Integer, Void, String> {
        // Variable to store url
        protected String mURL;

        // Constructor
        public RedirectAPI(String url) {
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
            } catch (Exception e) {
                // Error calling the rest api
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }
}