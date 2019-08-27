package com.duykhanh.salesmanager.View.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.ListProductActivity;
import com.duykhanh.salesmanager.View.WareHouseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgBack, imgCheck, imgProduct1, imgProduct2;
    private RelativeLayout btnWare, btnClassify;
    private SwitchCompat btnTax, btnAllowToSell;
    private TextView txtAddProperties;
    private EditText edtNameProduct, edtCodeProduct, edtDiscribeProduct, edtRetailPrice, edtWholesalePrices, edtEntryPrice;
    private String codeProductKey,codeImageKey;

    private ProgressDialog progressDialog;

    private List<Uri> imageProduct;
    private ProductModel productModel;
    private DatabaseReference nodeRoot, nodeProduct, nodeImageProduct;

    int RESULT_IMAGE_ONE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        MapAddProduct();
        CheckSwich();
        SetTextPrices();

        imgBack.setOnClickListener(this);
        imgCheck.setOnClickListener(this);
        btnWare.setOnClickListener(this);
        btnClassify.setOnClickListener(this);
        btnTax.setOnClickListener(this);
        btnAllowToSell.setOnClickListener(this);
        txtAddProperties.setOnClickListener(this);
        imgProduct1.setOnClickListener(this);
        imgProduct2.setOnClickListener(this);


        imageProduct = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
    }

    private void SetTextPrices() {
        edtRetailPrice.setText("0");
        edtWholesalePrices.setText("0");
        edtEntryPrice.setText("0");
    }

    // Ánh xạ view
    private void MapAddProduct() {
        //EditText
        edtNameProduct = findViewById(R.id.edtNameProduct);
        edtCodeProduct = findViewById(R.id.edtCodeProduct);
        edtDiscribeProduct = findViewById(R.id.edtDiscribe);
        edtRetailPrice = findViewById(R.id.edtRetailPrice);
        edtWholesalePrices = findViewById(R.id.edtWholesalePrices);
        edtEntryPrice = findViewById(R.id.edtEntryPrice);
        //end EditText

        //Image
        view = findViewById(R.id.iclAddProduct);
        imgBack = view.findViewById(R.id.imgBackProduct);
        imgCheck = view.findViewById(R.id.imgCheckProduct);
        imgProduct1 = findViewById(R.id.imgImageProduct);
        imgProduct2 = findViewById(R.id.imgProductOne);
        //end Image

        //Button
        btnWare = findViewById(R.id.rWare);
        btnClassify = findViewById(R.id.rClassify);
        btnTax = findViewById(R.id.swtTax);
        btnAllowToSell = findViewById(R.id.swtAlts);
        txtAddProperties = findViewById(R.id.txtAddProperties);
        // end Button


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
                progressDialog.setMessage("Đang thêm sản phẩm");
                progressDialog.show();
                AddProduct();
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
            case R.id.imgImageProduct:
                ChooseImageProduct(RESULT_IMAGE_ONE);
                break;
            case R.id.imgProductOne:
                break;
        }
    }
    // end set sự kiện onclick

    // kiểm tra 2 nút switch tương ứng với chức năng cho phép bán và áp thuế
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
    // end kiểm tra 2 nút switch tương ứng với chức năng cho phép bán và áp thuế

    private void AddProduct() {
        String nameProduct = edtNameProduct.getText().toString();
        String codeProduct = edtCodeProduct.getText().toString();
        String discribeProduct = edtDiscribeProduct.getText().toString();
        long retailPrice = Long.parseLong(edtRetailPrice.getText().toString());
        long wholesalePrices = Long.parseLong(edtWholesalePrices.getText().toString());
        long entryPrice = Long.parseLong(edtEntryPrice.getText().toString());

        nodeRoot = FirebaseDatabase.getInstance().getReference();
        nodeProduct = nodeRoot.child(getString(R.string.db_product));
        nodeImageProduct = nodeRoot.child(getString(R.string.db_image_product));
        // Tạo key sản phẩm
        codeProductKey = nodeProduct.push().getKey();
        // end Tạo key sản phẩm

        // Tạo key hình ảnh
        codeImageKey = nodeImageProduct.child(codeProductKey).push().getKey();
        // end Tạo key hình ảnh
        if(nameProduct.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if(codeProduct.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập mã sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if(discribeProduct.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập mô tả về sản phẩm", Toast.LENGTH_SHORT).show();
        }
        else if(edtRetailPrice.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập giá bán lẻ", Toast.LENGTH_SHORT).show();
        }
        else if(edtWholesalePrices.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhập giá bán buôn", Toast.LENGTH_SHORT).show();
        }
        else if(edtEntryPrice.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Vui lòng nhâp giá nhập hàng", Toast.LENGTH_SHORT).show();
        }
        else {
            productModel = new ProductModel();
            productModel.setMasanpham(codeProduct);
            productModel.setTensanpham(nameProduct);
            productModel.setMota(discribeProduct);
            productModel.setGiabanle(retailPrice);
            productModel.setGiabanbuon(wholesalePrices);
            productModel.setGianhap(entryPrice);
            productModel.setKey(codeProductKey);
            productModel.setKeyImage(codeImageKey);

            Toast.makeText(this, productModel.getKey(), Toast.LENGTH_SHORT).show();

            // Tạo mã sản phẩm và mã hình ảnh và truyền vào giá trị
            nodeProduct.child(codeProductKey).setValue(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Lấy tất cả hình ảnh trong List Uri
                        for (Uri imageProduct : imageProduct) {
                            // Đưa hình ảnh vào storage
                            FirebaseStorage.getInstance().getReference().child("product").child("" + imageProduct.getLastPathSegment()).putFile(imageProduct);
                            // Đưa tên key hình ảnh lấy được được vào database
                            nodeImageProduct.child(codeProductKey).child(codeImageKey).setValue(imageProduct.getLastPathSegment()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Intent iList = new Intent(AddProductActivity.this, ListProductActivity.class);
                                        startActivity(iList);
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
        // end Tạo mã sản phẩm và mã hình ảnh và truyền vào giá trị
    }

    // Chọn hình ảnh từ thư viện của điện thoại
    private void ChooseImageProduct(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình...."), RESULT_IMAGE_ONE);
    }
    // end Chọn hình ảnh từ thư viện của điện thoại

    //  kiểm tra giá trị trả về và set hình ảnh vừa chọn vào ImageView trong layout
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE_ONE) {
            Uri uri = data.getData();
            Toast.makeText(this, ""+uri, Toast.LENGTH_SHORT).show();
            imgProduct2.setImageURI(uri);
            // truyền hình ảnh vào list<Uri>
            imageProduct.add(uri);
        }
    }
    //  end kiểm tra giá trị trả về và set hình ảnh vừa chọn vào ImageView trong layout
}
