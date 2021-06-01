package techtown.org;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

public class Disclosure extends AppCompatActivity {

    // 뉴스 리스트 객체 및 변수 선언
    News[] newsClass;
    News[] gongsiClass;
    int newsIndex = 0;
    int gongsiIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclosure);

        try {
            // rest api 통신
            String url = new GlobalApplication().getApiURL();
            String newsList = new RestAPITask(url.concat("/stocks/api/news/list")).execute().get();
            Log.e("news",newsList);
            JSONArray jsonArray = new JSONArray(newsList);

            newsClass = new News[jsonArray.length()];
            gongsiClass = new News[jsonArray.length()];
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(jsonObject.optString("category").equals("news")) {
                    newsClass[newsIndex] = new News();
                    newsClass[newsIndex].setTitle(jsonObject.optString("title"));
                    newsClass[newsIndex].setSummary(jsonObject.optString("summary"));
                    newsClass[newsIndex].setPress(jsonObject.optString("press"));
                    newsClass[newsIndex].setDate(jsonObject.optString("wdate"));
                    newsClass[newsIndex].setImg(jsonObject.optString("thumbnail_link"));
                    newsClass[newsIndex].setLink(jsonObject.optString("link"));
                    newsIndex++;
                }
                if(jsonObject.optString("category").equals("gongsi")) {
                    gongsiClass[gongsiIndex] = new News();
                    gongsiClass[gongsiIndex].setTitle(jsonObject.optString("title"));
                    gongsiClass[gongsiIndex].setSummary(jsonObject.optString("summary"));
                    gongsiClass[gongsiIndex].setPress(jsonObject.optString("press"));
                    gongsiClass[gongsiIndex].setDate(jsonObject.optString("wdate"));
                    gongsiClass[gongsiIndex].setLink(jsonObject.optString("link"));
                    gongsiIndex++;
                }
            }

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            NewsListAdapter adapter = new NewsListAdapter(newsClass);
            recyclerView.setAdapter(adapter);

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            RecyclerView recyclerView2 = findViewById(R.id.recyclerView2);
            recyclerView2.setLayoutManager(new LinearLayoutManager(this));

            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            Log.e("gonsi: ", gongsiClass[1].getTitle());
            GongsiAdapter adapter2 = new GongsiAdapter(gongsiClass);
            recyclerView2.setAdapter(adapter2);
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
                    gotoActivity(Ranking.class);
                    break;
                case R.id.go_predict:
                    gotoActivity(Prediction.class);
                    break;
            }
        }
    };

    // intent Acitivity 정의
    private void gotoActivity(Class c) {
        Intent intent = new Intent(Disclosure.this, c);
        startActivity(intent);
    }

}