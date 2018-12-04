package com.example.thanh.apphtlshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thanh.model.SanPham;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvSanPham;
    private ArrayAdapter<SanPham> arrSanPham;
    private ArrayList<SanPham> listSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
    }

    private void addControls() {
        lvSanPham = findViewById(R.id.lvSanPham);
        listSanPham = new ArrayList<>();
        arrSanPham = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listSanPham);
        lvSanPham.setAdapter(arrSanPham);

    }
}
