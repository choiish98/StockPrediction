package techtown.org;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GongsiAdapter extends RecyclerView.Adapter<GongsiAdapter.ViewHolder> {

    private News[] mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView gongsiTitle;
        TextView gongsiSummary;
        TextView gongsiDate;
        TextView gongsiPress;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            gongsiTitle = itemView.findViewById(R.id.gongsiTitle);
            gongsiSummary = itemView.findViewById(R.id.gongsiSummary);
            gongsiDate = itemView.findViewById(R.id.gongsiDate);
            gongsiPress = itemView.findViewById(R.id.gongsiPress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mData[0].getLink()));
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    GongsiAdapter(News[] news) {
        mData = news;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public GongsiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.gongsilist_item, parent, false);
        GongsiAdapter.ViewHolder vh = new GongsiAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(GongsiAdapter.ViewHolder holder, int position) {
        String title = mData[position].getTitle();
        String summary = mData[position].getSummary();
        String date = mData[position].getDate();
        String press = mData[position].getPress();

        holder.gongsiTitle.setText(title);
        holder.gongsiSummary.setText(summary);
        holder.gongsiDate.setText(date);
        holder.gongsiPress.setText(press);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData[0].size();
    }
}