package com.example.thanh.apphtlshop;

import android.content.Intent;
import android.os.AsyncTask;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thanh.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvDanhSachSanPham;
    ArrayAdapter<SanPham> sanPhamArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        lvDanhSachSanPham = findViewById(R.id.lvSanPham);
        sanPhamArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvDanhSachSanPham.setAdapter(sanPhamArrayAdapter);
        DanhSachSanPhamTask danhSachSanPhamTask = new DanhSachSanPhamTask();
        danhSachSanPhamTask.execute();
    }

    private void addEvents() {


    }

    class DanhSachSanPhamTask extends AsyncTask<Void, Void, ArrayList<SanPham>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<SanPham> sanPhams) {
            super.onPostExecute(sanPhams);
            sanPhamArrayAdapter.clear();
            sanPhamArrayAdapter.addAll(sanPhams);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<SanPham> doInBackground(Void... voids) {
            ArrayList<SanPham> dsSanPham = new ArrayList<>();
            try {
                URL url = new URL("http://www.tripletstore.somee.com/api/sanpham");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                JSONArray jsonArray = new JSONArray(builder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SanPham sp = new SanPham();
                    sp.setTenSp(jsonObject.getString("tensp"));
                    sp.setMaSp(jsonObject.getInt("masp"));
                    sp.setAnhBia(jsonObject.getString("anhbia"));
                    sp.setBaoHanh(jsonObject.getString("baohanh"));
                    sp.setGiaBan(jsonObject.getString("giaban"));
                    sp.setMaLoai(jsonObject.getInt("maloai"));
                    sp.setMaNcc(jsonObject.getInt("mancc"));
                    sp.setTongDanhGia(jsonObject.getString("TongDanhGia"));
                    sp.setNgayCapNhat(jsonObject.getString("ngaycapnhat"));
                    sp.setSoLuongTon(jsonObject.getInt("slbanra"));
                    sp.setMoTa(jsonObject.getString("mota"));
                    sp.setSlBanRa(jsonObject.getInt("slbanra"));
                    dsSanPham.add(sp);
                }
                br.close();
                isr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dsSanPham;
        }
    }
}
