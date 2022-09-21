package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class AddFund extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerTextSize, spinnerTextSize1, spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    EditText spinner1,transcationpin,g_numbers,numbers;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView textammount,showing;
    Button cirLoginButton;
    KProgressHUD kProgressHUD;
    Spinner methode;String check;
    String cus_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fund);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Deposit Request");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        spinnerTextSize=findViewById(R.id.spinnerTextSize);
        spinner1=findViewById(R.id.spinner1);
        kProgressHUD=KProgressHUD.create(AddFund.this);
        spinnerTextSize.setOnItemSelectedListener(this);
        String[] textSizes1 = getResources().getStringArray(R.array.agenda);
        ArrayAdapter adapter1 = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter1);
        g_numbers=findViewById(R.id.g_numbers);
        transcationpin=findViewById(R.id.transcationpin);
        numbers=findViewById(R.id.numbers);
        g_numbers.setEnabled(false);
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
                                cus_name=task.getResult().getString("username");
                            }
                        }
                    }
                });

        Button cirLoginButton=findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ammount=spinner1.getText().toString().toLowerCase();
                String gateway=valueFromSpinner;
                String gatwaysnumber=g_numbers.getText().toString().toLowerCase();
                String transca=transcationpin.getText().toString();
                String numberr=numbers.getText().toString();
                if (TextUtils.isEmpty(ammount)||TextUtils.isEmpty(gateway)
                        ||TextUtils.isEmpty(gatwaysnumber)||TextUtils.isEmpty(transca)||TextUtils.isEmpty(numberr)) {
                    Toasty.error(getApplicationContext(), "Error give right information.", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                else {
                    String uuid= UUID.randomUUID().toString();
                    progress_check();
                    Calendar calendar = Calendar.getInstance();
                    String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                    Long tsLong = System.currentTimeMillis()/1000;
                    String ts = tsLong.toString();
                    Package package1=new Package(firebaseAuth.getCurrentUser().getEmail(),"Deposit Request",ammount,valueFromSpinner,
                            numberr,transca,uuid,firebaseAuth.getCurrentUser().getUid(),"Pending",cus_name,current1,ts);
                    firebaseFirestore.collection("Subadmin")
                            .document("Packages").collection("101")
                            .document(uuid)
                            .set(package1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseFirestore.collection("MyPackage")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .collection("List")
                                                .document(uuid)
                                                .set(package1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            kProgressHUD.dismiss();
                                                            new AestheticDialog.Builder(AddFund.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                    .setTitle("Conformation")
                                                                    .setMessage("Deposit request is successfully done.")
                                                                    .setAnimation(DialogAnimation.SPLIT)
                                                                    .setOnClickListener(new OnDialogClickListener() {
                                                                        @Override
                                                                        public void onClick(AestheticDialog.Builder builder) {
                                                                            builder.dismiss();
                                                                            startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                        }
                                                                    }).show();
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
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),TranscationMain.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),TranscationMain.class));
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spinnerTextSize) {
            valueFromSpinner = adapterView.getItemAtPosition(i).toString();
            // Toast.makeText(this, ""+valueFromSpinner, Toast.LENGTH_SHORT).show();
            if (valueFromSpinner.toString().toLowerCase().equals("bkash")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("1")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            g_numbers.setText(task.getResult().getString("number"));
                                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("copyed", task.getResult().getString("number"));
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(getApplicationContext(), "Number Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e) {
                                            g_numbers.setText(task.getResult().getString("number"));
                                        }
                                    }
                                    else {
                                        // Toast.makeText(AddFund.this, "1", Toast.LENGTH_SHORT).show();
                                        g_numbers.setText("Call Admin");
                                    }
                                }
                                else {
                                    g_numbers.setText("Call Admin");
                                }
                            }
                        });
            }
            else    if (valueFromSpinner.contains("Rocket")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("2")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            g_numbers.setText(task.getResult().getString("number"));
                                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("copyed", task.getResult().getString("number"));
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(getApplicationContext(), "Number Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e) {
                                            g_numbers.setText(task.getResult().getString("number"));
                                        }
                                    }
                                    else {
                                        g_numbers.setText("Call Admin");
                                    }
                                }
                                else {
                                    g_numbers.setText("Call Admin");
                                }
                            }
                        });
            }
            else    if (valueFromSpinner.contains("Nagad")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("3")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            g_numbers.setText(task.getResult().getString("number"));
                                            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                            ClipData clip = ClipData.newPlainText("copyed", task.getResult().getString("number"));
                                            clipboard.setPrimaryClip(clip);
                                            Toast.makeText(getApplicationContext(), "Number Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e) {
                                            g_numbers.setText(task.getResult().getString("number"));
                                        }
                                    }
                                    else {
                                        g_numbers.setText("Call Admin");
                                    }
                                }
                                else {
                                    g_numbers.setText("Call Admin");
                                }
                            }
                        });
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void progress_check() {
        kProgressHUD = KProgressHUD.create(AddFund.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Uploading Data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }

    public void gatww(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copyed", g_numbers.getText().toString().toLowerCase().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Number Copied", Toast.LENGTH_SHORT).show();
    }
}