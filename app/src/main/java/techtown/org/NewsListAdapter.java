package techtown.org;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private News[] mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView newsTitle;
        TextView newsSummary;
        TextView newsDate;
        TextView press;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            img = itemView.findViewById(R.id.img);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsSummary = itemView.findViewById(R.id.newsSummary);
            newsDate = itemView.findViewById(R.id.newsDate);
            press = itemView.findViewById(R.id.press);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData[getAdapterPosition()].getLink()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    NewsListAdapter(News[] news) {
        mData = news;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.newslist_item, parent, false);
        NewsListAdapter.ViewHolder vh = new NewsListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        String title = mData[position].getTitle();
        String summary = mData[position].getSummary();
        String date = mData[position].getDate();
        String press = mData[position].getPress();
        try {
            String img = mData[position].getImg();
            if(img.isEmpty()){
                Log.e("img:", "isEmpty");
            } else {
                holder.img.setVisibility(View.VISIBLE);
                holder.img.setImageBitmap(new downImgITask(img).execute().get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        holder.newsTitle.setText(title);
        holder.newsSummary.setText(summary);
        holder.newsDate.setText(date);
        holder.press.setText(press);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData[0].size();
    }

    // 백그라운드 동작을 위한 asyncTask
    public static class downImgITask extends AsyncTask<Integer, Void, Bitmap> {
        // Variable to store url
        protected String mURL;

        // Constructor
        public downImgITask(String url) {
            mURL = url;
        }

        // Background work
        protected Bitmap doInBackground(Integer... params) {
            Bitmap result = null;
            try {
                // Open the connection
                URL url = new URL(mURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                int nSize = conn.getContentLength();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);

                // Set the result
                result = BitmapFactory.decodeStream(bis);
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
}