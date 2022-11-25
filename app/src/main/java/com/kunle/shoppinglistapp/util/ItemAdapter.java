package com.kunle.shoppinglistapp.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.RecyclerItem;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<RecyclerItem> recyclerItemList;

    public ItemAdapter(Context context, ArrayList<RecyclerItem> recyclerItemList) {
        this.context = context;
        this.recyclerItemList = recyclerItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.inner_cardview,parent,false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String name = recyclerItemList.get(position).getName();
        String quantity = String.valueOf(recyclerItemList.get(position).getQuantity());
        String measurement = recyclerItemList.get(position).getMeasurement();
        String parenthesis;
        if (measurement == "") {
            parenthesis = "(" + quantity + ")";
        } else {
            parenthesis = "(" + quantity + " " + measurement + ")";
        }
        SpannableString first_part = new SpannableString(name);

        SpannableString second_part = new SpannableString(parenthesis);
        second_part.setSpan(new ForegroundColorSpan(Color.GRAY),
                0,parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        second_part.setSpan(new AbsoluteSizeSpan(12,true),
                0,parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(first_part," ",second_part);

        holder.item.setText(finalText);
    }

    @Override
    public int getItemCount() {
        return recyclerItemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView item;
        private final CheckBox checkBox;
        private final ImageView clickable_pencil;
        private final color textColor;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);
            textColor = itemView.findViewById(R.color.text_color);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle(view);
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit(view);
                }
            });

        }

        private void toggle(View view) {
            view.setBackgroundColor(Color.YELLOW);
        }

        private void edit(View view) {
            view.setBackgroundColor(Color.DKGRAY);
        }
    }
}
