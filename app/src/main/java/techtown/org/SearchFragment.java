package techtown.org;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {
    List<String> list;
    ListView listView;
    EditText editSearch;
    SearchAdapter adapter;
    ArrayList<String> arraylist;



    public SearchFragment(){
        // Required empty public constructor
    }

    public static SearchFragment newInstance(){
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.prediction_fragment, container, false);
        editSearch = (EditText) v.findViewById(R.id.search);
        //editSearch.
        listView = (ListView) v.findViewById(R.id.listView);
        list = new ArrayList<String>();

        settingList();

        arraylist = new ArrayList<String>();
        arraylist.addAll(list);
        list.clear();

        adapter = new SearchAdapter(list, container.getContext());

        listView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = editSearch.getText().toString();
                search(text);
            }
        });


        editSearch.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override public boolean onTouch(View v, MotionEvent event)
            {

                listView.setVisibility(View.VISIBLE);

                return false;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(container.getContext() ,"성공성공성공", Toast.LENGTH_LONG).show();

            }
        });


        return v;
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();

        if (charText.length() != 0) {
            for(int i = 0;i < arraylist.size(); i++)
            {
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    list.add(arraylist.get(i));
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    private void settingList(){
        list.add("asdf");
        list.add("thdefn");
        list.add("gggdsfaf");
        list.add("asdfghjk");
        list.add("asdfghjkdfsfa");
        list.add("asdf5");
        list.add("asdf6");

    }
}

