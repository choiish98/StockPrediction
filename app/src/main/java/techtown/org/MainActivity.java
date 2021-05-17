package techtown.org;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    TextView textView, textView2, textView3, textView4;
    Animation flowAnim, flowAnim2, alphaAnim, alphaAnim2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 변수 선언
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);

        // 애니메이션
        flowAnim = AnimationUtils.loadAnimation(this, R.anim.flow);
        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        flowAnim2 = AnimationUtils.loadAnimation(this, R.anim.flow);
        alphaAnim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);


        // 클릭 리스너
        findViewById(R.id.button1).setOnClickListener(onClickListener);
        findViewById(R.id.button2).setOnClickListener(onClickListener);
        findViewById(R.id.button3).setOnClickListener(onClickListener);
        findViewById(R.id.button4).setOnClickListener(onClickListener);
    }

    // onClickListener 정의
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button1:
                    gotoActivity(Disclosure.class);
                    break;
                case R.id.button2:
                    gotoActivity(Item_overview.class);
                    break;
                case R.id.button3:
                    gotoActivity(Prediction.class);
                    break;
                case R.id.button4:
                    gotoActivity(Notification.class);
                    break;
            }
        }
    };

    // intent Acitivity 정의
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
}