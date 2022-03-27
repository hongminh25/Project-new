package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    private View view;
    private Button btnDangki;
    private Button btnDangnhap;
    private Button btnDangxuat;
    private Button btnDoimatkhau;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        displayUI();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            btnDangxuat.setVisibility(view.GONE);
        }else{
            btnDangnhap.setVisibility(view.GONE);
            btnDangki.setVisibility(view.GONE);
        }

        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

//        btnDoimatkhau.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getActivity(), SignInActivity.class);
//                startActivity(intent);
//
//            }
//        });

        return view;
    }

    void displayUI() {
        btnDangki = (Button) view.findViewById(R.id.btn_dangki);
        btnDangnhap = (Button) view.findViewById(R.id.btn_dangnhap);
        btnDangxuat = (Button) view.findViewById(R.id.btn_dangxuat);
        btnDoimatkhau = (Button) view.findViewById(R.id.btn_DoiMK);

    }
}