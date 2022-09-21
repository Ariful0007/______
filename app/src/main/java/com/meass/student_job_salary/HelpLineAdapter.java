package com.meass.student_job_salary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HelpLineAdapter  extends RecyclerView.Adapter<HelpLineAdapter.myview> {
    public List<Help_Model> data;
    FirebaseFirestore firebaseFirestore;

    public HelpLineAdapter(List<Help_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public HelpLineAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calling,parent,false);
        return new HelpLineAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpLineAdapter.myview holder, final int position) {
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText(data.get(position).getPhone());
        holder.customer_area.setText(data.get(position).getArea());
        holder.customer_area.setVisibility(View.INVISIBLE);


        holder.logout.setText("Open Link");
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Link Open")
                        .setMessage("Are you want to open this link ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(data.get(position).getPhone()));
                        v.getContext().startActivity(intent);

                    }
                }).create().show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        CardView reay;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
            reay=itemView.findViewById(R.id.reay);
        }
    }
}
