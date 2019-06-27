package com.example.hp.pallysteve;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    Context context;
    ArrayList<String> locationList = new ArrayList<>();
    ArrayList<String> companyList = new ArrayList<>();
    ArrayList<String> salaryList = new ArrayList<>();
    ArrayList<String> roleList = new ArrayList<>();

    public CustomAdapter(Context context, ArrayList<String> companyList,
                         ArrayList<String> locationList, ArrayList<String> salaryList,
                         ArrayList<String> roleList) {
        this.context = context;
        this.locationList = locationList;
        this.companyList = companyList;
        this.salaryList = salaryList;
        this.roleList = roleList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_layout, viewGroup, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.location.setText(locationList.get(i));
        customViewHolder.companyName.setText(companyList.get(i));
        customViewHolder.salary.setText(salaryList.get(i));
        customViewHolder.role.setText(roleList.get(i));
        Glide.with(context).load(R.drawable.ic_menu_gallery).into(customViewHolder.companyLogo);
        customViewHolder.detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView companyName;
        TextView location;
        TextView salary;
        TextView role;
        ImageView companyLogo;
        TextView detailsBtn;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            companyLogo = itemView.findViewById(R.id.company_logo);
            companyName = itemView.findViewById(R.id.company_name_txt);
            location = itemView.findViewById(R.id.location_txt);
            salary = itemView.findViewById(R.id.salary_txt);
            role = itemView.findViewById(R.id.role_txt);
            detailsBtn = itemView.findViewById(R.id.details_btn);

        }
    }
}
