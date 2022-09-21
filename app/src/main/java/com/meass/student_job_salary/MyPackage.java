package com.meass.student_job_salary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyPackage extends RecyclerView.Adapter<MyPackage.myview> {
    public List<Package> data;
    FirebaseFirestore firebaseFirestore;

    public MyPackage(List<Package> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyPackage.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_pacvkage,parent,false);
        return new MyPackage.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPackage.myview holder, final int position) {

holder.customer_name.setText(""+data.get(position).getPackage_name()+"\n"
        +"Request Amount : "+data.get(position).getPackage_price()+"\n"+"Request Date : "+data.get(position).getDate()+"\n" +
        ""+"Status : "+data.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.my_details);

        }
    }
}

