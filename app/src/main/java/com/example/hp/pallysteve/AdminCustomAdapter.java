package com.example.hp.pallysteve;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AdminCustomAdapter extends RecyclerView.Adapter<AdminCustomAdapter.AdminViewHolder> {
    Context context;
    ArrayList<String> AdminCompanyList = new ArrayList<String>();

    public AdminCustomAdapter(){

    }

    public AdminCustomAdapter(Context context, ArrayList<String> AdminCompanyList){
        this.context = context;
        this.AdminCompanyList = AdminCompanyList;
    }

    public AdminCustomAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_custom_layout, viewGroup, false);
        AdminViewHolder adminViewHolder = new AdminViewHolder(view);
        return adminViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder adminViewHolder, int i) {
        adminViewHolder.jobItem.setText(AdminCompanyList.get(i));
        Glide.with(context).load(R.drawable.ic_work_black_24dp).into(adminViewHolder.adminCompanyLogo);
    }

    @Override
    public int getItemCount() {
        return AdminCompanyList.size();
    }



    public class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView jobItem;
        ImageView adminCompanyLogo;
        ImageView adminOptionBtn;

        public AdminViewHolder(@NonNull final View itemView) {
            super(itemView);
            jobItem = itemView.findViewById(R.id.admin_job_item);
            adminCompanyLogo = itemView.findViewById(R.id.admin_company_logo);
            adminOptionBtn = itemView.findViewById(R.id.admin_option_btn);

            adminOptionBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                        @Override
                        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                            MenuInflater menuInflater = new MenuInflater(context);
                            menuInflater.inflate(R.menu.job_menu, contextMenu);
                        }

                    });

                }

            });


        }





    }


}
