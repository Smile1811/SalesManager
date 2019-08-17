package com.duykhanh.salesmanager.View.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.duykhanh.salesmanager.View.IndexActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    private TextView txtForgotPass;
    private EditText edtEmail, edtPassword;
    private SignInButton btnLoginGoogle;
    private Button btnSignUp, btnLogin;
    private ProgressDialog pDialog;
    private GoogleApiClient apiClient;
    public static int REQUEST_CODE_LOGIN_GOOGLE = 3;
    private FirebaseAuth auth;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo chứng thực Firebase Auth
        auth = FirebaseAuth.getInstance();
        // end Khởi tạo chứng thực Firebase Auth

        pDialog = new ProgressDialog(this);
//        auth.signOut();

        // Ánh xạ
        MapLogin();

        // Gán sự kiện
        txtForgotPass.setOnClickListener(this);
        btnLoginGoogle.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // Cấu hình Đăng nhập Google cho ứng dụng -> yêu cầu dữ liệu người dùng -> xây dựng clien
        CreateClienLoginGoogle();

    }

    // Ánh xạ giao diện người dùng từ xml
    public void MapLogin() {
        txtForgotPass = findViewById(R.id.txtForgot);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLoginGoogle = findViewById(R.id.sign_in_button);
        btnLoginGoogle.setSize(SignInButton.SIZE_WIDE);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
    }
    // end // Ánh xạ giao diện người dùng từ xml

    //Khởi tạo client cho đăng nhập google
    private void CreateClienLoginGoogle() {
        GoogleSignInOptions signInOp = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOp)
                .build();
    }
    //end Khởi tạo client cho đăng nhập google

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(this);
    }

    // Mở form đăng nhập google và sau đó chạy onActivityResult
    private void LoginGoogle(GoogleApiClient apiClient) {
        Intent iLoginGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iLoginGoogle, REQUEST_CODE_LOGIN_GOOGLE);
    }
    // end Mở form đăng nhập google và sau đó chạy onActivityResult

    // Lấy tokenId đã đăng nhập bằng google để dăng nhập trên firebase
    private void AuthenticationLoginGoogle(String tokenID) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID, null);
        // dăng nhập thành công sẽ gọi hàm onAUthStateChanged đã đăng ký ở onStart()
        auth.signInWithCredential(authCredential);
    }
    // end Lấy tokenId đã đăng nhập bằng google để dăng nhập trên firebase

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN_GOOGLE) {     //  xác thực click button google
            if (resultCode == RESULT_OK) {                  //  form đăng nhập google đã tắt hay chưa

                // Lấy kết quả đăng nhập google
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                // Lấy tài khoản người dùng đăng nhập
                GoogleSignInAccount account = signInResult.getSignInAccount();

                // Lấy tokenID của người dùng
                String tokenID = account.getIdToken();
                AuthenticationLoginGoogle(tokenID);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                LoginGoogle(apiClient);
                break;
            case R.id.btnSignUp:
                Intent iSignUp = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(iSignUp);
                break;
            case R.id.btnLogin:
                Login();
                break;
            case R.id.txtForgot:
                Intent iForGot = new Intent(LoginActivity.this, RessetPasswordActivity.class);
                startActivity(iForGot);
                break;
        }
    }

    // Sự kiện đăng nhập với tài khoản được đăng ký bởi người dùng
    private void Login() {
        pDialog.setMessage(getString(R.string.string_loading));
        pDialog.setIndeterminate(true);
        pDialog.show();

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String input = getString(R.string.string_input_err);
        boolean checkEmail = CheckEmail(email);

        // // Validation form nhập
        if (email.length() == 0) {
            input += " " + getString(R.string.string_email);
            pDialog.dismiss();
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        }else if(!checkEmail){
            pDialog.dismiss();
            edtEmail.setError("example@example");
            Toast.makeText(this,getString(R.string.string_invalid_email), Toast.LENGTH_SHORT).show();
        }
        else if (password.length() == 0) {
            input += " " + getString(R.string.string_password);
            pDialog.dismiss();
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
        }
        else {
            // phương thức
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pDialog.dismiss();
                        finish();
                        Log.d(TAG, "signInWithEmail:success ");
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, task.getException().getMessage());
                    }
                }
            });
        }
    }
    // end Sự kiện đăng nhập với tài khoản được đăng ký bởi người dùng

    // Sự kiện kiểm tra xem người dùng đã đăng nhập thành công, thất bại, đăng xuất
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        // Lấy user vừa đăng nhập thành công
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Intent iIndex = new Intent(LoginActivity.this, IndexActivity.class);
            startActivity(iIndex);
            finish();
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        } else {

        }
    }
    //end Sự kiện kiểm tra xem người dùng đã đăng nhập thành công, thất bại, đăng xuất

    // Kiểm tra định dạng email hợp lệ hay không
        private boolean CheckEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    // end Kiểm tra định dạng email hợp lệ hay không
}
