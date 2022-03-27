package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoimatKhauActivity extends AppCompatActivity {

    private EditText edtNewPassword;
    private EditText edtOldPassword;
    private EditText edtOldEmail;
    private Button btnChangePassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doimat_khau);

        initUi();
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePassword();

            }
        });
    }

    private void initUi(){
        progressDialog = new ProgressDialog(this);
        edtOldEmail = (EditText) findViewById(R.id.edt_old_email);
        edtNewPassword = (EditText) findViewById(R.id.edt_new_password);
        edtOldPassword = (EditText) findViewById(R.id.edt_old_password);
        btnChangePassword = (Button) findViewById(R.id.btn_change_password);
    }

    private void onClickChangePassword(){
        if(edtOldEmail.getText().toString().trim().equals("")){
            edtOldEmail.setError("Bạn cần nhập email.");
        }else if(edtOldPassword.getText().toString().trim().equals("")){
            edtOldPassword.setError("Bạn cần nhập mật khẩu cũ.");
        }else if(edtNewPassword.getText().toString().trim().equals("")){
            edtNewPassword.setError("Bạn cần nhập mật khẩu mới.");
        }else {

            String strOldEmail = edtOldEmail.getText().toString().trim();
            String strOldPassword = edtOldPassword.getText().toString().trim();
            String strNewPassword = edtNewPassword.getText().toString().trim();

            new AlertDialog.Builder(this)
                    .setTitle("Đổi mật khẩu")
                    .setMessage("Bạn có chắc chắn muốn đổi sang mật khẩu mới?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(strOldEmail, strOldPassword);

                            progressDialog.show();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            assert user != null;
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                // progressDialog.dismiss();
                                                user.updatePassword(strNewPassword)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                progressDialog.dismiss();
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(DoimatKhauActivity.this, "Đã cập nhật mật khẩu mới.", Toast.LENGTH_SHORT).show();

                                                                } else {
                                                                    Toast.makeText(DoimatKhauActivity.this, "Vui lòng đăng nhập lại trước đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(DoimatKhauActivity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }

    }
}