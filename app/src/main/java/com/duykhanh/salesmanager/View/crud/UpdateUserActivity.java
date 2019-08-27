package com.duykhanh.salesmanager.View.crud;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.duykhanh.salesmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgBackUpdateuser, imgCheckUpdateUser, imgChoooImageUpdate, imgShowUp;
    private EditText edtNameuser;
    private FirebaseUser user;
    private List<Uri> imgUser;
    Uri uri;
    int REQUEST_IMAGE_UPDATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        MapUpdateUser();

        imgBackUpdateuser.setOnClickListener(this);
        imgCheckUpdateUser.setOnClickListener(this);
        imgChoooImageUpdate.setOnClickListener(this);

        imgUser = new ArrayList<>();

    }

    private void MapUpdateUser() {
        view = findViewById(R.id.iclUpdateUser);
        imgBackUpdateuser = view.findViewById(R.id.imgBackUpdateUser);
        imgCheckUpdateUser = view.findViewById(R.id.imgCheckUpdateUser);
        imgChoooImageUpdate = findViewById(R.id.imgChooseImageUp);
        imgShowUp = findViewById(R.id.imgshowUp);
        edtNameuser = findViewById(R.id.edtNameUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackUpdateUser:
                finish();
                break;
            case R.id.imgCheckUpdateUser:
                UpdateUser();
                break;
            case R.id.imgChooseImageUp:
                ChooseImageUser(REQUEST_IMAGE_UPDATE);
                break;

        }
    }

    private void UpdateUser() {
        String username = edtNameuser.getText().toString();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (username.equals("")) {
            Toast.makeText(this, "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
        } else {
            // Update profile cho tài khoản đăng nhập bao gồm tên và hình ảnh
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .setPhotoUri(uri)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("check", "User profile updated.");
                                finish();
                            }
                        }
                    });
            // end Update profile cho tài khoản đăng nhập bao gồm tên và hình
        }

    }

    // Chọn hình ảnh từ thư viện của điện thoại
    private void ChooseImageUser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình...."), REQUEST_IMAGE_UPDATE);
    }
    // end Chọn hình ảnh từ thư viện của điện thoại

    //  kiểm tra giá trị trả về và set hình ảnh vừa chọn vào ImageView trong layout
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_UPDATE) {
            uri = data.getData();
            imgShowUp.setImageURI(uri);
            // truyền hình ảnh vào list<Uri>
            imgUser.add(uri);
        }
    }
    //  end kiểm tra giá trị trả về và set hình ảnh vừa chọn vào ImageView trong layout
}
