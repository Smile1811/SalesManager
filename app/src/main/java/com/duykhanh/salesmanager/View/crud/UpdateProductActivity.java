package com.duykhanh.salesmanager.View.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.ListProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpdateProductActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgUpdatePr, imgUpdateProduct, imgBackUpdateProduct, imgCheckUpdateProduct;
    private EditText edtNameProductUpdate, edtCodeProductUpdate, edtDiscribeUpdate, edtRetailPriceUpdate, edtWholesalePricesUpdate, edtEntryPriceUpdate;
    private List<Uri> imageProductUp;
    private ProductModel productModel;
    private DatabaseReference nodeRootUp, nodeProdcuUp, nodeImageProductUp;
    private String key, keyImage;
    private ProgressDialog progressDialog;
    int RESULT_IMAGE_UPDATE_ONE = 111;
    Intent iDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        MapUpdateProduct();

        imgBackUpdateProduct.setOnClickListener(this);
        imgCheckUpdateProduct.setOnClickListener(this);
        imgUpdateProduct.setOnClickListener(this);
        imgUpdatePr.setOnClickListener(this);

        imageProductUp = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        iDetail = getIntent();

        SetUpdateProduct();

    }

    private void MapUpdateProduct() {
        view = findViewById(R.id.iclUpdateProduct);
        imgBackUpdateProduct = view.findViewById(R.id.imgBackUpdateProduct);
        imgCheckUpdateProduct = view.findViewById(R.id.imgCheckUpdateProduct);
        imgUpdateProduct = findViewById(R.id.imgImageUpdateProduct);
        imgUpdatePr = findViewById(R.id.imgImageUpdatePr);
        edtNameProductUpdate = findViewById(R.id.edtNameProductUpdate);
        edtCodeProductUpdate = findViewById(R.id.edtCodeProductUpdate);
        edtDiscribeUpdate = findViewById(R.id.edtDiscribeUpdate);
        edtRetailPriceUpdate = findViewById(R.id.edtRetailPriceUpdate);
        edtWholesalePricesUpdate = findViewById(R.id.edtWholesalePricesUpdate);
        edtEntryPriceUpdate = findViewById(R.id.edtEntryPriceUpdate);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackUpdateProduct:
                finish();
                break;
            case R.id.imgCheckUpdateProduct:
                progressDialog.setMessage("Đang sửa thông tin sản phẩm");
                progressDialog.show();
                UpdateProduct();
                break;
            case R.id.imgImageUpdateProduct:
                ChooseImageUpdate();
                break;
        }
    }

    private void UpdateProduct() {
        String updateNameUp = edtNameProductUpdate.getText().toString();
        String updateCodeUp = edtCodeProductUpdate.getText().toString();
        String updateDiscribeUp = edtDiscribeUpdate.getText().toString();
        long updateRetailPriceUp = Long.parseLong(edtRetailPriceUpdate.getText().toString());
        long updateWholesalePricesUp = Long.parseLong(edtWholesalePricesUpdate.getText().toString());
        long updateEntryPriceUp = Long.parseLong(edtEntryPriceUpdate.getText().toString());

        productModel = new ProductModel();
        productModel.setMasanpham(updateCodeUp);
        productModel.setTensanpham(updateNameUp);
        productModel.setMota(updateDiscribeUp);
        productModel.setGiabanle(updateRetailPriceUp);
        productModel.setGiabanbuon(updateWholesalePricesUp);
        productModel.setGianhap(updateEntryPriceUp);
        productModel.setKeyImage(keyImage);
        productModel.setKey(key);

        nodeRootUp = FirebaseDatabase.getInstance().getReference();
        nodeProdcuUp = nodeRootUp.child(getString(R.string.db_product));
        nodeImageProductUp = nodeRootUp.child(getString(R.string.db_image_product));


        //
        nodeProdcuUp.child(key).setValue(productModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    for (Uri imageProductUp : imageProductUp) {
                        FirebaseStorage.getInstance().getReference().child("product").child("" + imageProductUp.getLastPathSegment()).putFile(imageProductUp);
                        nodeImageProductUp.child(key).child(keyImage).setValue(imageProductUp.getLastPathSegment()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProductActivity.this, getString(R.string.string_update_success), Toast.LENGTH_SHORT).show();
                                    Intent iList = new Intent(UpdateProductActivity.this, ListProductActivity.class);
                                    iList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(iList);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateProductActivity.this, getString(R.string.string_update_failded), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    Toast.makeText(UpdateProductActivity.this, getString(R.string.string_update_success), Toast.LENGTH_SHORT).show();
                    Intent iList = new Intent(UpdateProductActivity.this, ListProductActivity.class);
                    iList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iList);
                    finish();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(UpdateProductActivity.this, getString(R.string.string_update_failded), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void SetUpdateProduct() {
        String nameProduct = iDetail.getStringExtra("nameproduct");
        String codeproduct = iDetail.getStringExtra("codeproduct");
        String discribe = iDetail.getStringExtra("discribe");
        long retailprice = iDetail.getLongExtra("retailprice", 0);
        long wholeasaleprice = iDetail.getLongExtra("wholeasaleprice", 0);
        long entryprice = iDetail.getLongExtra("entryprice", 0);
        String image = iDetail.getStringExtra("image");
        key = iDetail.getStringExtra("key");
        keyImage = iDetail.getStringExtra("keyimage");

        edtNameProductUpdate.setText(nameProduct);
        edtCodeProductUpdate.setText(codeproduct);
        edtDiscribeUpdate.setText(discribe);
        edtRetailPriceUpdate.setText("" + retailprice);
        edtWholesalePricesUpdate.setText("" + wholeasaleprice);
        edtEntryPriceUpdate.setText("" + entryprice);

        // Kiểm tra nếu có ảnh thì tiến hành cập nhật lên firebase storage
        if (image.length() > 0) {
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child("product").child(image);
            long TWO_MEGABYTE = 2048 * 2048;
            storageImage.getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgUpdatePr.setImageBitmap(bitmap);
                }
            });
        }
        // end Kiểm tra nếu có ảnh thì tiến hành cập nhật lên firebase storage
    }

    // Chọn ảnh từ thu viện của điện thoại
    private void ChooseImageUpdate() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình..."), RESULT_IMAGE_UPDATE_ONE);
    }
    // Chọn ảnh từ thu viện của điện thoại

    // get và set hình ảnh được chọn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_IMAGE_UPDATE_ONE) {
                Uri uri = data.getData();
                imgUpdatePr.setImageURI(uri);
                imageProductUp.add(uri);
        }
    }
    // end get và set hình ảnh được chọn
}
