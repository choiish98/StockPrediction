package techtown.org;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Ranklist[] mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView rankImg;
        TextView rank;
        TextView rankingName;
        TextView rankingTotalPoint;
        TextView rankingProfit;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            rankImg = itemView.findViewById(R.id.rankImg);
            rank = itemView.findViewById(R.id.rank);
            rankingName = itemView.findViewById(R.id.rankingName);
            rankingTotalPoint = itemView.findViewById(R.id.rankingTotalPoint);
            rankingProfit = itemView.findViewById(R.id.rankingProfit);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    RankingAdapter(Ranklist[] news) {
        mData = news;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.ranking_item, parent, false);
        RankingAdapter.ViewHolder vh = new RankingAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RankingAdapter.ViewHolder holder, int position) {
        Drawable rankImg = mData[position].getRankImg();
        String rank = mData[position].getRank();
        String rankingName = mData[position].getRankingName();
        String rankingTotalPoint = mData[position].getRankingTotalPoint();
        String rankingProfit = mData[position].getRankingProfit();

        holder.rankImg.setImageDrawable(rankImg);
        holder.rank.setText(rank);
        holder.rankingName.setText(rankingName);
        holder.rankingTotalPoint.setText(rankingTotalPoint);
        holder.rankingProfit.setText(rankingProfit);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData[0].size();
    }
}