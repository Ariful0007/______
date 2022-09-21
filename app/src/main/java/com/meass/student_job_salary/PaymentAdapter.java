package com.meass.student_job_salary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.myview> {
    public List<PaymentModel> data;
    FirebaseFirestore firebaseFirestore;

    public PaymentAdapter(List<PaymentModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PaymentAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paymentdesign, parent, false);
        return new PaymentAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.myview holder, final int position) {
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText("trxid ID : " + data.get(position).getTaxid());
        holder.customer_area1.setText("Amount : ৳" + data.get(position).getAmmount());
        holder.customer_area11.setText("Fee : ৳" + data.get(position).getFee());
        holder.customer_area111.setText("Total : ৳" + data.get(position).getTotal());
        holder.logout.setText(data.get(position).getDateandtime());
        holder.logout1.setText(data.get(position).getConvertname());
        holder.logout2.setText("৳" + data.get(position).getTotal());
        holder.appnamef.setText(data.get(position).getStatus());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView logout2,appnamef, logout1, customer_name, customer_number, customer_area, logout, customer_area1, customer_area111, customer_area11;
        CardView reay;

        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_number = itemView.findViewById(R.id.customer_number);
            logout = itemView.findViewById(R.id.logout);
            reay = itemView.findViewById(R.id.reay);
            customer_area11 = itemView.findViewById(R.id.customer_area11);
            customer_area111 = itemView.findViewById(R.id.customer_area111);
            logout2 = itemView.findViewById(R.id.logout2);
            logout1 = itemView.findViewById(R.id.logout1);
            appnamef=itemView.findViewById(R.id.appnamef);

            customer_area1 = itemView.findViewById(R.id.customer_area1);

        }
    }
}
