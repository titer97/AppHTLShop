package com.example.thanh.apphtlshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thanh.model.DanhMuc;
import com.example.thanh.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ThemSpActivity extends AppCompatActivity {
    EditText edtTenSanPham,edtSoLuong,edtDonGia,edtBaoHanh,edtMaLoai,edtMaNCC,edtAnhBia,edtMoTa,edtNgayCapNhat;
    Button btnThemSanPham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sp);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemSp();
            }
        });
    }

    private void xuLyThemSp() {
        SanPham sp=new SanPham();
        sp.setTenSp(edtTenSanPham.getText().toString());
        sp.setSoLuongTon(Integer.parseInt(edtSoLuong.getText().toString()));
        sp.setGiaBan(Integer.parseInt(edtDonGia.getText().toString()));
        sp.setBaoHanh(edtBaoHanh.getText().toString());
        sp.setMaLoai(Integer.parseInt(edtMaLoai.getText().toString()));
        sp.setMaNcc(Integer.parseInt(edtMaNCC.getText().toString()));
        sp.setAnhBia(edtAnhBia.getText().toString());
        sp.setMoTa(edtMoTa.getText().toString());
        sp.setNgayCapNhat(edtNgayCapNhat.getText().toString());
        ThemSanPhamTask task=new ThemSanPhamTask();
        task.execute(sp);
    }

    private void addControls() {
        edtTenSanPham = findViewById(R.id.edtTenSanPham);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtDonGia = findViewById(R.id.edtDonGia);
        edtBaoHanh=findViewById(R.id.edtBaoHanh);
        edtMaLoai=findViewById(R.id.edtMaLoai);
        edtMaNCC=findViewById(R.id.edtMaNCC);
        edtAnhBia=findViewById(R.id.edtAnhBia);
        edtMoTa=findViewById(R.id.edtMoTa);
        edtNgayCapNhat=findViewById(R.id.edtNgayCapNhat);
        btnThemSanPham = findViewById(R.id.btnThemSanPham);


    }


    class ThemSanPhamTask extends AsyncTask<SanPham, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue() == true) {
                Toast.makeText(ThemSpActivity.this, "Thêm sản phẩm thành công!!", Toast.LENGTH_LONG).show();

                edtTenSanPham.setText("");
                edtMaNCC.setText("");
                edtMaLoai.setText("");
                edtSoLuong.setText("");
                edtAnhBia.setText("");
                edtBaoHanh.setText("");
                edtDonGia.setText("");
                edtMoTa.setText("");
                edtNgayCapNhat.setText("");


            } else {
                Toast.makeText(ThemSpActivity.this, "Thêm sản phẩm thất bại!!", Toast.LENGTH_LONG).show();
                edtTenSanPham.setText("");
                edtMaNCC.setText("");
                edtMaLoai.setText("");
                edtSoLuong.setText("");
                edtAnhBia.setText("");
                edtBaoHanh.setText("");
                edtDonGia.setText("");
                edtMoTa.setText("");
                edtNgayCapNhat.setText("");

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(SanPham... sanPhams) {
            try {
                String tdg = "Khong co";
                SanPham sp = sanPhams[0];
                String param = "tenSp=" +URLEncoder.encode(sp.getTenSp())+
                        "&maNcc=" +sp.getMaNcc()+
                        "&maLoai=" +sp.getMaLoai()+
                        "&soLuongTon=" + sp.getSoLuongTon() +
                        "&anhBia=" +URLEncoder.encode(sp.getAnhBia())  +
                        "&giaBan=" + sp.getGiaBan() +
                        "&baoHanh=" +URLEncoder.encode( sp.getBaoHanh())  +
                        "&moTa=" +URLEncoder.encode(sp.getMoTa()) +
                        "&ngayCapNhat=" +URLEncoder.encode(sp.getNgayCapNhat())+
                        "&slBanRa=0"+
                        "&tongDanhGia=" +URLEncoder.encode(tdg);
                //http://tripletstore.somee.com/api/sanpham/?tenSp=123&maNcc=1&maLoai=2&soLuongTon=100&anhBia=100.jpg&giaBan=100000&baoHanh=12 thabsng&moTa=123&ngayCapNhat=5/12/2018&slBanRa=0&tongDanhGia=5 sao
                //chac sai o thằng Ngaycapnhat, t de datetime trên web, dưới này là string, tí chạy app nếu lỗi nữa thì để t sửa lại web. chạy đi, lỗi kêu t
                URL url = new URL("http://tripletstore.somee.com/api/sanpham/?" + param);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                StringBuilder builder = new StringBuilder();
                String line = null;
                InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                boolean kq = builder.toString().contains("true");
                return kq;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("MYTAG1", "error: " + e.toString() );
            }
            return false;
        }
    }
}
