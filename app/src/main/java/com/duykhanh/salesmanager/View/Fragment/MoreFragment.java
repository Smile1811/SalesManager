package com.duykhanh.salesmanager.View.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.Login.LoginActivity;
import com.duykhanh.salesmanager.View.crud.UpdateUserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class MoreFragment extends Fragment implements View.OnClickListener {
    private CardView cardUser, cardSignOut;
    private LinearLayout lnProdcutIntroction, lnRulesOfUser, lnPrivacyPolicy, lnSupport, lnVersion;
    private CircleImageView imgUser;
    private TextView txtNameUser, txtEmail;
    private View view;
    String name = null;
    Uri photoUrl;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);
        MapMore();
        cardUser.setOnClickListener(this);
        cardSignOut.setOnClickListener(this);
        lnProdcutIntroction.setOnClickListener(this);
        lnRulesOfUser.setOnClickListener(this);

        lnPrivacyPolicy.setOnClickListener(this);
        lnSupport.setOnClickListener(this);
        lnVersion.setOnClickListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfomationUser();

    }

    private void getInfomationUser() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.getDisplayName();
            String email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            if (name == null || name.equals("")) {
                txtNameUser.setText("Chưa có thông tin");
            } else {
                txtNameUser.setText(name);
            }
            txtEmail.setText(email);

            for (UserInfo profile : user.getProviderData()) {
                Picasso.with(getActivity()).load(profile.getPhotoUrl()).into(imgUser);
            }
        }
    }


    private void MapMore() {
        cardUser = view.findViewById(R.id.cardUser);
        cardSignOut = view.findViewById(R.id.cardSignOut);
        lnProdcutIntroction = view.findViewById(R.id.lnIntroducePr);
        lnRulesOfUser = view.findViewById(R.id.lnRulesOfUser);
        lnPrivacyPolicy = view.findViewById(R.id.lnPrivacyPolicy);
        lnSupport = view.findViewById(R.id.lnSupport);
        lnVersion = view.findViewById(R.id.lnVersion);
        txtEmail = view.findViewById(R.id.txtEmailDetail);
        txtNameUser = view.findViewById(R.id.txtUserDetail);
        imgUser = view.findViewById(R.id.imgUserget);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.cardUser:
                startActivity(new Intent(getContext(), UpdateUserActivity.class));
                break;
            case R.id.cardSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent iLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(iLogin);
                getActivity().finish();
                break;
            case R.id.lnIntroducePr:
                fragment = new FragmentProductIntroduction();
                loadFragmentIntroduce(fragment);
                break;
            case R.id.lnRulesOfUser:
                fragment = new FragmentUsageRules();
                loadFragmentIntroduce(fragment);
                break;
            case R.id.lnPrivacyPolicy:
                fragment = new FragmentPrivacyPolicy();
                loadFragmentIntroduce(fragment);
                break;
            case R.id.lnSupport:
                fragment = new FragmentSupport();
                loadFragmentIntroduce(fragment);
                break;
        }
    }

    private void loadFragmentIntroduce(Fragment fragment){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
