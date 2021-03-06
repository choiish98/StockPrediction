package techtown.org;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;

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
            String url = new APIURL().getApiURL();
            String rankList = new RestAPITask(url.concat("/users/api/list")).execute().get();

            // 정규화 필요
            JSONArray jsonArray = new JSONArray(rankList);
            Log.e("ranklist", jsonArray.toString());
            rankClass = new Ranklist[jsonArray.length()];
            for(int i = 0;  i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                rankClass[i] = new Ranklist();
                switch(i) {
                    case 0 :
                        rankClass[i].setRankImg(getResources().getDrawable(R.drawable.medal1));
                        rankClass[i].setRank(null);
                        break;
                    case 1 :
                        rankClass[i].setRankImg(getResources().getDrawable(R.drawable.medal2));
                        rankClass[i].setRank(null);
                        break;
                    case 2 :
                        rankClass[i].setRankImg(getResources().getDrawable(R.drawable.medal3));
                        rankClass[i].setRank(null);
                        break;
                    default :
                        rankClass[i].setRankImg(null);
                        rankClass[i].setRank(String.valueOf(i + 1));
                        break;
                }
                rankClass[i].setRankingName(jsonObject.getString("nickname"));
                rankClass[i].setRankingTotalPoint(NumberFormat.getInstance(Locale.getDefault()).format(Integer.parseInt(jsonObject.getString("point")))+" P");
                rankClass[i].setRankingProfit(NumberFormat.getInstance(Locale.getDefault()).format(Integer.parseInt(jsonObject.getString("net_gain")))+" P");
                rankClass[i].setSize(jsonArray.length());
            }

            Log.e("asedf", rankClass[0].toString());

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
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
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
                case R.id.go_disclosure:
                    gotoActivity(Disclosure.class);
                    break;
                case R.id.go_predict:
                    AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT);
                            Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "토큰 정보 요청 실패: " + errorResult);
                        }

                        @Override
                        public void onSuccess(AccessTokenInfoResponse result) {
                            gotoActivity(Prediction.class);
                            Log.i("KAKAO_API", "사용자 아이디: " + result.getUserId());
                        }
                    });
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