package com.meass.student_job_salary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RefferaLAdadpter extends RecyclerView.Adapter<RefferaLAdadpter.myview> {
    public List<Refdferal_model> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth  firebaseAuth;

    public RefferaLAdadpter(List<Refdferal_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RefferaLAdadpter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subadmin, parent, false);
        return new RefferaLAdadpter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefferaLAdadpter.myview holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();


        firebaseFirestore.collection("User2")
                .document( data.get(position).getUser_email())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    holder.customer_name.setText("Username : " +task.getResult().getString("username"));
                                    holder.customer_number.setText("Email  : " + task.getResult().getString("email"));
                                }
                                catch (Exception e) {
                                    holder.customer_name.setText("Username : " +task.getResult().getString("username"));
                                    holder.customer_number.setText("Email  : " + task.getResult().getString("email"));
                                }
                            }
                        }
                    }
                });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView customer_name, customer_number, customer_area, logout;

        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_number = itemView.findViewById(R.id.customer_number);
            customer_area = itemView.findViewById(R.id.customer_area);
            logout = itemView.findViewById(R.id.logout);
        }
    }
}