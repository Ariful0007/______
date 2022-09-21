package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;

public class Reports extends AppCompatActivity {

    EditText spinner1,transcationpin;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView textammount,showing;
    Button cirLoginButton;
    KProgressHUD kProgressHUD;
    String check;
    TextView bonus,refer,gb;
    TextView mainbalancetransfer,allwithdraw,convertmybalance,workincome,totall,referbonus,genbonus,
            shopinging,fundRecevice,leadership;

    String username;

    TextView button1,match,monthly,royal;
    private int SECTOR = 100; // Default value for one pdf file.
    private int START;
    private int END = SECTOR;
    private int NO_OF_PDF_FILE = 1;
    private int NO_OF_FILE;
    private int LIST_SIZE;
    private boolean IS_MANY_PDF_FILE;
    private ProgressDialog progressDialog;
    // for storing our images
    Bitmap bmp, scaledbmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Reports");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        allwithdraw=findViewById(R.id.allwithdraw);
        convertmybalance=findViewById(R.id.convertmybalance);
        totall=findViewById(R.id.totall);
        referbonus=findViewById(R.id.referbonus);
        genbonus=findViewById(R.id.genbonus);
        fundRecevice=findViewById(R.id.fundRecevice);
        shopinging=findViewById(R.id.shopinging);
        leadership=findViewById(R.id.leadership);
        TextView formonth=findViewById(R.id.formonth);
        Calendar calendar = Calendar.getInstance();
        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
        formonth.setText("For The Month Of "+current1);

        mainbalancetransfer=findViewById(R.id.mainbalancetransfer);
        workincome=findViewById(R.id.workincome);
        TextView name=findViewById(R.id.name);
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    name.setText(task.getResult().getString("name"));
                                }catch (Exception e) {
                                    name.setText(task.getResult().getString("name"));
                                }
                            }
                        }
                    }
                });

        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Main_Balance")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try { totall.setText("0");
                                    fundRecevice.setText(task.getResult().getString("monthly_income"));
                                    mainbalancetransfer.setText(task.getResult().getString("third_level"));
                                    allwithdraw.setText(task.getResult().getString("cashwalet"));
                                    convertmybalance.setText(task.getResult().getString("giving_balance"));
                                    workincome.setText(task.getResult().getString("daily_income"));
                                    // totall.setText(task.getResult().getString("main_balance"));
                                    referbonus.setText(task.getResult().getString("first_level"));
                                    genbonus.setText(task.getResult().getString("gen_bonus"));
                                    shopinging.setText(task.getResult().getString("shoping_balance"));
                                    leadership.setText(task.getResult().getString("leader_club"));
                                }catch (Exception e) {
                                    mainbalancetransfer.setText(task.getResult().getString("third_level"));
                                    allwithdraw.setText(task.getResult().getString("cashwalet"));
                                    convertmybalance.setText(task.getResult().getString("giving_balance"));
                                    workincome.setText(task.getResult().getString("daily_income"));
                                    // totall.setText(task.getResult().getString("main_balance"));
                                    referbonus.setText(task.getResult().getString("first_level"));
                                    genbonus.setText(task.getResult().getString("gen_bonus"));
                                    totall.setText("0");
                                    fundRecevice.setText(task.getResult().getString("monthly_income"));
                                    shopinging.setText(task.getResult().getString("shoping_balance"));
                                    leadership.setText(task.getResult().getString("leader_club"));

                                }

                            }
                            else {
                                mainbalancetransfer.setText("0");
                                allwithdraw.setText("0");
                                convertmybalance.setText("0");
                                workincome.setText("0");
                                totall.setText("0");
                                referbonus.setText("0");
                                genbonus.setText("0");
                                fundRecevice.setText("0");
                                shopinging.setText("0");
                                leadership.setText("0");
                            }

                        }
                        else {
                            shopinging.setText("0");
                            totall.setText("0");
                            mainbalancetransfer.setText("0");
                            allwithdraw.setText("0");
                            convertmybalance.setText("0");
                            workincome.setText("0");
                            referbonus.setText("0");
                            genbonus.setText("0");
                            fundRecevice.setText("0");
                            leadership.setText("0");
                        }

                    }
                });
        totall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),TotalHistory.class));
            }
        });
        leadership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LeaderShipHistory.class));
            }
        });

        fundRecevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FundHistory.class));
            }
        });
        genbonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GenHistory.class));
            }
        });
        referbonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReferHistory.class));
            }
        });

        allwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PaymentList.class));
            }
        });

        workincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),IncomeHistoryActivity.class));
            }
        });

    }
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),TranscationMain.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),TranscationMain.class));
        return true;
    }
}