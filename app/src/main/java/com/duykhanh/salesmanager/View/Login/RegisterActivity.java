package com.duykhanh.salesmanager.View.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtRegisterBack;
    private EditText edtEmailRegister, edtPasswordRegister, edtConfirmPassword;
    private Button btnRegister;
    private ProgressDialog pDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Khởi tạo chứng thực Firebase Auth
        auth = FirebaseAuth.getInstance();
        pDialog = new ProgressDialog(this);

        MapRegister();

        txtRegisterBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    public void MapRegister() {
        txtRegisterBack = findViewById(R.id.txtRegisterBack);
        edtEmailRegister = findViewById(R.id.edtEmailRegister);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        edtConfirmPassword = findViewById(R.id.edtCofirmPass);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRegisterBack:
                Intent iBackLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                iBackLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iBackLogin);
                break;
            case R.id.btnRegister:
                RegisterAccount();
                break;
        }
    }

    public void RegisterAccount() {
        pDialog.setMessage(getString(R.string.string_loading));
        pDialog.setIndeterminate(true);
//        pDialog.setProgressDrawable(getResources().getDrawable(R.drawable.circle));
        pDialog.show();

        String email = edtEmailRegister.getText().toString();
        String password = edtPasswordRegister.getText().toString();
        String confirmPass = edtConfirmPassword.getText().toString();
        String string_input = getString(R.string.string_input_err);
        boolean checkEmail = CheckEmail(email);

        // Validation form nhập
        if (email.trim().length() == 0) {
            string_input += " " + getString(R.string.string_email);
            pDialog.dismiss();
            Toast.makeText(this, string_input, Toast.LENGTH_SHORT).show();
        }
        else if(!checkEmail){
            pDialog.dismiss();
            edtEmailRegister.setError("example@example.com");
            Toast.makeText(this, getString(R.string.string_invalid_email), Toast.LENGTH_SHORT).show();
        }
        else if (password.trim().length() == 0) {
            string_input += " " + getString(R.string.string_password);
            pDialog.dismiss();
            Toast.makeText(this, " " + string_input, Toast.LENGTH_SHORT).show();
        } else if (!confirmPass.equals(password)) {
            pDialog.dismiss();
            Toast.makeText(this,getString(R.string.string_confirm_password), Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pDialog.dismiss();
                        finish();
                        Toast.makeText(RegisterActivity.this, getString(R.string.string_registed_success), Toast.LENGTH_SHORT).show();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("test", " " + task.getException());
                    }
                }
            });
        }
    }

    // Kiểm tra định dạng email
    private Boolean CheckEmail(String email){
         return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //end Kiểm tra định dạng email


}
