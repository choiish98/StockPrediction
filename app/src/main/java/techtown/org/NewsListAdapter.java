package techtown.org;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private News[] mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView imageView2;
        TextView newsTitle;
        TextView newsSummary;
        TextView newsDate;
        TextView news;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            imageView2 = itemView.findViewById(R.id.imageView2);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsSummary = itemView.findViewById(R.id.newsSummary);
            newsDate = itemView.findViewById(R.id.newsDate);
            news = itemView.findViewById(R.id.news);
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
        String news = mData[position].getNews();
        String img = mData[position].getImg();

        holder.newsTitle.setText(title);
        holder.newsSummary.setText(summary);
        holder.newsDate.setText(date);
        holder.news.setText(news);
        holder.news.setText(img);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData[0].size();
    }
}