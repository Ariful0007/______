package com.meass.student_job_salary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class ActivepackageAdapter extends RecyclerView.Adapter<ActivepackageAdapter.myview> {
    public List<BloodBankModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Context context;
    String username;

    public ActivepackageAdapter(List<BloodBankModel> data, Context context,String username) {
        this.data = data;
        this.context=context;
        this.username=username;
    }

    @NonNull
    @Override
    public ActivepackageAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.packagess,parent,false);
        return new ActivepackageAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivepackageAdapter.myview holder, final int position) {
firebaseAuth=FirebaseAuth.getInstance();
firebaseFirestore=FirebaseFirestore.getInstance();

holder.logout.setText("Plan name : "+data.get(position).getName()+"\n" +
        "Plan Price : "+data.get(position).getFeeentry()+" TK\n" +
        "Daily Income : "+data.get(position).getTime()+" TK");
holder.dddddd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(view.getContext())
                .setBackgroundColor(R.color.white)
                .setCancelable(false)
                .setTextSubTitle("Are you want to deposit on  this package ?")
                .setPositiveButtonText("No")
                .setPositiveColor(R.color.toolbar)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButtonText("Yes")
                .setNegativeColor(R.color.colorPrimaryDark)
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

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
                                                String pass = task.getResult().getString("purches_balance");
                                                if (Double.parseDouble(pass) < Double.parseDouble(data.get(position).getFeeentry())) {
                                                    Toasty.error(view.getContext().getApplicationContext(), "User have not enough money", Toasty.LENGTH_SHORT, true).show();

                                                } else {
                                                    final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                            .setLabel("Checking Data.....")
                                                            .setCancellable(false)
                                                            .setAnimationSpeed(2)
                                                            .setDimAmount(0.5f)
                                                            .show();
                                                    double mainBaki =Double.parseDouble(pass) - Double.parseDouble(data.get(position).getFeeentry());
                                                    firebaseFirestore.collection("Users")
                                                            .document(firebaseAuth.getCurrentUser().getUid())
                                                            .collection("Main_Balance")
                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                            .update("purches_balance", "" + mainBaki)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Map<String, String> userMap1 = new HashMap<>();

                                                                        userMap1.put("counter", "1");
                                                                        userMap1.put("package_", ""+username);
                                                                        userMap1.put("package_name",data.get(position).getName());
                                                                        userMap1.put("package_price",data.get(position).getFeeentry());
                                                                        userMap1.put("daily_income",data.get(position).getTime());
                                                                        userMap1.put("email",firebaseAuth.getCurrentUser().getEmail());
                                                                        userMap1.put("uuid",firebaseAuth.getCurrentUser().getUid());
                                                                        firebaseFirestore.collection("Packages_Checker")
                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .set(userMap1)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            Map<String, String> counter = new HashMap<>();

                                                                                            counter.put("counter", "0");
                                                                                            firebaseFirestore.collection("Counttter")
                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                    .set(counter)
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {

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
                                                                                                                    Map<String, String> userMap1 = new HashMap<>();

                                                                                                                    userMap1.put("package", ""+data.get(position).getName());
                                                                                                                    userMap1.put("price", ""+data.get(position).getFeeentry());
                                                                                                                    userMap1.put("daily", ""+data.get(position).getTime());
                                                                                                                    firebaseFirestore.collection("Mypackesss")
                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                            .set(userMap1)
                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                }
                                                                                                                            });
                                                                                                                    taking(task.getResult().getString("refername_email"));

                                                                                                                    firebaseFirestore.collection("Group_Detector")
                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                            .get()
                                                                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                        if (task.getResult().exists()) {
                                                                                                                                            int a = 1245;
                                                                                                                                            int b = 249;
                                                                                                                                            third = 249;
                                                                                                                                            main_invest = 24910;
                                                                                                                                            detector = "Diamond";
                                                                                                                                            package_point=6;
                                                                                                                                            refer=((Double.parseDouble(data.get(position).getFeeentry()))*2)/100;
                                                                                                                                            first=(Double.parseDouble(data.get(position).getFeeentry()))*5/100;
                                                                                                                                            secon=(Double.parseDouble(data.get(position).getFeeentry()))*3/100;
                                                                                                                                            thhird=(Double.parseDouble(data.get(position).getFeeentry()))*2/100;
                                                                                                                                            fourth=(Double.parseDouble(data.get(position).getFeeentry()))*1/100;


                                                                                                                                            String counter = task.getResult().getString("counter");
                                                                                                                                            //  todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                            generation_bonus1("Diamond", firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                    firebaseAuth.getCurrentUser().getUid(), task.getResult().getString("refername"),
                                                                                                                                                    "TeamB", counter, refer, first, secon);
                                                                                                                                            //  Toasty.success(getApplicationContext(),"Account Activation Successfully Done.",Toasty.LENGTH_SHORT,true).show();

                                                                                                                                        } else {
                                                                                                                                            int a = 75;
                                                                                                                                            int b = 15;
                                                                                                                                            third = 15;
                                                                                                                                            main_invest = 1510;
                                                                                                                                            detector = "Silver";
                                                                                                                                            package_point=6;
                                                                                                                                            //todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                            refer=((Double.parseDouble(data.get(position).getFeeentry()))*2)/100;
                                                                                                                                            first=(Double.parseDouble(data.get(position).getFeeentry()))*5/100;
                                                                                                                                            secon=(Double.parseDouble(data.get(position).getFeeentry()))*3/100;
                                                                                                                                            thhird=(Double.parseDouble(data.get(position).getFeeentry()))*2/100;
                                                                                                                                            fourth=(Double.parseDouble(data.get(position).getFeeentry()))*1/100;

                                                                                                                                            String counter = task.getResult().getString("counter");
                                                                                                                                            //  todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                            generation_bonus1("Diamond", firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                    firebaseAuth.getCurrentUser().getUid(), task.getResult().getString("refername"),
                                                                                                                                                    "TeamB", counter, refer, first, secon);
                                                                                                                                            // Toasty.success(getApplicationContext(),"Account Activation Successfully Done.",Toasty.LENGTH_SHORT,true).show();
                                                                                                                                        }
                                                                                                                                    } else {
                                                                                                                                        int a = 75;
                                                                                                                                        int b = 15;
                                                                                                                                        third = 15;
                                                                                                                                        main_invest = 1510;
                                                                                                                                        detector = "Silver";
                                                                                                                                        package_point=6;
                                                                                                                                        //todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                        refer=((Double.parseDouble(data.get(position).getFeeentry()))*2)/100;
                                                                                                                                        first=(Double.parseDouble(data.get(position).getFeeentry()))*5/100;
                                                                                                                                        secon=(Double.parseDouble(data.get(position).getFeeentry()))*3/100;
                                                                                                                                        thhird=(Double.parseDouble(data.get(position).getFeeentry()))*2/100;
                                                                                                                                        fourth=(Double.parseDouble(data.get(position).getFeeentry()))*1/100;


                                                                                                                                        String counter = task.getResult().getString("counter");
                                                                                                                                        //  todays_free(firebaseAuth.getCurrentUser().getEmail().toString());
                                                                                                                                        generation_bonus1("Diamond", firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                firebaseAuth.getCurrentUser().getUid(), task.getResult().getString("refername"),
                                                                                                                                                "TeamB", counter, refer, first, secon);
                                                                                                                                        // Toasty.success(getApplicationContext(),"Account Activation Successfully Done.",Toasty.LENGTH_SHORT,true).show();
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            });

                                                                                                                }
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
//show

                    }
                })
                .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setCancelable(false)
                .build();
        alert.show();
    }
});

    }
    double main_invest,third,package_point;
    String detector;
    double first,secon,thhird,refer,fourth;
    private void taking(String email) {
        Map<String, String> userMap1 = new HashMap<>();

        userMap1.put("counter", "1");
        firebaseFirestore.collection("Package_takingChecker")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .set(userMap1)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });

    }
    String ts;
    private void generation_bonus1(String valueFromSpinner, String email, String uid, String
            string, String getC, String groupdetector, double first, double second__, double mainthird) {
        String mainuuid = UUID.randomUUID().toString();
        // new CheckInternetConnection(this).checkConnection();
        Long tsLong = System.currentTimeMillis() / 1000;
        ts = tsLong.toString();
        firebaseFirestore.collection("ALL_GET")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                final String refername = task.getResult().getString("refername");
                                String referemail = task.getResult().getString("refername_email");

                                // Toast.makeText(ActivePackage.this, ""+referemail, Toast.LENGTH_SHORT).show();

                                firebaseFirestore.collection("All_UserID")
                                        .document(referemail)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        final String uuid = task.getResult().getString("uuid");
                                                        firebaseFirestore.collection("Users")
                                                                .document(uuid)
                                                                .collection("Main_Balance")
                                                                .document(referemail)
                                                                .get()
                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                        if (task.getResult().exists()) {
                                                                            final double a = first;
                                                                            String third_level=task.getResult().getString("third_level");
                                                                            double sum_mon=main_invest+Double.parseDouble(third_level);
                                                                            String main_balance = task.getResult().getString("main_balance");
                                                                            double Submain = Double.parseDouble(main_balance) + a;
                                                                            String sponsor_income = task.getResult().getString("sponsor_income");
                                                                            double subsponsor_income = Double.parseDouble(sponsor_income) + a;
                                                                            String first_level = task.getResult().getString("first_level");
                                                                            double subfirst_level = Double.parseDouble(first_level) + a;
                                                                            String giving_balance = task.getResult().getString("giving_balance");
                                                                            double subgiving_balance = Double.parseDouble(giving_balance) + first;
                                                                            firebaseFirestore.collection("Users")
                                                                                    .document(uuid)
                                                                                    .collection("Main_Balance")
                                                                                    .document(referemail)
                                                                                    .update("third_level",""+sum_mon,"main_balance", String.valueOf(Submain),
                                                                                            "sponsor_income", String.valueOf(subsponsor_income),
                                                                                            "first_level", String.valueOf(subfirst_level), "giving_balance",
                                                                                            String.valueOf(subgiving_balance))
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                Refdferal_model refdferal_model = new Refdferal_model(string, referemail, uid,
                                                                                                        username, mainuuid, email, ts);
                                                                                                firebaseFirestore.collection("Persions")
                                                                                                        .document(referemail).collection("List")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .set(refdferal_model)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                }
                                                                                                            }
                                                                                                        });
                                                                                                firebaseFirestore.collection("User2")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .get()
                                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    if (task.getResult().exists()) {
                                                                                                                        String real11, real2, real3;

                                                                                                                        // Toast.makeText(ActivePackage.this, ""+referemail, Toast.LENGTH_SHORT).show();
                                                                                                                        firebaseFirestore.collection("My_Tree")
                                                                                                                                .document("Tream")
                                                                                                                                .collection("TeamA")
                                                                                                                                .document(referemail)
                                                                                                                                .collection("List")
                                                                                                                                .document(mainuuid)
                                                                                                                                .set(refdferal_model)
                                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                            double ttt = refer;
                                                                                                                                            Calendar calendar = Calendar.getInstance();
                                                                                                                                            String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                                                                                            String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                            Income income = new Income(refername, "Refer from " + username, email,
                                                                                                                                                    String.valueOf(ttt), current1, ts,"Lxt"+ts,"0");
                                                                                                                                            firebaseFirestore.collection("Income_History").document(referemail)
                                                                                                                                                    .collection("List")
                                                                                                                                                    .document(UUID.randomUUID().toString())
                                                                                                                                                    .set(income)
                                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                                //second level start
                                                                                                                                                                //second level start
                                                                                                                                                                Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                                                                context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                //  Toast.makeText(RegisterAcivity.this, ""+referemail, Toast.LENGTH_SHORT).show();

                                                                                                                                                            } else {
                                                                                                                                                                Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                                                                context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                                                //Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    });
                                                                                                                                        } else {
                                                                                                                                            Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                                            context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                                                                           // Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                });
                                                                                                                    } else {
                                                                                                                        Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                        context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                                                        //Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                    }

                                                                                                                    //exsiii
                                                                                                                } else {
                                                                                                                    Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                    context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                                                    //Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                                }
                                                                                                            }
                                                                                                        });


                                                                                                //trrr
                                                                                            } else {
                                                                                                Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                                context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                                               // Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                        } else {
                                                                            Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                            context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                                            //Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                        context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                      //  Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                    }
                                                } else {
                                                    Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                    context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                                                  //  Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                                }
                                            }
                                        });
                            } else {
                                Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                                context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                               // Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                            context.startActivity(new Intent(context.getApplicationContext(),HomeACTIVITY.class));

                            //Toasty.success(context.getApplicationContext(), "Account Activation Successfully Done.", Toasty.LENGTH_SHORT, true).show();
                        }
                    }
                });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        CardView dddddd;
        public myview(@NonNull View itemView) {
            super(itemView);
logout=itemView.findViewById(R.id.morei);
            dddddd=itemView.findViewById(R.id.dddddd);

        }
    }
}

