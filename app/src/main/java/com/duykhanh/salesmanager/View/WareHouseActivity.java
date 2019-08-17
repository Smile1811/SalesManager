package com.duykhanh.salesmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.duykhanh.salesmanager.R;

public class WareHouseActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgBackWare, imgCheckWare;
    private EditText edtUnitCount, edtMass, edtUnit, edtOrInvent, edtCostPrice;
    private Spinner spinner;
    private String[] items;
    private ArrayAdapter adapterSpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_house);

        MapWareHouse();
        SpinerUnit();
        setTextWareHouseFirst();
        imgBackWare.setOnClickListener(this);
        imgCheckWare.setOnClickListener(this);


    }

    // Đưa vào giá trị mặc định của các text khi khởi tạo kho hàng
    private void setTextWareHouseFirst(){
        edtMass.setText("0");
        edtOrInvent.setText("0");
        edtCostPrice.setText("0");
    }
    // end Đưa vào giá trị mặc định của các text khi khởi tạo kho hàng

    // Tạo ra 1 spinner có thể tùy chọn đơn vị khối lượng áp dụng cho hàng hóa
    private void SpinerUnit(){
        items = new String[]{"g", "kg"};
        adapterSpiner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpiner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(WareHouseActivity.this, spinner.getSelectedItem().toString()
                        , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    // end Tạo ra 1 spinner có thể tùy chọn đơn vị khối lượng áp dụng cho hàng hóa

    // Ánh xạ
    private void MapWareHouse() {
        view = findViewById(R.id.iclWareHouse);
        imgBackWare = view.findViewById(R.id.imgBackWare);
        imgCheckWare = view.findViewById(R.id.imgCheckWare);
        edtUnitCount = findViewById(R.id.edtUnitCount);
        edtMass = findViewById(R.id.edtMass);
        spinner = findViewById(R.id.spinerWare);
        edtOrInvent = findViewById(R.id.edtOrInvent);
        edtCostPrice = findViewById(R.id.edtCostPrice);
    }
    // end Ánh xạ


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackWare:
                finish();
                break;
            case R.id.imgCheckWare:
                Toast.makeText(this, "Check Ware Ok!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
