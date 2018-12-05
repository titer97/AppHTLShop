package com.example.thanh.apphtlshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thanh.model.SanPham;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    EditText txtNhapMaSp,txtTenSanPham2,txtSoLuong2,txtDonGia2,txtBaoHanh2,txtTenNcc2,txtDanhGia2;
    Button btnXemChiTiet2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtNhapMaSp=findViewById(R.id.edtNhapMaSp);
        txtTenSanPham2=findViewById(R.id.edtTenSanPham2);
        txtSoLuong2=findViewById(R.id.edtSoLuong2);
        txtDonGia2=findViewById(R.id.edtDonGia2);
        txtBaoHanh2=findViewById(R.id.edtBaoHanh2);
        txtTenNcc2=findViewById(R.id.edtTenNcc2);
        txtDanhGia2=findViewById(R.id.edtDanhGia2);
        btnXemChiTiet2=findViewById(R.id.btnXemChiTiet2);
    }

    private void addEvents() {
        btnXemChiTiet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChiTietSanPham();
            }
        });



    }

    private void xuLyChiTietSanPham() {
        ChiTietSanPhamTask task = new ChiTietSanPhamTask();
        task.execute(txtNhapMaSp.getText().toString());
    }

    class ChiTietSanPhamTask extends AsyncTask<String, Void, SanPham> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(SanPham sanPham) {
            super.onPostExecute(sanPham);
            if (sanPham != null) {
                txtTenSanPham2.setText(sanPham.getTenSp());
                txtSoLuong2.setText(sanPham.getSoLuongTon()+"");
                txtDonGia2.setText(sanPham.getGiaBan()+"");
                txtBaoHanh2.setText(sanPham.getBaoHanh());
                txtTenNcc2.setText(sanPham.getMaNcc()+"");
                txtDanhGia2.setText(sanPham.getTongDanhGia());
            } else {
                Toast.makeText(ChiTietSanPhamActivity.this, "Không tìm thấy", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected SanPham doInBackground(String... strings) {
            try {
                URL url = new URL("http://www.tripletstore.somee.com/api/sanpham/" + strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }

                JSONObject jsonObject = new JSONObject(builder.toString());
                SanPham sp = new SanPham();
                sp.setMaLoai(jsonObject.getInt("maloai"));
                sp.setTenSp(jsonObject.getString("tensp"));
                sp.setSoLuongTon(jsonObject.getInt("soluongton"));
                sp.setGiaBan((jsonObject.getInt("giaban")));
                sp.setBaoHanh(jsonObject.getString("baohanh"));
                sp.setMaNcc(jsonObject.getInt("mancc"));
                sp.setTongDanhGia(jsonObject.getString("TongDanhGia"));
                Log.d("MYTAG", "sp: " + sp.getTenSp());
                return sp;
            } catch (Exception e) {
                Log.e("MYTAG", "error: " + e.toString() );
            }
            return null;
        }
    }

}
