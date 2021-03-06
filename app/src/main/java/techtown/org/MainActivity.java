package techtown.org;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    TextView textView, textView2, textView3, textView4;
    Animation flowAnim, flowAnim2, alphaAnim, alphaAnim2;

    private Button login;
    private Button logout;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                login.setVisibility(View.VISIBLE);
                logout.setVisibility(View.INVISIBLE);
                Log.e("KAKAO_API", "????????? ?????? ??????: " + errorResult);
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("KAKAO_API", "?????? ?????? ?????? ??????: " + errorResult);
            }

            @Override
            public void onSuccess(AccessTokenInfoResponse result) {
                login.setVisibility(View.INVISIBLE);
                logout.setVisibility(View.VISIBLE);
                Log.i("KAKAO_API", "????????? ?????????: " + result.getUserId());
                Log.i("KAKAO_API", "?????? ??????(s): " + result.getExpiresInMillis());;
            }
        });

        // ?????? ??????
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        login = (Button) findViewById(R.id.login);
        logout = (Button) findViewById(R.id.logout);

        // ???????????????
        flowAnim = AnimationUtils.loadAnimation(this, R.anim.flow);
        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        flowAnim2 = AnimationUtils.loadAnimation(this, R.anim.flow);
        alphaAnim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);


        // ?????? ?????????
        findViewById(R.id.button1).setOnClickListener(onClickListener);
        findViewById(R.id.button2).setOnClickListener(onClickListener);
        findViewById(R.id.button3).setOnClickListener(onClickListener);
        findViewById(R.id.button4).setOnClickListener(onClickListener);

        login.setOnClickListener(onClickListener);
        logout.setOnClickListener(onClickListener);
    }

    // onClickListener ??????
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button1:
                    gotoActivity(Disclosure.class);
                    break;
                case R.id.button2:
                    gotoActivity(ItemOverview.class);
                    break;
                case R.id.button3:
                    AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) {
                            Toast.makeText(getApplicationContext(),"???????????? ???????????????.",Toast.LENGTH_SHORT);
                            Log.e("KAKAO_API", "????????? ?????? ??????: " + errorResult);
                        }

                        @Override
                        public void onFailure(ErrorResult errorResult) {
                            Log.e("KAKAO_API", "?????? ?????? ?????? ??????: " + errorResult);
                        }

                        @Override
                        public void onSuccess(AccessTokenInfoResponse result) {
                            gotoActivity(Prediction.class);
                            Log.i("KAKAO_API", "????????? ?????????: " + result.getUserId());
                        }
                    });
                    break;
                case R.id.button4:
                    gotoActivity(Ranking.class);
                    break;
                case R.id.login:
                    session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);
                    break;
                case R.id.logout:
                    UserManagement.getInstance()
                            .requestLogout(new LogoutResponseCallback() {
                                @Override
                                public void onCompleteLogout() {
                                    Toast.makeText(MainActivity.this, "log out", Toast.LENGTH_SHORT).show();
                                    Intent intent = getIntent();
                                    startActivity(intent);
                                }
                            });
                    finish();
                    startActivity(getIntent());
                    break;
            }
        }
    };

    // intent Acitivity ??????
    private void gotoActivity(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        textView.startAnimation(flowAnim);
        textView2.startAnimation(alphaAnim);
        textView3.startAnimation(flowAnim2);
        textView4.startAnimation(alphaAnim2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // ?????? ?????? ??????
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // ????????????|????????? ??????????????? ?????? ????????? ????????? SDK??? ??????
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}