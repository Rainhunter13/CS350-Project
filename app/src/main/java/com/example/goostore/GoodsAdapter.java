package com.example.goostore;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Goods> mGoods;
    private String selectedCategory = "";
    private OnItemClickListener mListener;


    public GoodsAdapter(Context context, List<Goods> goods){
        mContext = context;
        mGoods = goods;
    }

    public GoodsAdapter(Context context, List<Goods> goods, String category) {
        mContext = context;
        mGoods = goods;
        selectedCategory = category;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.goods_list, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Goods goodsCurrent = mGoods.get(position);

        //This situation is for the category page
        if(goodsCurrent.getCategory().equals(selectedCategory)) {
            holder.textViewName.setText(goodsCurrent.getName());
            holder.textViewCurrentPrice.setText(goodsCurrent.getBasePrice() + "WON");
            holder.textViewDeadLine.setText(goodsCurrent.getDeadLine());
            Picasso.get()
                    .load(goodsCurrent.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .into(holder.imageView);
        } else if(selectedCategory.equals("")){

            //This situation is for myAuction Page
            holder.textViewName.setText(goodsCurrent.getName());
            holder.textViewCurrentPrice.setText(goodsCurrent.getBasePrice() + "WON");
            holder.textViewDeadLine.setText(goodsCurrent.getDeadLine());
            Picasso.get()
                    .load(goodsCurrent.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mGoods.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public TextView textViewCurrentPrice;
        public TextView textViewDeadLine;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_goods_name);
            textViewCurrentPrice = itemView.findViewById(R.id.text_goods_price);
            textViewDeadLine = itemView.findViewById(R.id.text_goods_deadline);
            imageView = itemView.findViewById(R.id.goods_image);

            itemView.setOnClickListener(this);
            OnItemClickListener listener = (OnItemClickListener) mContext;
            setOnItemClickListener(listener);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(view, position);
                }
            }
        }


    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
