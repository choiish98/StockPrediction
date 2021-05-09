package techtown.org;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class disclosure extends AppCompatActivity {

    public class Stock{
        private String name;

        public Stock(String name){
            this.name = name;
        }
    }
    String[] names = {"A","B","C"};
    int count =0;

    ArrayList<Stock> stocks = new ArrayList<Stock>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclosure);
    }
}