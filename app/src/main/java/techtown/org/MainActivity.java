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

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButton1Clicked(MainActivity.this);
            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);

        textView = (TextView) findViewById(R.id.textView);
        flowAnim = AnimationUtils.loadAnimation(this, R.anim.flow);

        textView2 = (TextView) findViewById(R.id.textView2);
        alphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);

        textView3 = (TextView) findViewById(R.id.textView3);
        flowAnim2 = AnimationUtils.loadAnimation(this, R.anim.flow);

        textView4 = (TextView) findViewById(R.id.textView4);
        alphaAnim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);

    }

    public void onButton1Clicked(MainActivity v) {
        Intent intent = new Intent(this, item_overview.class);
        startActivity(intent);
    }
    public void onButton2Clicked(View v) {
        Intent intent2 = new Intent(this, disclosure.class);
        startActivity(intent2);
    }
    public void onButton3Clicked(View v) {
        Intent intent3 = new Intent(this, item_overview.class);
        startActivity(intent3);
    }
    public void onButton4Clicked(View v) {
        Intent intent4 = new Intent(this, prediction.class);
        startActivity(intent4);
    }
    public static class FragmentBottom extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            return inflater.inflate(R.layout.notification, null);
        }
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