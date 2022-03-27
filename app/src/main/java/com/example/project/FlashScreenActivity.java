package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FlashScreenActivity extends AppCompatActivity {
    ProgressBar probTientrinh;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        probTientrinh = (ProgressBar) findViewById(R.id.progressBar2);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                CountDownTimer cntDownTimer = new CountDownTimer(2000, 200) {
                    @Override
                    public void onTick(long l) {
                        int current = probTientrinh.getProgress();
                        int next = probTientrinh.getSecondaryProgress();
                        if (next == probTientrinh.getMax()){
                            current = next;

                        }
                        probTientrinh.setProgress(current + 10);
                        probTientrinh.setSecondaryProgress(next + 10);


                    }

                    @Override
                    public void onFinish() {
                        if(user == null){
                            Intent intent = new Intent(FlashScreenActivity.this, SignInActivity.class);
                            startActivity(intent);

                        }else{
                            Intent intent = new Intent(FlashScreenActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        //chạy tiếp
                        //this.start();
                        //Toast.makeText(SplashActivity.this, "Tiến trình đã hoàn thành", Toast.LENGTH_SHORT).show();
                    }
                };
                cntDownTimer.start();

            }
        }, 500);
    }
}
