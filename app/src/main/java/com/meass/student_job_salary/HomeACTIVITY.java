package com.meass.student_job_salary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.squareup.picasso.Picasso;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeACTIVITY extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar mainToolbar;
    private String current_user_id;
    private BottomNavigationView mainBottomNav;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainToggle;
    private NavigationView mainNav;

    FrameLayout frameLayout;
    private TextView drawerName,appname;
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
    CircleImageView profileImage;
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
    FloatingActionButton dialogClose;

    ; Dialog mDialog;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        session = new UserSession(getApplicationContext());
        getValues();
        profileImage=findViewById(R.id.profileImage);
        appname=findViewById(R.id.appname);
       //Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
        dialogClose=findViewById(R.id.dialogClose);


       // Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


       // imageSlider = findViewById(R.id.slider);

        Log.d("xlr8_wlv", String.valueOf(session.getWishlistValue()));
        //retrieve session values and display on listviews

        mainBottomNav = findViewById(R.id.mainBottomNav);
        //BottomNavigationViewHelper.disableShiftMode(mainBottomNav);
        homeFragment=new HomeFragment();
        chatFragment=new WorkFragment();
        requestFragment=new ProfileFragment();

        //inflateImageSlider();
        initializeFragment();

        //Toast.makeText(this, ""+mAuth.getCurrentUser().getPhoneNumber(), Toast.LENGTH_SHORT).show();
        //profileImage
        //profileImage
        String image2="https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    String image=task.getResult().getString("name");
                                    appname.setText(image);
                                    String image1=task.getResult().getString("image");
                                    if (image1.equals(image2)) {
                                       // Picasso.get().load(R.drawable.beard).into(profileImage);
                                    }
                                    else {
                                        Picasso.get().load(image1).into(profileImage);
                                    }


                                }catch (Exception e) {
                                    String image=task.getResult().getString("name");
                                    appname.setText(image);
                                    String image1=task.getResult().getString("image");
                                    if (image1.equals(image2)) {
                                       // Picasso.get().load(R.drawable.beard).into(profileImage);
                                    }
                                    else {
                                        Picasso.get().load(image1).into(profileImage);
                                    }
                                }
                            }
                        }
                    }
                });


        mainBottomNav.setOnNavigationItemSelectedListener(selectlistner);

        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        replaceFragment(homeFragment);
                        return true;

                    case R.id.bottom_chat:
                        replaceFragment(chatFragment);
                        return true;
                    case R.id.profil:
                        replaceFragment(requestFragment);
                        return true;



                    default:
                        return false;
                }
            }
        });

    }
    private void inflateImageSlider() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
      /*
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/G/31/img19/Wireless/Apple/iPhone11/RiverImages/11Pro/IN_iPhone11Pro_DESKTOP_01._CB437064827_.jpg"));
        slideModels.add(new SlideModel("https://piunikaweb.com/wp-content/uploads/2019/08/oneplus_7_pro_5g_experience_the_power_of_5g_banner-750x354.jpg"));
        slideModels.add(new SlideModel("https://lh3.googleusercontent.com/RSyeouwiFX4XVq6iw3H94al0VcXD693tBy2MxhBKCxAHCIfIpdt7wDV47_j2HanPSnTli7JgZ0fYHxESjz0uvVgeCBT3=w1000"));
        slideModels.add(new SlideModel("https://cdn.metrobrands.com/mochi/media/images/content/Homepage/HOTTMARZZ-BANNER-MOCHI.webp"));
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/b2/78/7c/b2787cea792bff7d2c33e26ada6436bb.jpg"));
        slideModels.add(new SlideModel("https://cdnb.artstation.com/p/assets/images/images/016/802/459/large/shuja-shuaib-banner.jpg?1553535424"));
       */
        slideModels.add(new SlideModel(R.drawable.icon));
        slideModels.add(new SlideModel(R.drawable.logo2));
        slideModels.add(new SlideModel(R.drawable.unk));
        //slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/salary.png?alt=media&token=627d0a8e-2bf4-4e84-84e9-5574d9b8fbaa"));

        imageSlider.setImageList(slideModels,true);
    }
    private void getValues() {
        //validating session


        try {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
        }catch (Exception e) {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HomeACTIVITY.this)
                .setBackgroundColor(R.color.white)
                .setTextTitle("Exit")
                .setCancelable(false)
                .setTextSubTitle("Are you want to exit")
                .setBody("User is not stay at app when user click exit button.")
                .setPositiveButtonText("No")
                .setPositiveColor(R.color.toolbar)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButtonText("Exit")
                .setNegativeColor(R.color.colorPrimaryDark)
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        finishAffinity();

                    }
                })
                .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setCancelable(false)
                .build();
        alert.show();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            HomeFragment fragment2 = new HomeFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;



                    }
                    return false;
                }
            };
    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment == homeFragment){
            fragmentTransaction.hide(requestFragment);
            fragmentTransaction.hide(chatFragment);
            fragmentTransaction.hide(requestFragment);

        } else if (fragment == chatFragment){
            fragmentTransaction.hide(requestFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(requestFragment);

        }
        else if(fragment==requestFragment) {
            fragmentTransaction.hide(requestFragment);
            fragmentTransaction.hide(homeFragment);
            fragmentTransaction.hide(chatFragment);
        }


        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
    public void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,homeFragment);
        fragmentTransaction.add(R.id.main_container,chatFragment);
        fragmentTransaction.add(R.id.main_container,requestFragment);


        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(requestFragment);



        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }

    public void logout(View view) {
        AlertDialog.Builder warning = new AlertDialog.Builder(HomeACTIVITY.this)
                .setTitle("Logout")
                .setMessage("Are you want to logout?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();



                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ToDO: delete all the notes created by the Anon user
                        firebaseAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));




                    }
                });

        warning.show();
    }

    public void checkdata(View view) {

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
                                String purches_balance=task.getResult().getString("purches_balance");
                                String shoping_balance=task.getResult().getString("shoping_balance");
                                String self_income=task.getResult().getString("self_income");
                                String daily_income=task.getResult().getString("daily_income");
                                firebaseFirestore.collection("Active_Package")

                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        String tyepe=task.getResult().getString("tyepe");
                                                        new ViewDialog(HomeACTIVITY.this,"Deposit Balance : "+purches_balance,"Withdraw Balance : "+self_income
                                                                , "Total Invest : "+tyepe,"Daily Work Balance : "+daily_income);

                                                    }
                                                    else {
                                                        new ViewDialog(HomeACTIVITY.this,"Deposit Balance : "+purches_balance,"Main Balance : "+self_income
                                                                , "Total Invest : "+"0","Daily Work Balance : "+daily_income);
                                                    }
                                                }
                                            }
                                        });
                               /*
                                AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);
                                builder.setTitle("All Balance")
                                        .setMessage("Purchase Balance : "+purches_balance+"\n" +
                                                "Income Balance : "+self_income+"\n" +
                                                "Shopping Balabce : "+shoping_balance+"\n" +
                                                "Work Balance : "+daily_income);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                                */


                            }
                        }
                    }
                });


       // mDialog = new Dialog(HomeACTIVITY.this);
        //mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
       // mDialog.setContentView(deleteDialogView);
        //mDialog.setContentView(R.layout.dialog_contact);
        //mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       // mDialog.show();

    }
}