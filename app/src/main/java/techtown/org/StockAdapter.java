package techtown.org;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private Stock[] stocks = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView label1;
        TextView label2;
        TextView label3;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            label1 = itemView.findViewById(R.id.label);
            label2 = itemView.findViewById(R.id.label2);
            label3 = itemView.findViewById(R.id.label3);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    StockAdapter(Stock[] stocks) {
        this.stocks = stocks;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.prediction_stock_item, parent, false);
        StockAdapter.ViewHolder vh = new StockAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(StockAdapter.ViewHolder holder, int position) {
        String stock_name = stocks[position].getStock_name();
        String amount = stocks[position].getAmount();
        String gae = stocks[position].getGae();

        holder.label1.setText(stock_name);
        holder.label2.setText(amount);
        holder.label3.setText(gae);
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return stocks[0].size();
    }
}