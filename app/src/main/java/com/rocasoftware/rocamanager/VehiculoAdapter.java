package com.rocasoftware.rocamanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class VehiculoAdapter extends FirebaseRecyclerAdapter<VehiculoModel,VehiculoAdapter.myViewHolder> {

    public VehiculoAdapter(@NonNull FirebaseRecyclerOptions<VehiculoModel> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull VehiculoModel model) {
        holder.marca.setText(model.getMarca());
        holder.tipo.setText(model.getTipo());
        holder.color.setText(model.getColor());

        Glide.with(holder.vehiculoImgSrc.getContext())
                .load(model.getVehiculoImgSrc())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.vehiculoImgSrc);



    }
    @NonNull
    @Override
    public VehiculoAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehiculo_item,parent,false);
        return new VehiculoAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView vehiculoImgSrc;
        TextView marca,tipo,color;

        public myViewHolder(View itemView)
        {
            super(itemView);
            vehiculoImgSrc = (CircleImageView) itemView.findViewById(R.id.vehiculoImgSrcImage);
            marca = (TextView) itemView.findViewById(R.id.marcaTextView);
            tipo = (TextView) itemView.findViewById(R.id.tipoTextView);
            color = (TextView) itemView.findViewById(R.id.colorTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    String idVehiculo = getRef(position).getKey();
                    Intent intent = new Intent(itemView.getContext(), VehiculoEditarActivity.class);
                    intent.putExtra("idVehiculo",idVehiculo);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
