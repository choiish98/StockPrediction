package techtown.org;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // 클릭 리스너
        findViewById(R.id.go_home).setOnClickListener(onClickListener);
        findViewById(R.id.go_disclosure).setOnClickListener(onClickListener);
        findViewById(R.id.go_overview).setOnClickListener(onClickListener);
        findViewById(R.id.go_predict).setOnClickListener(onClickListener);
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
                    gotoActivity(Ranking.class);
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
        Intent intent = new Intent(Notification.this, c);
        startActivity(intent);
    }
}