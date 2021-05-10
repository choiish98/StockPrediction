package techtown.org;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Disclosure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclosure);

        // rest api 통신
        RestAPITask restAPITask = new RestAPITask("http://78834cb91eae.ngrok.io/stocks/api/news/list");
        restAPITask.execute();

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        News[] news = new News[10];
        for(int i = 0; i < news.length; i++) {
            news[i] = new News("안녕", "나는 안드로이드 개발자 최승혁이야", "2021-05-09",
                    "대한일보", "어떤이미지의경로...");
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        NewsListAdapter adapter = new NewsListAdapter(news);
        recyclerView.setAdapter(adapter);
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
                Log.e("result: ", result);
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
}