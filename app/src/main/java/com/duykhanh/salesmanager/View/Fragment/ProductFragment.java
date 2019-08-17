package com.duykhanh.salesmanager.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.AddProductActivity;


public class ProductFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView txtToolbar;
    private RelativeLayout cardAddProduct, cardListProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product, container, false);
        MapProduct();
        txtToolbar.setText(getString(R.string.title_san_pham));
        cardAddProduct.setOnClickListener(this);
        cardListProduct.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void MapProduct() {
        cardAddProduct = view.findViewById(R.id.rAddProduct);
        cardListProduct = view.findViewById(R.id.rListProduct);
        txtToolbar = getActivity().findViewById(R.id.txtToolbar);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.rAddProduct:
                Intent iAddProduct = new Intent(getActivity(), AddProductActivity.class);
                startActivity(iAddProduct);
                break;
            case R.id.rListProduct:

                break;
        }
    }

    // load Fragment
    private void loadFragmentProduct(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    // end load Fragment

}
