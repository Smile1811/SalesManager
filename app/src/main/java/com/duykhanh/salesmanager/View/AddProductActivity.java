package com.duykhanh.salesmanager.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.R;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgBack, imgCheck;
    private RelativeLayout btnWare, btnClassify;
    private SwitchCompat btnTax, btnAllowToSell;
    private TextView txtAddProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        MapAddProduct();

        imgBack.setOnClickListener(this);
        imgCheck.setOnClickListener(this);
        btnWare.setOnClickListener(this);
        btnClassify.setOnClickListener(this);
        btnTax.setOnClickListener(this);
        btnAllowToSell.setOnClickListener(this);
        txtAddProperties.setOnClickListener(this);
        btnAllowToSell.setChecked(true);

        CheckSwich();
    }

    // Ánh xạ view
    private void MapAddProduct() {
        view = findViewById(R.id.iclAddProduct);
        imgBack = view.findViewById(R.id.imgBackProduct);
        imgCheck = view.findViewById(R.id.imgCheckProduct);
        btnWare = findViewById(R.id.rWare);
        btnClassify = findViewById(R.id.rClassify);
        btnTax = findViewById(R.id.swtTax);
        btnAllowToSell = findViewById(R.id.swtAlts);
        txtAddProperties = findViewById(R.id.txtAddProperties);

    }
    // end Ánh xạ view

    // set sự kiện onclick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackProduct:
                finish();
                break;
            case R.id.imgCheckProduct:
                Toast.makeText(this, "ok smile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rWare:
                Intent iWare = new Intent(AddProductActivity.this, WareHouseActivity.class);
                startActivity(iWare);
                break;
            case R.id.rClassify:
                Toast.makeText(this, "Pha loai", Toast.LENGTH_SHORT).show();
                break;
            case R.id.swtAlts:

                break;
            case R.id.swtTax:

                break;
            case R.id.txtAddProperties:
                Toast.makeText(this, "Them thuoc tinh", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    // end set sự kiện onclick

    private void CheckSwich() {
        btnAllowToSell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(AddProductActivity.this, "Cho phep ban", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddProductActivity.this, "Khong cho phep ban", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnTax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Toast.makeText(AddProductActivity.this, "Ap dung thue", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddProductActivity.this, "Khong ap thue", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
