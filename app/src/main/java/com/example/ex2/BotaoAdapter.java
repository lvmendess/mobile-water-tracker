package com.example.ex2;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class BotaoAdapter extends RecyclerView.Adapter<BotaoAdapter.BotaoViewHolder> {
    private int itemCount;
    private OnItemClickListener listener;
    private boolean[] state;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public BotaoAdapter(int itemCount, OnItemClickListener listener) {
        this.itemCount = itemCount;
        this.listener = listener;
        this.state = new boolean[itemCount];
    }

    @NonNull
    @Override
    public BotaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.botao, parent, false);
        return new BotaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BotaoViewHolder holder, int position) {
        if (state[position]) {
            holder.button.setBackgroundColor(Color.GRAY);
        } else {
            holder.button.setBackgroundResource(R.drawable.copo);
        }
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public void setClicked(int position, boolean clicked) {
        state[position] = clicked;
        notifyItemChanged(position);
    }

    public int getClickedCount() {
        int count = 0;
        for (boolean clicked : state) {
            if (clicked) count++;
        }
        return count;
    }

    public void resetAll() {
        Arrays.fill(state, false);
        notifyDataSetChanged();
    }

    class BotaoViewHolder extends RecyclerView.ViewHolder {
        Button button;

        BotaoViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.btnGlass);

            button.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    state[position] = !state[position];
                    notifyItemChanged(position);
                    listener.onItemClick(position);
                }
            });
        }
    }
}
