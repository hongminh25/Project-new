package com.example.project;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailedit, passedit;
    private Button btnregis;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        emailedit = findViewById(R.id.email);
        passedit = findViewById(R.id.password);
        btnregis = findViewById(R.id.btnregis);

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String email, pass;
        if(emailedit.getText().toString().trim().equals("")){
            emailedit.setError("Bạn cần nhập email.");
            Toast.makeText(this, "Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(passedit.getText().toString().trim().length() < 6){
            passedit.setError("Mật khẩu cần ít nhất 6 chữ số.");
            //             dialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            email = emailedit.getText().toString().trim();
            pass = passedit.getText().toString().trim();

            // tao moi email va pass
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Thông tin không hợp lệ hoặc đã được sử dụng.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
