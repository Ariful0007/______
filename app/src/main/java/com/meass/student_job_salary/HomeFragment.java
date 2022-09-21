package com.meass.student_job_salary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
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

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

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
String get;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.slider);
        inflateImageSlider();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Counttter")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    get=task.getResult().getString("counter");
                                }catch (Exception e) {
                                    get=task.getResult().getString("counter");
                                }
                            }
                        }
                    }
                });
        CardView wor1111k=view.findViewById(R.id.wor1111k);
        wor1111k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        CardView work33=view.findViewById(R.id.work33);
        work33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        CardView wor1111k1133=view.findViewById(R.id.wor1111k1133);
        wor1111k1133.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        CardView watchCard=view.findViewById(R.id.watchCard);
        watchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),NotificationActivity.class);
                startActivity(intent);
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });

        CardView taskCard=view.findViewById(R.id.taskCard);
        taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),TreeSettings.class);
                startActivity(intent);
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        CardView luckySpinCard=view.findViewById(R.id.luckySpinCard);
        luckySpinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),TranscationMain.class);
                startActivity(intent);
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });

        CardView redeemCard=view.findViewById(R.id.redeemCard);
        redeemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), All_Packages.class);
                startActivity(intent);
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        CardView dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),FULL_REFFER.class);
                startActivity(intent);
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        LinearLayout linear=view.findViewById(R.id.linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),HelplineActivity.class);
                startActivity(intent);
            }
        });
        //status check
        CardView referCard=view.findViewById(R.id.referCard);
        referCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               firebaseFirestore.collection("Packages_Checker")
                       .document(firebaseAuth.getCurrentUser().getEmail())
                       .get()
                       .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                               if (task.isSuccessful()) {
                                   if (task.getResult().exists()) {
                                       AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                       builder.setTitle("Deposit Status")
                                               .setMessage("Your deposit package name is : "+task.getResult().getString("package_name")+"" +
                                                       "\n Total Daily Bonus You Gain : "+get)
                                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       dialog.dismiss();
                                                   }
                                               }).create();
                                       builder.show();
                                   }
                                   else {
                                       Toasty.warning(getContext(),"You havn't activate your accout and if you want to active account please deposit first.",Toasty.LENGTH_SHORT,true).show();
                                   }
                               }
                           }
                       });
                //Toasty.info(getContext(),"Coming.....", Toast.LENGTH_SHORT,true).show();
            }
        });
        return view; 
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
        slideModels.add(new SlideModel(R.drawable.globallife));
        slideModels.add(new SlideModel(R.drawable.globallife));
        slideModels.add(new SlideModel(R.drawable.globallife));

        slideModels.add(new SlideModel(R.drawable.globallife));

        //slideModels.add(new SlideModel(R.drawable.i3));
        //slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/salary.png?alt=media&token=627d0a8e-2bf4-4e84-84e9-5574d9b8fbaa"));

        imageSlider.setImageList(slideModels,true);
    }
}