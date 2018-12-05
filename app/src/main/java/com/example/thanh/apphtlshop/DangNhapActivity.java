package com.example.thanh.apphtlshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class DangNhapActivity extends AppCompatActivity {
    EditText txtTaiKhoan,txtMatKhau;
    Button btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        txtTaiKhoan=findViewById(R.id.txtTaiKhoan);
        txtMatKhau=findViewById(R.id.txtMatKhau);
        btnDangNhap=findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDangNhap();
            }
        });
    }

    private void xuLyDangNhap() {

    }


}
