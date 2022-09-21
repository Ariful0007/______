package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TranscationMain extends AppCompatActivity {

    private Toolbar mainToolbar;
    private String current_user_id;
    private BottomNavigationView mainBottomNav;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainToggle;
    private NavigationView mainNav;

    FrameLayout frameLayout;
    private TextView drawerName;
    private CircleImageView drawerImage;
    FirebaseAuth firebaseAuth;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestoreSettings settings;
    private DatabaseReference mUserRef;
    HomeFragment homeFragment;
    WorkFragment chatFragment;
    ProfileFragment requestFragment;


    KProgressHUD kProgressHUD;
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();

    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile,username;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    FirebaseFirestore db;

    FirebaseUser firebaseUser;
    String user1;

    IProfile profile;
    TextView nameTv,emailTv;
    ImageView profileImage;
    TextView coinsT1v;
    CardView dailyCheckCard,luckySpinCard,aboutCard1,aboutCard,redeemCard,referCard,taskCard;

    String sessionname,sessionmobile,sessionphoto,sessionemail,sessionusername;
    int count,count1,count2,count3;
    String package_actove;
    String daily_bonus;
    String incomeType="Daily Task";
    int main_account;
    int count12,count123;
    int main_refer;
    String main_task ;
    private ImageSlider imageSlider;


    private TextView tvemail,tvphone;
    private HashMap<String,String> uaser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcation_main);
        imageSlider = findViewById(R.id.slider);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Transcation");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
    }



    public void withdraw(View view) {
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                Intent intent=new Intent(getApplicationContext(),MyPayment.class);
                                intent.putExtra("username",task.getResult().getString("username"));
                                intent.putExtra("number",task.getResult().getString("number"));
                                startActivity(intent);

                            }
                        }
                    }
                });
        // startActivity(new Intent(getApplicationContext(),MyPayment.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }

    public void fund(View view) {
        startActivity(new Intent(getApplicationContext(),AddFund.class));
    }

    public void ree(View view) {
        startActivity(new Intent(getApplicationContext(),Reports.class));
    }

    public void wor111136k(View view) {
        startActivity(new Intent(getApplicationContext(),FundRequest.class));
    }
}