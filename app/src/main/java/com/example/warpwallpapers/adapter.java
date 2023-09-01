package com.example.warpwallpapers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.viewHolder> {
    private Context context;
    private ArrayList<modal> arr;
//    private Onclick onclick;
    public adapter(Context context, ArrayList<modal> arr) {
        this.context = context;
        this.arr = arr;
//        this.onclick = onclick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaperlayout , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        modal mod = arr.get(position);
        Picasso.get().load(mod.getMediamurl()).into(holder.medium);
        String url = mod.getOrignalurl();
        String photographer = mod.getPhotgrapher();
        String Photgraperid = mod.getPhotgrapher_id();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    intent = new Intent(context, Orignal_image_screen.class);
                }
                intent.putExtra("orignal_url" , url);
                intent.putExtra("initial" , photographer);
                intent.putExtra("id" , Photgraperid);
                context.startActivity(intent);
//                onclick.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView medium;

        public viewHolder(@NonNull View itemView) {

            super(itemView);
            medium = itemView.findViewById(R.id.image_view);
        }
    }
// public interface Onclick{
//        public void onClick(Integer position);
// }
}
