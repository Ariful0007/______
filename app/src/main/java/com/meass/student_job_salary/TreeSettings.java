package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class TreeSettings extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    RefferaLAdadpter getDataAdapter1;
    List<Refdferal_model> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    TextView username,loginid,name,ranking,sponsar,teamA,teamB,teamC,logindate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Team");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        ranking=findViewById(R.id.ranking);
        name=findViewById(R.id.name);
        loginid=findViewById(R.id.loginid);
        username=findViewById(R.id.username);
        sponsar=findViewById(R.id.sponsar);
        teamA=findViewById(R.id.teamA);
        teamB=findViewById(R.id.teamB);
        teamC=findViewById(R.id.teamC);
        logindate=findViewById(R.id.logindate);
        firebaseFirestore.collection("User2")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    name.setText(task.getResult().getString("name"));
                                    loginid.setText(task.getResult().getString("username"));
                                }catch (Exception e) {
                                    name.setText(task.getResult().getString("name"));
                                    loginid.setText(task.getResult().getString("username"));
                                }
                            }
                        }
                    }
                });
        firebaseFirestore.collection("ALL_GET")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    sponsar.setText(task.getResult().getString("refername"));
                                }catch (Exception e) {
                                    sponsar.setText(task.getResult().getString("refername"));
                                }
                            }
                        }
                    }
                });
        firebaseFirestore.collection("Persions")
                .document(firebaseAuth.getCurrentUser().getEmail())
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
                                ranking.setText("No Star");
                            }
                            else if (ncount>=15) {
                                ranking.setText("One Star");
                            }
                            else  if (ncount>=50) {
                                ranking.setText("Two Star");
                            }
                            else  if (ncount>=100) {
                                ranking.setText("Three Star");
                            }
                            else  if (ncount>=150) {
                                ranking.setText("Four Star");
                            }
                            else  if (ncount>=200) {
                                ranking.setText("Five Star");
                            }
                            else  if (ncount>=250) {
                                ranking.setText("Six Star");
                            }
                            else  if (ncount>=300) {
                                ranking.setText("Seven Star");
                            }
                            else  if (ncount>=250) {
                                ranking.setText("Eight Star");
                            }
                            else  if (ncount>=400) {
                                ranking.setText("Nine Star");
                            }
                            else  if (ncount>=450) {
                                ranking.setText("Ten Star");
                            }
                        }

                    }
                });
        firebaseFirestore.collection("Persions")
                .document(firebaseAuth.getCurrentUser().getEmail())
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
                            teamA.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamB")
                .document(firebaseAuth.getCurrentUser().getEmail())
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
                            teamB.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamC")
                .document(firebaseAuth.getCurrentUser().getEmail())
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
                            teamC.setText(""+ncount);
                        }
                    }
                });
        firebaseFirestore.collection("Send_Status")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                logindate.setText(task.getResult().getString("opining"));
                            }
                            else {
                                logindate.setText("Date Not  Detected");
                            }
                        }
                    }
                });
        getList = new ArrayList<>();
        getDataAdapter1 = new RefferaLAdadpter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("Persions").document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List").document();
        recyclerView = findViewById(R.id.takdir);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TreeSettings.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }

    private void reciveData() {

        firebaseFirestore.collection("Persions").document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Refdferal_model get = ds.getDocument().toObject(Refdferal_model.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });

    }






    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
        return true;
    }
}