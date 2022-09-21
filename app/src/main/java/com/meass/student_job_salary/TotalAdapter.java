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

public class TotalAdapter extends RecyclerView.Adapter<TotalAdapter.myview> {
    public List<Income> data;
    FirebaseFirestore firebaseFirestore;

    public TotalAdapter(List<Income> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TotalAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income, parent, false);
        return new TotalAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TotalAdapter.myview holder, final int position) {
        holder.customer_name.setText("All Income");
        holder.customer_number.setText("trxid ID : " + data.get(position).getTrixid());
        holder.customer_area1.setText("Fee : ৳" + data.get(position).getFee());
        holder.logout.setText(data.get(position).getDate());
        holder.logout1.setText(data.get(position).getType());
        holder.logout2.setText("৳" + data.get(position).getPayment());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView logout2, logout1, customer_name, customer_number, customer_area, logout, customer_area1, customer_area111, customer_area11;
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

            customer_area1 = itemView.findViewById(R.id.customer_area1);

        }
    }
}
