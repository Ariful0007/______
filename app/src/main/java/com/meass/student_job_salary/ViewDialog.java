package com.meass.student_job_salary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewDialog {
    Context ctx;

    public ViewDialog(Context context,String purchas,String self_income,String shoping_balance,String daily_income){
        ctx=context;
        final Dialog mDialog = new Dialog(context);

        //mDialog = new Dialog(HomeACTIVITY.this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
      //  mDialog.setContentView(deleteDialogView);
        mDialog.setContentView(R.layout.dialog_contact);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
        TextView register=(TextView)mDialog.findViewById(R.id.register);
        register.setText(purchas);
        TextView katal=(TextView)mDialog.findViewById(R.id.katal);
        katal.setText(self_income);
        TextView maint=(TextView)mDialog.findViewById(R.id.maint);
        maint.setText(shoping_balance);
        TextView dddddd=(TextView)mDialog.findViewById(R.id.dddddd);
        dddddd.setText(daily_income);
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

}
