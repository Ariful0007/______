package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class Tree2Activity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    TreeAdapter getDataAdapter1;
    List<Refdferal_model> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    TextView username,loginid,name,ranking,sponsar,teamA,teamB,teamC,logindate;
    String names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree2);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Sales Team");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        try {
            names=getIntent().getStringExtra("email");
        }catch (Exception e) {
            names=getIntent().getStringExtra("email");
        }
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
                .document(names)
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
                .document(names)
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
                .document(names)
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
        firebaseFirestore.collection("My_Tree")
                .document("Tream")
                .collection("TeamA")
                .document(names)
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
                .document(names)
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
                .document(names)
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
                .document(names)
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
        getDataAdapter1 = new TreeAdapter(getList,Tree2Activity.this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("MyRefer") .document(names)
                .collection("List").document();
        recyclerView = findViewById(R.id.takdir);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Tree2Activity.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }

    private void reciveData() {

        firebaseFirestore.collection("MyRefer") .document(names)
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.dmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.app_bar_search) {
            startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),TreeSettings.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),TreeSettings.class));
        return true;
    }
}