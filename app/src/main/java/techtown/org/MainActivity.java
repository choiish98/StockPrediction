package techtown.org;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
                    gotoActivity(disclosure.class);
                    break;
                case R.id.button2:
                    gotoActivity(item_overview.class);
                    break;
                case R.id.button3:
                    gotoActivity(prediction.class);
                    break;
                case R.id.button4:
                    gotoActivity(notification.class);
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