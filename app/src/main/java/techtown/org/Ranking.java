package techtown.org;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.String;
import java.text.NumberFormat;
import java.util.Locale;


public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Ranklist rankClass[];

        try {
            // rest api 통신// rest api 통신
            String url = new GlobalApplication().getApiURL();
            String rankList = new RestAPITask(url.concat("/users/api/list")).execute().get();

            // 정규화 필요
            JSONArray jsonArray = new JSONArray(rankList);
            rankClass = new Ranklist[jsonArray.length()];
            for(int i = 0;  i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rankClass[i] = new Ranklist();
                rankClass[i].setRankingName(jsonObject.getString("nickname"));
                rankClass[i].setRankingPoint(NumberFormat.getInstance(Locale.getDefault()).format(Integer.parseInt(jsonObject.getString("point"))));
                rankClass[i].setRankingProfit(NumberFormat.getInstance(Locale.getDefault()).format(Integer.parseInt(jsonObject.getString("net_gain"))));
                rankClass[i].setSize(jsonArray.length());
            }

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            RecyclerView recyclerView = findViewById(R.id.rankingRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            RankingAdapter adapter = new RankingAdapter(rankClass);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 클릭 리스너
        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_notification).setOnClickListener(onClickListener);
        findViewById(R.id.go_overview).setOnClickListener(onClickListener);
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
                case R.id.go_overview:
                    gotoActivity(ItemOverview.class);
                    break;
                case R.id.go_notification:
                    gotoActivity(Notification.class);
                    break;
                case R.id.go_predict:
                    gotoActivity(Prediction.class);
                    break;
            }
        }
    };

    // intent Acitivity 정의
    private void gotoActivity(Class c) {
        Intent intent = new Intent(Ranking.this, c);
        startActivity(intent);
    }
}
