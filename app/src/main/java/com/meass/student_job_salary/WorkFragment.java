package com.meass.student_job_salary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
import com.mikepenz.materialdrawer.Drawer;
import com.owater.library.CircleTextView;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import es.dmoral.toasty.Toasty;

public class WorkFragment extends Fragment {

    private static final String TAG = "MainActivity";
    VideoView videoView;
    EditText textView;   private HashMap<String, String> user;
    String sessionname,sessionmobile,sessionphoto,sessionemail,sessionusername;
    //For looking logs
    ArrayAdapter adapter; private String name, email, photo, mobile,username;
    private Drawer result;
    ArrayList<String> list = new ArrayList<>();
    private  String tyepe,active;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    KProgressHUD kProgressHUD;
    CircleTextView cir;
    TextView maintokiyo;
    private UserSession session;
    //data load
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    ActivepackageAdapter getDataAdapter1;
    List<BloodBankModel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    //to get user session data

    public WorkFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_work, container, false);
        //load data

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        session=new UserSession(getContext());
        getValues();

        getList = new ArrayList<>();
        getDataAdapter1 = new ActivepackageAdapter(getList,getContext(),cus_name);

        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  =  firebaseFirestore.collection("Packages").document();
        recyclerView =view.findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(getDataAdapter1);
        firebaseFirestore.collection("Packages")
                .orderBy("time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        BloodBankModel get = ds.getDocument().toObject(BloodBankModel.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });
        //videoView = view.findViewById(R.id.videoview);
        //String path = "android.resource://" + getActivity().getPackageName() + File.separator + R.raw.main;
        videoView = (VideoView) view.findViewById(R.id.videoView);
        session=new UserSession(view.getContext());

        kProgressHUD=KProgressHUD.create(getContext());
        maintokiyo=view.findViewById(R.id.maintokiyo);

        cir=view.findViewById(R.id.cir);
        Calendar calendar=Calendar.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        firebaseFirestore.collection("DailyEarn")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection(""+year)
                .document(""+month)
                .collection(""+day)
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                cir.setVisibility(View.INVISIBLE);

                            }
                            else {
                                cir.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


        //textView=view.findViewById(R.id.bonus);
        cir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getContext(),"Tab for 3s .....",Toasty.LENGTH_SHORT,true).show();
            }
        });
        firebaseFirestore.collection("Active_Package")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String active=task.getResult().getString("active");
                                String tyepe=task.getResult().getString("tyepe");
                                if (tyepe.equals("a")) {
                                    maintokiyo.setText("25 tk");

                                }
                               else if (tyepe.equals("b")) {
                                    maintokiyo.setText("65 tk");
                                }
                                else if (tyepe.equals("c")) {
                                    maintokiyo.setText("120 tk");
                                }
                              else  if (tyepe.equals("d")) {
                                    maintokiyo.setText("250 tk");
                                }
                               else if (tyepe.equals("e")) {
                                    maintokiyo.setText("350 tk");
                                }
                               else if (tyepe.equals("f")) {
                                    maintokiyo.setText("500 tk");
                                }
                               else if (tyepe.equals("g")) {
                                    maintokiyo.setText("700 tk");
                                }
                            }
                        }
                    }
                });
cir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firebaseFirestore.collection("Packages_Checker")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String active=task.getResult().getString("counter");
                                String tyepe=task.getResult().getString("daily_income");
                                if (active.equals("1")) {
                                    Calendar calendar=Calendar.getInstance();
                                    final  int year=calendar.get(Calendar.YEAR);
                                    final int month=calendar.get(Calendar.MONTH);
                                    final int day=calendar.get(Calendar.DAY_OF_MONTH);
                                    firebaseFirestore.collection("DailyEarn")
                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                            .collection(""+year)
                                            .document(""+month)
                                            .collection(""+day)
                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.WARNING)
                                                                    .setTitle("Warning")
                                                                    .setMessage("You are alreday clime  task for today.\nWait for next day's task.")
                                                                    .setAnimation(DialogAnimation.SPLIT)
                                                                    .setOnClickListener(new OnDialogClickListener() {
                                                                        @Override
                                                                        public void onClick(AestheticDialog.Builder builder) {
                                                                            builder.dismiss();

                                                                        }
                                                                    }).show();

                                                        }
                                                        else {

                                                           firebaseFirestore.collection("Counttter")
                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                   .get()
                                                                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                       @Override
                                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                           if (task.isSuccessful()) {
                                                                               if (task.getResult().exists()) {
                                                                                   String counter=task.getResult().getString("counter");
                                                                                   int  n=Integer.parseInt(counter)+1;
                                                                                   if (Integer.parseInt(counter)==45) {
                                                                                       new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.ERROR)
                                                                                               .setTitle("Information")
                                                                                               .setMessage("You have collect all activation investment on current deposit package.\nPlease active a invest.")
                                                                                               .setAnimation(DialogAnimation.SPLIT)
                                                                                               .setOnClickListener(new OnDialogClickListener() {
                                                                                                   @Override
                                                                                                   public void onClick(AestheticDialog.Builder builder) {
                                                                                                       builder.dismiss();

                                                                                                   }
                                                                                               }).show();
                                                                                   }
                                                                                   else if (n==45){
                                                                                       Toasty.success(getContext(),"Video Starting .....",Toasty.LENGTH_SHORT,true).show();
                                                                                       cir.setVisibility(View.INVISIBLE);

                                                                                       videoView.setMediaController(new MediaController(getContext()));
                                                                                       Uri video = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.s_1);
                                                                                       videoView.setVideoURI(video);
                                                                                       videoView.setMediaController(null);
                                                                                       videoView.start();
                                                                                       Handler handler=new Handler();
                                                                                       handler.postDelayed(new Runnable() {
                                                                                           @Override
                                                                                           public void run() {
                                                                                               new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                       .setTitle("Success")
                                                                                                       .setMessage("First Work  Done. Take daily bonus.")
                                                                                                       .setAnimation(DialogAnimation.SPLIT)
                                                                                                       .setOnClickListener(new OnDialogClickListener() {
                                                                                                           @Override
                                                                                                           public void onClick(AestheticDialog.Builder builder) {
                                                                                                               builder.dismiss();
                                                                                                               firebaseFirestore.collection("Counttter")
                                                                                                                       .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                       .update("counter",""+n)
                                                                                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                           @Override
                                                                                                                           public void onComplete(@NonNull Task<Void> task) {

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
                                                                                                                                       try {

                                                                                                                                           String taka = task.getResult().getString("self_income");
                                                                                                                                           double total=Double.parseDouble(taka)+Double.parseDouble(tyepe);
                                                                                                                                           String taka_daily_income = task.getResult().getString("daily_income");
                                                                                                                                           double total_daily_income=Double.parseDouble(taka_daily_income)+Double.parseDouble(tyepe);
                                                                                                                                           firebaseFirestore.collection("Users")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                                                   .collection("Main_Balance")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                   .update("self_income",""+total,"daily_income",""+total_daily_income)
                                                                                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                       @Override
                                                                                                                                                       public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                       }
                                                                                                                                                   });
                                                                                                                                           Calendar calendar = Calendar.getInstance();

                                                                                                                                           firebaseFirestore.collection("Users")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                                                   .get()
                                                                                                                                                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                                       @Override
                                                                                                                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                                           if (task.isSuccessful()) {
                                                                                                                                                               if (task.getResult().exists()) {
                                                                                                                                                                   Calendar calendar = Calendar.getInstance();
                                                                                                                                                                   final int year = calendar.get(Calendar.YEAR);
                                                                                                                                                                   final int month = calendar.get(Calendar.MONTH);
                                                                                                                                                                   final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                                                                   Map<String, String> userMap1 = new HashMap<>();

                                                                                                                                                                   userMap1.put("counter", firebaseAuth.getCurrentUser().getEmail());
                                                                                                                                                                   int a = 16;
                                                                                                                                                                   int b = 8;
                                                                                                                                                                   firebaseFirestore.collection("Packages_Checker")
                                                                                                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .delete()
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                                               }
                                                                                                                                                                           });


                                                                                                                                                                   //todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                                                   ///generation_bonus1("Gold", firebaseAuth.getCurrentUser().getEmail(), firebaseAuth.getCurrentUser().getUid(), "ariful", "TeamB", "TeamB", 24, a, b);
                                                                                                                                                                   firebaseFirestore.collection("DailyEarn")
                                                                                                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .collection("" + year)
                                                                                                                                                                           .document("" + month)
                                                                                                                                                                           .collection("" + day)
                                                                                                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .set(userMap1)
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                                               }
                                                                                                                                                                           });



                                                                                                                                                                   Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                                                   String ts = tsLong.toString();
                                                                                                                                                                   String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                                                                                   String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                                                   Income income = new Income(task.getResult().getString("username"), "Daily  Income", firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                                           ""+tyepe, current1,ts,"Ltsx"+ts,"0");
                                                                                                                                                                   String username=task.getResult().getString("username");

                                                                                                                                                                   firebaseFirestore.collection("Income_History").document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .collection("List")
                                                                                                                                                                           .document(UUID.randomUUID().toString())
                                                                                                                                                                           .set(income)
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                                   if (task.isSuccessful()) {

                                                                                                                                                                                       new AestheticDialog.Builder((Activity) getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                               .setTitle("Successful")
                                                                                                                                                                                               .setMessage("You are successfully  clime this task for today.\nWait for next day's task.")
                                                                                                                                                                                               .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                               .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                                   @Override
                                                                                                                                                                                                   public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                                       builder.dismiss();
                                                                                                                                                                                                       startActivity(new Intent(getContext(),MainActivity.class));



                                                                                                                                                                                                   }
                                                                                                                                                                                               }).show();





                                                                                                                                                                                   }
                                                                                                                                                                               }
                                                                                                                                                                           });
                                                                                                                                                               }
                                                                                                                                                           }
                                                                                                                                                       }
                                                                                                                                                   });




                                                                                                                                       } catch (Exception e) {

                                                                                                                                           String taka = task.getResult().getString("main_balance");

                                                                                                                                       }
                                                                                                                                   } else {

                                                                                                                                   }
                                                                                                                               }
                                                                                                                           }
                                                                                                                       });







                                                                                                           }


                                                                                                       }).show();

                                                                                           }
                                                                                       },3000);
                                                                                   }
                                                                                   else  if (Integer.parseInt(counter)<45) {
                                                                                       Toasty.success(getContext(),"Video Starting .....",Toasty.LENGTH_SHORT,true).show();
                                                                                       cir.setVisibility(View.INVISIBLE);

                                                                                       videoView.setMediaController(new MediaController(getContext()));
                                                                                       Uri video = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.s_1);
                                                                                       videoView.setVideoURI(video);
                                                                                       videoView.setMediaController(null);
                                                                                       videoView.start();
                                                                                       Handler handler=new Handler();
                                                                                       handler.postDelayed(new Runnable() {
                                                                                           @Override
                                                                                           public void run() {
                                                                                               new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                       .setTitle("Success")
                                                                                                       .setMessage("First Work  Done. Take daily bonus.")
                                                                                                       .setAnimation(DialogAnimation.SPLIT)
                                                                                                       .setOnClickListener(new OnDialogClickListener() {
                                                                                                           @Override
                                                                                                           public void onClick(AestheticDialog.Builder builder) {
                                                                                                               builder.dismiss();
                                                                                                               firebaseFirestore.collection("Counttter")
                                                                                                                       .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                       .update("counter",""+n)
                                                                                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                           @Override
                                                                                                                           public void onComplete(@NonNull Task<Void> task) {

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
                                                                                                                                       try {

                                                                                                                                           String taka = task.getResult().getString("self_income");
                                                                                                                                           double total=Double.parseDouble(taka)+Double.parseDouble(tyepe);
                                                                                                                                           String taka_daily_income = task.getResult().getString("daily_income");
                                                                                                                                           double total_daily_income=Double.parseDouble(taka_daily_income)+Double.parseDouble(tyepe);
                                                                                                                                           firebaseFirestore.collection("Users")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                                                   .collection("Main_Balance")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                   .update("self_income",""+total,"daily_income",""+total_daily_income)
                                                                                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                       @Override
                                                                                                                                                       public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                       }
                                                                                                                                                   });
                                                                                                                                           Calendar calendar = Calendar.getInstance();

                                                                                                                                           firebaseFirestore.collection("Users")
                                                                                                                                                   .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                                                                   .get()
                                                                                                                                                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                                       @Override
                                                                                                                                                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                                           if (task.isSuccessful()) {
                                                                                                                                                               if (task.getResult().exists()) {
                                                                                                                                                                   Calendar calendar = Calendar.getInstance();
                                                                                                                                                                   final int year = calendar.get(Calendar.YEAR);
                                                                                                                                                                   final int month = calendar.get(Calendar.MONTH);
                                                                                                                                                                   final int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                                                                                                                                   Map<String, String> userMap1 = new HashMap<>();

                                                                                                                                                                   userMap1.put("counter", firebaseAuth.getCurrentUser().getEmail());
                                                                                                                                                                   int a = 16;
                                                                                                                                                                   int b = 8;


                                                                                                                                                                   //todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                                                   ///generation_bonus1("Gold", firebaseAuth.getCurrentUser().getEmail(), firebaseAuth.getCurrentUser().getUid(), "ariful", "TeamB", "TeamB", 24, a, b);
                                                                                                                                                                   firebaseFirestore.collection("DailyEarn")
                                                                                                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .collection("" + year)
                                                                                                                                                                           .document("" + month)
                                                                                                                                                                           .collection("" + day)
                                                                                                                                                                           .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .set(userMap1)
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                                               }
                                                                                                                                                                           });



                                                                                                                                                                   Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                                                   String ts = tsLong.toString();
                                                                                                                                                                   String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                                                                                   String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                                                   Income income = new Income(task.getResult().getString("username"), "Daily  Income", firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                                           ""+tyepe, current1,ts,"Ltsx"+ts,"0");
                                                                                                                                                                   String username=task.getResult().getString("username");
                                                                                                                                                                   firebaseFirestore.collection("Income_History").document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                           .collection("List")
                                                                                                                                                                           .document(UUID.randomUUID().toString())
                                                                                                                                                                           .set(income)
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                                   if (task.isSuccessful()) {

                                                                                                                                                                                       new AestheticDialog.Builder((Activity) getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                               .setTitle("Successful")
                                                                                                                                                                                               .setMessage("You are successfully  clime this task for today.\nWait for next day's task.")
                                                                                                                                                                                               .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                               .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                                   @Override
                                                                                                                                                                                                   public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                                       builder.dismiss();
                                                                                                                                                                                                       startActivity(new Intent(getContext(),MainActivity.class));



                                                                                                                                                                                                   }
                                                                                                                                                                                               }).show();





                                                                                                                                                                                   }
                                                                                                                                                                               }
                                                                                                                                                                           });
                                                                                                                                                               }
                                                                                                                                                           }
                                                                                                                                                       }
                                                                                                                                                   });




                                                                                                                                       } catch (Exception e) {

                                                                                                                                           String taka = task.getResult().getString("main_balance");

                                                                                                                                       }
                                                                                                                                   } else {

                                                                                                                                   }
                                                                                                                               }
                                                                                                                           }
                                                                                                                       });







                                                                                                           }


                                                                                                       }).show();

                                                                                           }
                                                                                       },3000);

                                                                                   }
                                                                               }
                                                                           }
                                                                       }
                                                                   });


                                                        }
                                                    }
                                                    else {
                                                        new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.ERROR)
                                                                .setTitle("Failed")
                                                                .setMessage("You havn't any activation investment.\nPlease active a invest.")
                                                                .setAnimation(DialogAnimation.SPLIT)
                                                                .setOnClickListener(new OnDialogClickListener() {
                                                                    @Override
                                                                    public void onClick(AestheticDialog.Builder builder) {
                                                                        builder.dismiss();

                                                                    }
                                                                }).show();
                                                    }
                                                }
                                            });


                                }
                                else {
                                    new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.ERROR)
                                            .setTitle("Failed")
                                            .setMessage("You havn't any activation investment.\nPlease active a invest.")
                                            .setAnimation(DialogAnimation.SPLIT)
                                            .setOnClickListener(new OnDialogClickListener() {
                                                @Override
                                                public void onClick(AestheticDialog.Builder builder) {
                                                    builder.dismiss();

                                                }
                                            }).show();

                                }
                            }
                            else {
                                new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.ERROR)
                                        .setTitle("Failed")
                                        .setMessage("You havn't any activation investment.\nPlease active a invest.")
                                        .setAnimation(DialogAnimation.SPLIT)
                                        .setOnClickListener(new OnDialogClickListener() {
                                            @Override
                                            public void onClick(AestheticDialog.Builder builder) {
                                                builder.dismiss();

                                            }
                                        }).show();
                            }
                        }
                    }
                });
    }
});



        return view;
    }

    private void progress_check() {
        kProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Uploading  Data...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
        cus_name=user.get(UserSession.Username);


    }
}