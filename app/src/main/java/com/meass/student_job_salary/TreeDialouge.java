package com.meass.student_job_salary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class TreeDialouge {
    Context ctx;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    TreeAdapter1 getDataAdapter1;
    List<Refdferal_model> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    TextView username,loginid,name,ranking,sponsar,teamA,teamB,teamC,logindate;
    String email;

    public TreeDialouge(Context context,String email){
        ctx=context;
        final Dialog mDialog = new Dialog(context);

        //mDialog = new Dialog(HomeACTIVITY.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
        //  mDialog.setContentView(deleteDialogView);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        mDialog.setContentView(R.layout.treeabc);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
        getList = new ArrayList<>();
        getDataAdapter1 = new TreeAdapter1(getList,context);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("Persions").document(email)
                .collection("List").document();
        RecyclerView recyclerView = (RecyclerView)mDialog.findViewById(R.id.commentRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(getDataAdapter1);
        firebaseFirestore.collection("Persions").document(email)
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

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
    private void reciveData() {



    }
}
