package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MyPayment extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerTextSize, spinnerTextSize1, spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    EditText spinner1,transcationpin;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView textammount,showing;
    Button cirLoginButton;
    KProgressHUD kProgressHUD;
    Spinner methode;String check;
    String username,number;
    DocumentReference documentReference;
    List<String> names;
    ArrayAdapter adapter;
    EditText number1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Withdraw");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("User2")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                username=task.getResult().getString("username");
                            }
                        }
                    }
                });
        number1=findViewById(R.id.number);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        spinner1=findViewById(R.id.spinner1);
        textammount=findViewById(R.id.textammount);
        transcationpin=findViewById(R.id.transcationpin);
        cirLoginButton=findViewById(R.id.cirLoginButton);
        showing=findViewById(R.id.showing);
        kProgressHUD=KProgressHUD.create(MyPayment.this);
        spinner1.addTextChangedListener(nameWatcher);
       // spinner1.addTextChangedListener(nameWatcher);
        spinnerTextSize=findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener(this);
        //
        spinnerTextSize1=findViewById(R.id.agents);
        spinnerTextSize1.setOnItemSelectedListener(this);
        String[] textSizes1 = getResources().getStringArray(R.array.agentr);
        ArrayAdapter adapter1 = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinnerTextSize1.setAdapter(adapter1);
        String[] textSizes = getResources().getStringArray(R.array.payment);
        names=new ArrayList<>();


        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);

        String[] textSizes111 = getResources().getStringArray(R.array.agentr);
        ArrayAdapter adapter11 = new ArrayAdapter(MyPayment.this,
                R.layout.spinner_row, textSizes111);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize1.setAdapter(adapter11);

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
                                try {
                                    String balance=task.getResult().getString("self_income");
                                    double main_=Double.parseDouble(balance);
                                    if (main_==0) {
                                        cirLoginButton.setEnabled(false);

                                    }
                                    else {
                                        cirLoginButton.setEnabled(true);
                                    }
                                    showing.setText("Income Balance : "+task.getResult().getString("self_income"));
                                }catch (Exception e) {
                                    String balance=task.getResult().getString("self_income");
                                    double main_=Double.parseDouble(balance);
                                    if (main_==0) {
                                        cirLoginButton.setEnabled(false);

                                    }
                                    else {
                                        cirLoginButton.setEnabled(true);
                                    }
                                    showing.setText("Income Balance : "+task.getResult().getString("self_income"));
                                }
                            }
                            else {

                                showing.setText("Income Balance : 0");
                            }
                        }
                        else {

                            showing.setText("Income Balance : 0");
                        }
                    }
                });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH)+1;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        firebaseFirestore.collection("Payment_date_Detector_EachDay")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection(""+month)
                .document(""+day)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                cirLoginButton.setEnabled(false);
                                new AestheticDialog.Builder(MyPayment.this, DialogStyle.FLASH, DialogType.WARNING)
                                        .setTitle("Warning")
                                        .setMessage("You can not give withdraw in today.")
                                        .setAnimation(DialogAnimation.SPLIT)
                                        .setOnClickListener(new OnDialogClickListener() {
                                            @Override
                                            public void onClick(AestheticDialog.Builder builder) {
                                                builder.dismiss();
                                                startActivity(new Intent(getApplicationContext(),TranscationMain.class));

                                            }
                                        }).show();
                                //Toasty.error(getApplicationContext(),"You  can not give any payment request today.",Toasty.LENGTH_SHORT,true).show();

                            }
                            else {
                                cirLoginButton.setEnabled(true);
                            }
                        }
                    }
                });

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount=spinner1.getText().toString().toLowerCase();
                String pin=transcationpin.getText().toString().toLowerCase();
                String pay_number=number1.getText().toString();
                if (TextUtils.isEmpty(pay_number)||TextUtils.isEmpty(ammount)||TextUtils.isEmpty(pin)||TextUtils.isEmpty(valueFromSpinner1)
                ) {
                    Toasty.error(getApplicationContext(), "Error give right information.", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                else {
                    if (Double.parseDouble(ammount)<100) {
                        Toasty.info(getApplicationContext(), "Minimum withdraw request is 100.", Toast.LENGTH_SHORT, true).show();
                        return;
                    }
                    else{
                        firebaseFirestore.collection("Payment__Admin_Checker")
                                .document("abc@gmail.com")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                String get=task.getResult().getString("get");
                                               // Toast.makeText(MyPayment.this, ""+get, Toast.LENGTH_SHORT).show();
                                                if (get.equals("1")) {
                                                    new AestheticDialog.Builder(MyPayment.this, DialogStyle.FLASH, DialogType.WARNING)
                                                            .setTitle("Warning")
                                                            .setMessage("Admin Close Payment Request")
                                                            .setAnimation(DialogAnimation.SPLIT)
                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                @Override
                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                    builder.dismiss();
                                                                    startActivity(new Intent(getApplicationContext(),TranscationMain.class));

                                                                }
                                                            }).show();
                                                }
                                                else {
                                                    progress_check();
                                                    firebaseFirestore.collection("Password")
                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                            .get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        if (task.getResult().exists()) {

                                                                            try {
                                                                                String pass=task.getResult().getString("uuid");
                                                                                if (pass.toLowerCase().toString().equals(pin)) {
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
                                                                                                            String balance=task.getResult().getString("self_income");
                                                                                                            String cashwalet=task.getResult().getString("cashwalet");
                                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                                            String giving_balance=task.getResult().getString("giving_balance");
                                                                                                            String ammount1=ammount;
                                                                                                            double  fee=Double.parseDouble(ammount1)*5/100;
                                                                                                            double  total=Double.parseDouble(check)-fee;
                                                                                                            double secon=Double.parseDouble(balance)-Double.parseDouble(check);
                                                                                                            double subtotal=Double.parseDouble(giving_balance)+Double.parseDouble(ammount1);
                                                                                                            double subcashwalet=Double.parseDouble(cashwalet)+Double.parseDouble(ammount1);
                                                                                                            if (Double.parseDouble(check)>Double.parseDouble(balance)) {
                                                                                                                kProgressHUD.dismiss();
                                                                                                                Toasty.error(getApplicationContext(), "You have not much money.", Toast.LENGTH_SHORT, true).show();
                                                                                                            }
                                                                                                            else {
                                                                                                                firebaseFirestore.collection("Users")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                        .collection("Main_Balance")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                        .update("self_income",""+secon,"cashwalet",
                                                                                                                                ""+subcashwalet)
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                    String ts = tsLong.toString();
                                                                                                                                    Random r = new Random();
                                                                                                                                    int randomNumber = r.nextInt(1002563985);
                                                                                                                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                                                                                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                                                                                                                    Date date = new Date();
                                                                                                                                    Date date1 = new Date();
                                                                                                                                    String s=dateFormat.format(date);
                                                                                                                                    String s1=dateFormat1.format(date1);
                                                                                                                                    String uuid= UUID.randomUUID().toString();
                                                                                                                                    String randomid="TRK"+randomNumber;
                                                                                                                                    PaymentModel converMyBalance=new PaymentModel(
                                                                                                                                            "Withdraw ( Using "+valueFromSpinner1+" Methode)",ts,s,randomid,"Withdraw From Income Balance",""+ammount,
                                                                                                                                            ""+fee,""+total,"Pending",uuid,firebaseAuth.getCurrentUser().getEmail(),valueFromSpinner1,valueFromSpinner1,username,pay_number,s1
                                                                                                                                    );
                                                                                                                                    Map<String, String> userMap1 = new HashMap<>();

                                                                                                                                    userMap1.put("counter", firebaseAuth.getCurrentUser().getEmail());
                                                                                                                                    Calendar calendar = Calendar.getInstance();
                                                                                                                                    final int year = calendar.get(Calendar.YEAR);
                                                                                                                                    final int month = calendar.get(Calendar.MONTH)+1;
                                                                                                                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                                    firebaseFirestore.collection("Payment_date_Detector_EachDay")
                                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                            .collection(""+month)
                                                                                                                                            .document(""+day)
                                                                                                                                            .set(userMap1)
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                    firebaseFirestore.collection("My_Withdraw")
                                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                            .collection("List")
                                                                                                                                            .document(uuid)
                                                                                                                                            .set(converMyBalance)
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                        firebaseFirestore.collection("Admin_paymentRequest")
                                                                                                                                                                .document(uuid)
                                                                                                                                                                .set(converMyBalance)
                                                                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                                                            kProgressHUD.dismiss();
                                                                                                                                                                            new AestheticDialog.Builder(MyPayment.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                    .setTitle("Conformation")
                                                                                                                                                                                    .setMessage("Withdraw request is Successfully Done in Student Job.")
                                                                                                                                                                                    .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                    .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                            builder.dismiss();
                                                                                                                                                                                            startActivity(new Intent(getApplicationContext(),TranscationMain.class));

                                                                                                                                                                                        }
                                                                                                                                                                                    }).show();
                                                                                                                                                                            //Toasty.success(getApplicationContext(), "Withdraw request  is done.", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                });

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                            }


                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                }
                                                                                else {
                                                                                    kProgressHUD.dismiss();
                                                                                    Toasty.error(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT, true).show();
                                                                                }
                                                                            }catch (Exception e) {
                                                                                String pass=task.getResult().getString("password");
                                                                                if (pass.toLowerCase().toString().equals(pin)) {
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
                                                                                                            String balance=task.getResult().getString("self_income");
                                                                                                            String cashwalet=task.getResult().getString("cashwalet");
                                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                                            String giving_balance=task.getResult().getString("giving_balance");
                                                                                                            String ammount1=ammount;
                                                                                                            double  fee=Double.parseDouble(ammount1)*5/100;
                                                                                                            double  total=Double.parseDouble(check)-fee;
                                                                                                            double secon=Double.parseDouble(balance)-Double.parseDouble(check);
                                                                                                            double subtotal=Double.parseDouble(giving_balance)+Double.parseDouble(ammount1);
                                                                                                            double subcashwalet=Double.parseDouble(cashwalet)+Double.parseDouble(ammount1);
                                                                                                            if (Double.parseDouble(check)>Double.parseDouble(balance)) {
                                                                                                                kProgressHUD.dismiss();
                                                                                                                Toasty.error(getApplicationContext(), "You have not much money.", Toast.LENGTH_SHORT, true).show();
                                                                                                            }
                                                                                                            else {
                                                                                                                firebaseFirestore.collection("Users")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                        .collection("Main_Balance")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                        .update("self_income",""+secon,"cashwalet",
                                                                                                                                ""+subcashwalet)
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                    Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                    String ts = tsLong.toString();
                                                                                                                                    Random r = new Random();
                                                                                                                                    int randomNumber = r.nextInt(1002563985);
                                                                                                                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                                                                                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                                                                                                                                    Date date = new Date();
                                                                                                                                    Date date1 = new Date();
                                                                                                                                    String s=dateFormat.format(date);
                                                                                                                                    String s1=dateFormat1.format(date1);
                                                                                                                                    String uuid= UUID.randomUUID().toString();
                                                                                                                                    String randomid="TRK"+randomNumber;
                                                                                                                                    PaymentModel converMyBalance=new PaymentModel(
                                                                                                                                            "Withdraw ( Using "+valueFromSpinner1+" Methode)",ts,s,randomid,"Withdraw From Income Balance",""+ammount,
                                                                                                                                            ""+fee,""+total,"Pending",uuid,firebaseAuth.getCurrentUser().getEmail(),valueFromSpinner1,valueFromSpinner2,username,pay_number,s1
                                                                                                                                    );
                                                                                                                                    Map<String, String> userMap1 = new HashMap<>();

                                                                                                                                    userMap1.put("counter", firebaseAuth.getCurrentUser().getEmail());
                                                                                                                                    Calendar calendar = Calendar.getInstance();
                                                                                                                                    final int year = calendar.get(Calendar.YEAR);
                                                                                                                                    final int month = calendar.get(Calendar.MONTH)+1;
                                                                                                                                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                                    firebaseFirestore.collection("Payment_date_Detector_EachDay")
                                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                            .collection(""+month)
                                                                                                                                            .document(""+day)
                                                                                                                                            .set(userMap1)
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                    firebaseFirestore.collection("My_Withdraw")
                                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                            .collection("List")
                                                                                                                                            .document(uuid)
                                                                                                                                            .set(converMyBalance)
                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                        firebaseFirestore.collection("AllPaymentRequest")
                                                                                                                                                                .document(uuid)
                                                                                                                                                                .set(converMyBalance)
                                                                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                                                            kProgressHUD.dismiss();
                                                                                                                                                                            new AestheticDialog.Builder(MyPayment.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                    .setTitle("Conformation")
                                                                                                                                                                                    .setMessage("Withdraw request is Successfully Done in Student Job.")
                                                                                                                                                                                    .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                    .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                        @Override
                                                                                                                                                                                        public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                            builder.dismiss();
                                                                                                                                                                                            startActivity(new Intent(getApplicationContext(),TranscationMain.class));

                                                                                                                                                                                        }
                                                                                                                                                                                    }).show();
                                                                                                                                                                            // Toasty.success(getApplicationContext(), "Withdraw request  is done.", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                });

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            });
                                                                                                                                }
                                                                                                                            }
                                                                                                                        });
                                                                                                            }


                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                }
                                                                                else {
                                                                                    kProgressHUD.dismiss();
                                                                                    Toasty.error(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT, true).show();
                                                                                }

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }
                                            }
                                        }
                                    }
                                });

                    }

                }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId()==R.id.spinnerTextSize) {
            valueFromSpinner1=parent.getItemAtPosition(position).toString();


        }
         if (parent.getId()==R.id.agents) {
             valueFromSpinner2=parent.getItemAtPosition(position).toString();
             //Toast.makeText(this, ""+valueFromSpinner2, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

           try {
               check = s.toString();
               String ammount=check;
               double fee=Double.parseDouble(ammount)*5/100;
               double total=Double.parseDouble(check)-fee;


               textammount.setVisibility(View.VISIBLE);
               textammount.setText("Amount : "+check+", Fee : "+fee+", Total : "+total);
           }catch (Exception e) {
               check = s.toString();
               String ammount=check;
               double fee=Double.parseDouble(ammount)*5/100;
               double total=Double.parseDouble(check)-fee;


               textammount.setVisibility(View.VISIBLE);
               textammount.setText("Amount : "+check+", Fee : "+fee+", Total : "+total);
           }
        }

    };
    private void progress_check() {
        kProgressHUD = KProgressHUD.create(MyPayment.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Checking Data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
}