package techtown.org;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Disclosure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclosure);

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
}