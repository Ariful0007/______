package com.meass.student_job_salary;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class ProfileFragment extends Fragment {

TextView name_button,nameTv,namfeTv;
    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout wishlistView;
    private ImageSlider imageSlider;

    //to get user session data
    private UserSession session;
    private TextView tvemail,tvphone;
    private HashMap<String,String> user;
    private String name,email,photo,mobile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    CircleImageView profileImage;
    private StorageReference mStorageRef;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        name_button=view.findViewById(R.id.name_button);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        profileImage=view.findViewById(R.id.profileImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        nameTv=view.findViewById(R.id.nameTv);
        namfeTv=view.findViewById(R.id.namfeTv);
        getValues();
        LinearLayout pinupgrade=view.findViewById(R.id.pinupgrade);
        pinupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UpdatePinActivity.class));
            }
        });


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.info(getContext(),"Go to profile activity.",Toasty.LENGTH_SHORT,true).show();
            }
        });
        return view;
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
        name_button.setText(name);
        nameTv.setText("Contact Number : "+mobile+"\nEmail : "+email);

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
                                    String image=task.getResult().getString("image");
                                    namfeTv.setText("Country : "+task.getResult().getString("transcationpin"));
                                    if (image.equals(image2)) {
                                        try {
                                            Picasso.get().load(R.drawable.man__new).into(profileImage);
                                        }catch (Exception e) {
                                            Picasso.get().load(R.drawable.man__new).into(profileImage);
                                        }
                                    }
                                    else {
                                        try {
                                            Picasso.get().load(image).into(profileImage);
                                        }catch (Exception e) {
                                            Picasso.get().load(image).into(profileImage);
                                        }
                                    }

                                }catch (Exception e) {
                                    String image=task.getResult().getString("image");
                                    if (image.equals(image2)) {
                                        try {
                                            Picasso.get().load(R.drawable.beard).into(profileImage);
                                        }catch (Exception e12) {
                                            Picasso.get().load(R.drawable.beard).into(profileImage);
                                        }
                                    }
                                    else {
                                        try {
                                            Picasso.get().load(image).into(profileImage);
                                        }catch (Exception e1) {
                                            Picasso.get().load(image).into(profileImage);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });



        
    }
}