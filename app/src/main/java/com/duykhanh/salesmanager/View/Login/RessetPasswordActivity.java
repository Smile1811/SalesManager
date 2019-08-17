package com.duykhanh.salesmanager.View.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duykhanh.salesmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RessetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtRessetBack;
    private EditText edtEmailResset;
    private Button btnSendEmailResset;
    private ProgressDialog pDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resset_password);

        // Khởi tạo chứng thực Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        // end Khởi tạo chứng thực Firebase Auth

        pDialog = new ProgressDialog(this);

        //Ánh xạ
        MapReset();

        // Gán sự kiện
        edtEmailResset.setFocusableInTouchMode(false);
        edtEmailResset.clearFocus();
        edtEmailResset.setFocusableInTouchMode(true);
        txtRessetBack.setOnClickListener(this);
        btnSendEmailResset.setOnClickListener(this);
    }

    private void MapReset() {
        txtRessetBack = findViewById(R.id.txtRessetBack);
        edtEmailResset = findViewById(R.id.edtEmailResset);
        btnSendEmailResset = findViewById(R.id.btnResset);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResset:
                pDialog.setMessage(getString(R.string.string_loading));
                pDialog.setIndeterminate(true);
                pDialog.show();

                String email = edtEmailResset.getText().toString();
                boolean checkEmail = CheckEmail(email);
                if (email.length() == 0) {
                    pDialog.dismiss();
                    Toast.makeText(this, getString(R.string.string_input_err) + " " + getString(R.string.string_email), Toast.LENGTH_SHORT).show();
                } else if (!checkEmail) {
                    pDialog.dismiss();
                    edtEmailResset.setError("exemple@exemple.com");
                    Toast.makeText(this, getString(R.string.string_invalid_email), Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pDialog.dismiss();
                                Toast.makeText(RessetPasswordActivity.this, getString(R.string.string_send_email_success), Toast.LENGTH_SHORT).show();
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(RessetPasswordActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                break;
            case R.id.txtRessetBack:
                Intent iReBackLogin = new Intent(RessetPasswordActivity.this, LoginActivity.class);
                iReBackLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iReBackLogin);
                break;
        }
    }

    // Kiểm tra định dạng email hợp lệ hay không
    private boolean CheckEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    // end Kiểm tra định dạng email hợp lệ hay không
}
