package techtown.org;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<String> unFilteredlist;
    ArrayList<String> filteredList;

    public PredictionAdapter(Context context, ArrayList<String> list) {
        super();
        this.context = context;
        this.unFilteredlist = list;
        this.filteredList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.overview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(filteredList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, String code);
    }

    private ItemAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(ItemAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textview);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        String stockList;
                        try {
                            // 아이템 클릭 시 종목 코드 받아오기
                            String stock = textView.getText().toString();
                            String apiURL = new APIURL().getApiURL();
                            String url = "/stocks/api/related_companies?text=";
                            stockList = new ItemAdapter.RestAPITask(apiURL.concat(url.concat(stock))).execute().get();
                            JSONObject jsonObject = new JSONObject(stockList);
                            JSONArray jsonArray = jsonObject.getJSONArray("companies");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            String code = obj.getString("code");
                            if(mListener != null) {
                                mListener.onItemClick(view, code);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    filteredList = unFilteredlist;
                } else {
                    ArrayList<String> filteringList = new ArrayList<>();
                    for(String name : unFilteredlist) {
                        if(name.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(name);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<String>)results.values;
                notifyDataSetChanged();
            }
        };
    }

}