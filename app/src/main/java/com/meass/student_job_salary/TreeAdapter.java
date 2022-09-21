package com.meass.student_job_salary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.myview> {
    public List<Refdferal_model> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Context context;
    RecyclerView recyclerView;
    TreeAdapter getDataAdapter1;
    List<Refdferal_model> getList;
    DocumentReference documentReference;

    public TreeAdapter(List<Refdferal_model> data,Context context) {

        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public TreeAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_layout, parent, false);
        return new TreeAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeAdapter.myview holder, final int position) {
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();
holder.username.setText(data.get(position).getUser_name());
holder.loginid.setText(data.get(position).getUser_name());
firebaseFirestore.collection("User2")
        .document(data.get(position).getUser_name()+"@gmail.com")
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        try {
                            holder.name.setText(task.getResult().getString("name"));
                        }catch (Exception e) {
                            holder.name.setText(task.getResult().getString("name"));
                        }
                    }
                }
            }
        });
        firebaseFirestore.collection("ALL_GET")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    holder.sponsar.setText(task.getResult().getString("refername"));
                                }catch (Exception e) {
                                    holder.sponsar.setText(task.getResult().getString("refername"));
                                }
                            }
                        }
                    }
                });
        firebaseFirestore.collection("Persions")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                               if (ncount<15) {
                                   holder.ranking.setText("No Star");
                            }
                            else if (ncount>=15) {
                                   holder.ranking.setText("One Star");
                            }
                            else  if (ncount>=50) {
                                   holder.ranking.setText("Two Star");
                            }
                            else  if (ncount>=100) {
                                   holder.ranking.setText("Three Star");
                            }
                            else  if (ncount>=150) {
                                   holder.ranking.setText("Four Star");
                            }
                            else  if (ncount>=200) {
                                   holder.ranking.setText("Five Star");
                            }
                            else  if (ncount>=250) {
                                   holder.ranking.setText("Six Star");
                            }
                            else  if (ncount>=300) {
                                   holder.ranking.setText("Seven Star");
                            }
                            else  if (ncount>=250) {
                                   holder.ranking.setText("Eight Star");
                            }
                            else  if (ncount>=400) {
                                   holder.ranking.setText("Nine Star");
                            }
                            else  if (ncount>=450) {
                                   holder.ranking.setText("Ten Star");
                            }
                        }

                    }
                });
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamA")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            holder.teamA.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamB")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            holder.teamB.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamC")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            holder.teamC.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("Send_Status")
                .document(data.get(position).getUser_name()+"@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                holder.logindate.setText(task.getResult().getString("opining"));
                            }
                            else {
                                holder.logindate.setText("Date Not  Detected");
                            }
                        }
                    }
                });
        holder.laymain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Persions") .document(data.get(position).getUser_name()+"@gmail.com")
                        .collection("List")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    int ncount = 0;
                                    for (DocumentSnapshot document : task.getResult()) {
                                        ncount++;
                                    }
                                    if (ncount==0) {
                                        Toasty.error(v.getContext(),"No Referals",Toasty.LENGTH_SHORT,true).show();
                                    }
                                    else {
                                        Intent intent=new Intent(v.getContext(),Tree2Activity.class);
                                        intent.putExtra("email",data.get(position).getUser_name()+"@gmail.com");
                                        v.getContext().startActivity(intent);
                                       // new TreeDialouge(context,data.get(position).getUser_name()+"@gmail.com");
                                    }
                                }
                            }
                        });


            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {

TextView username,loginid,name,ranking,sponsar,teamA,teamB,teamC,logindate;
RelativeLayout laymain;
RelativeLayout rekti;
RecyclerView takdir;
        public myview(@NonNull View itemView) {
            super(itemView);
            ranking=itemView.findViewById(R.id.ranking);
            name=itemView.findViewById(R.id.name);
            loginid=itemView.findViewById(R.id.loginid);
            username=itemView.findViewById(R.id.username);
            sponsar=itemView.findViewById(R.id.sponsar);
            teamA=itemView.findViewById(R.id.teamA);
            teamB=itemView.findViewById(R.id.teamB);
            teamC=itemView.findViewById(R.id.teamC);
            logindate=itemView.findViewById(R.id.logindate);
            laymain=itemView.findViewById(R.id.laymain);
            rekti=itemView.findViewById(R.id.rekti);
            takdir=itemView.findViewById(R.id.takdir);


        }
    }
}
