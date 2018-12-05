package com.example.thanh.apphtlshop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.thanh.model.SanPham;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TimSpTheoDonGiaActivity extends AppCompatActivity {
    EditText edtDonGiaMin,edtDonGiaMax;
    Button btnXemSpTheoDonGia;
    ListView lvSanPham3;
    ArrayAdapter<SanPham> sanPhamArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_sp_theo_don_gia);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnXemSpTheoDonGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXemSpTheoDonGia();

            }
        });

    }

    private void xuLyXemSpTheoDonGia() {

        DsSanPhamTheoDonGiaTask task = new DsSanPhamTheoDonGiaTask();
        task.execute(edtDonGiaMin.getText().toString(),edtDonGiaMax.getText().toString());

    }

    private void addControls() {
        edtDonGiaMax=findViewById(R.id.edtDonGiaMax);
        edtDonGiaMin=findViewById(R.id.edtDonGiaMin);
        btnXemSpTheoDonGia=findViewById(R.id.btnXemSpTheoDonGia);
        lvSanPham3=findViewById(R.id.lvSanPham3);

        sanPhamArrayAdapter=new ArrayAdapter<>(TimSpTheoDonGiaActivity.this,android.R.layout.simple_list_item_1);
        lvSanPham3.setAdapter(sanPhamArrayAdapter);




    }
    class DsSanPhamTheoDonGiaTask extends AsyncTask<String,Void,ArrayList<SanPham>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sanPhamArrayAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<SanPham> sanPhams) {
            super.onPostExecute(sanPhams);
            sanPhamArrayAdapter.clear();
            sanPhamArrayAdapter.clear();
            sanPhamArrayAdapter.addAll(sanPhams);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected ArrayList<SanPham> doInBackground(String... strings) {
            ArrayList<SanPham> dsSanPham = new ArrayList<>();
            try {
                URL url = new URL("http://tripletstore.somee.com/api/sanpham/?a=" + strings[0] + "&b=" + strings[1]);
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
                    int masp = jsonObject.getInt("masp");
                    String tensp = jsonObject.getString("tensp");
                    int sl = jsonObject.getInt("soluong");
                    int dongia = jsonObject.getInt("dongia");
                    String baohanh=jsonObject.getString("baohanh");
                    SanPham sp = new SanPham();
                    sp.setMaSp(masp);
                    sp.setTenSp(tensp);
                    sp.setSoLuongTon(sl);
                    sp.setGiaBan(dongia);
                    sp.setBaoHanh(baohanh);
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
