package com.duykhanh.salesmanager.View.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.ListProductActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgProductDetail, imgBackProductDetail, imgCheckProductDetail;
    private TextView txtNameDetailPr, txtCodeDetailPr, txtDiscribeDetail, txtRetailPriceDetailPr,
            txtWholeasalePriceDetailPr, txtEntryPriceDetailPr, txtDeleteProduct;
    private ProductModel productModel;
    private String imageName;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        MapDetailProduct();

        imgBackProductDetail.setOnClickListener(this);
        imgCheckProductDetail.setOnClickListener(this);
        txtDeleteProduct.setOnClickListener(this);

        // Lấy dữ liệu được gử từ AdapterRecyclerview
        productModel = getIntent().getParcelableExtra("SANPHAM");

        SetDetailProduct();

        databaseReference = FirebaseDatabase.getInstance().getReference();


    }

    // set dữ liệu từ productmodel vào text
    private void SetDetailProduct() {
        txtNameDetailPr.setText(productModel.getTensanpham());
        txtCodeDetailPr.setText(productModel.getMasanpham());
        txtDiscribeDetail.setText(productModel.getMota());
        txtRetailPriceDetailPr.setText("" + convertTextDetail(String.valueOf(productModel.getGiabanle())) + "đ");
        txtWholeasalePriceDetailPr.setText("" + convertTextDetail(String.valueOf(productModel.getGiabanbuon())) + "đ");
        txtEntryPriceDetailPr.setText("" + convertTextDetail(String.valueOf(productModel.getGianhap())) + "đ");
        imageName = productModel.getImageProduct().get(0);

        if (productModel.getImageProduct().size() > 0) {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child("product").child(imageName);
            long TWO_MEGABYTE = 2048 * 2048;
            storageImage.getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgProductDetail.setImageBitmap(bitmap);
                }
            });
        }
    }
    // end set dữ liệu từ productmodel vào text

    // Ánh xạ
    private void MapDetailProduct() {
        view = findViewById(R.id.iclAddProduct);
        imgProductDetail = findViewById(R.id.imgProductDetail);
        txtNameDetailPr = findViewById(R.id.txtNameDetailPr);
        txtCodeDetailPr = findViewById(R.id.txtCodeDetailPr);
        txtDiscribeDetail = findViewById(R.id.txtDiscribeDetail);
        txtRetailPriceDetailPr = findViewById(R.id.txtRetailPriceDetailPr);
        txtWholeasalePriceDetailPr = findViewById(R.id.txtWholeasalePriceDetailPr);
        txtEntryPriceDetailPr = findViewById(R.id.txtEntryPriceDetailPr);
        txtDeleteProduct = findViewById(R.id.txtDeleteProduct);

        imgCheckProductDetail = view.findViewById(R.id.imgCheckProductDetail);
        imgBackProductDetail = view.findViewById(R.id.imgBackProductDetail);

    }
    // end Ánh xạ

    private String convertTextDetail(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = stringBuilder.length(); i > 0; i -= 3) {
            stringBuilder.insert(i, " ");
        }
        return stringBuilder.toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackProductDetail:

                finish();
                break;
            case R.id.imgCheckProductDetail:
                Toast.makeText(this, "" + productModel.getGiabanle(), Toast.LENGTH_SHORT).show();
                Intent iUpdateProduct = new Intent(DetailProductActivity.this, UpdateProductActivity.class);
                iUpdateProduct.putExtra("nameproduct", productModel.getTensanpham());
                iUpdateProduct.putExtra("codeproduct", productModel.getMasanpham());
                iUpdateProduct.putExtra("discribe", productModel.getMota());
                iUpdateProduct.putExtra("retailprice", productModel.getGiabanle());
                iUpdateProduct.putExtra("wholeasaleprice", productModel.getGiabanbuon());
                iUpdateProduct.putExtra("entryprice", productModel.getGianhap());
                iUpdateProduct.putExtra("image", imageName);
                iUpdateProduct.putExtra("key", productModel.getKey());
                iUpdateProduct.putExtra("keyimage", productModel.getKeyImage());
                iUpdateProduct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iUpdateProduct);
                break;
            case R.id.txtDeleteProduct:
                databaseReference.child(getString(R.string.db_product)).child(productModel.getKey()).removeValue();
                databaseReference.child(getString(R.string.db_image_product)).child(productModel.getKey()).removeValue();
                Intent iDetail = new Intent(this, ListProductActivity.class);
                iDetail.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iDetail);
                break;
        }
    }
}
