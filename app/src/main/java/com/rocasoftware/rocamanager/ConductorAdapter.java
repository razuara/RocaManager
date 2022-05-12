package com.rocasoftware.rocamanager;

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

public class ConductorAdapter extends FirebaseRecyclerAdapter<ConductorModel,ConductorAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ConductorAdapter(@NonNull FirebaseRecyclerOptions<ConductorModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ConductorModel model) {
        holder.nombre.setText(model.getNombreCompleto());
        holder.cede.setText(model.getCede());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getPerfiImgSrc())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conductor_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView nombre,cede,email;

        public myViewHolder(View itemView)
        {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.perfiImgSrcImage);
            nombre = (TextView) itemView.findViewById(R.id.nombreCompletoTextView);
            cede = (TextView) itemView.findViewById(R.id.cedeTextView);
            email = (TextView) itemView.findViewById(R.id.emailTextView);
        }
    }

}
