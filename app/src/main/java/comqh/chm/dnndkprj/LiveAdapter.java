package comqh.chm.dnndkprj;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chm.live.LiveManager;
import com.chm.live.list.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/11/14.
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.MyHolder> implements View.OnClickListener{


    private LayoutInflater mInflater;

    private OnItemClickListener itemClickListener;

    private List<Item> items;

    public LiveAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        items = new ArrayList<>();
    }


    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_room, parent, false);
        v.setOnClickListener(this);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Item item = items.get(position);
        holder.title.setText(item.getName());
        Glide.with(holder.picture).load(item.getPictures().getImg()).into(holder.picture);
        holder.itemView.setTag(item.getId());
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener !=null)
            itemClickListener.onItemClick((String) v.getTag());
    }


    public interface OnItemClickListener {
        void onItemClick(String id);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        ImageView picture;
        TextView title;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture);
            title = itemView.findViewById(R.id.title);
        }
    }
}
