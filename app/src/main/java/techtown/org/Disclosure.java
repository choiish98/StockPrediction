package techtown.org;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

        // 뉴스 리스트 객체 및 변수 선언
        News[] newsClass;
        String title = "[마켓뷰] 코스피, 미국 증시 훈풍 속 역대 최고가 경신";
        String summary = null;
        String press = null;
        String wdate = null;
        String link = null;
        int index = 0;

        try {
            // rest api 통신
            String newsList = new RestAPITask("http://1ad1946bd7c4.ngrok.io/stocks/api/news/list").execute().get();

            String[] news = newsList.split("\\},");
            newsClass = new News[news.length];
            for(String st : news) {
                newsClass[index] = new News();
                String[] newsId = st.split("\",");
                for (String item : newsId) {
                    item = item.replace("\"", "");
                    item = item.replace("\\{", "");
                    item = item.replace("\\}", "");
                    String[] newsItem = item.split(":");
                    if (newsItem[0].equals("{title")) {
                        title = newsItem[1];
                        newsClass[index].setTitle(title);
                        Log.e("title", title);
                    } else if (newsItem[0].equals("summary")) {
                        summary = newsItem[1];
                        newsClass[index].setSummary(summary);
                        Log.e("summary", summary);
                    } else if (newsItem[0].equals("press")) {
                        press = newsItem[1];
                        newsClass[index].setNews(press);
                        Log.e("press", press);
                    } else if (newsItem[0].equals("wdate")) {
                        wdate = newsItem[1];
                        newsClass[index].setDate(wdate);
                        Log.e("wdate", wdate);
                    } else if (newsItem[0].equals("link")) {
                        link = newsItem[1];
                        newsClass[index++].setImg(link);
                        Log.e("link", link);
                    }
                }
            }

            for(int i = 0; i < newsClass.length; i++)
                System.out.println(newsClass[i]);

            // 리사이클러뷰에 LinearLayoutManager 객체 지정.
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
            NewsListAdapter adapter = new NewsListAdapter(newsClass);
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
                    gotoActivity(Item_overview.class);
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
        Intent intent = new Intent(Disclosure.this, c);
        startActivity(intent);
    }

}