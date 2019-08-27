package com.duykhanh.salesmanager.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.crud.AddProductActivity;
import com.duykhanh.salesmanager.View.ListProductActivity;


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
        switch (view.getId()) {
            case R.id.rAddProduct:
                Intent iAddProduct = new Intent(getActivity(), AddProductActivity.class);
                iAddProduct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iAddProduct);
                break;
            case R.id.rListProduct:
                Intent iListProdcut = new Intent(getActivity(), ListProductActivity.class);
                iListProdcut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iListProdcut);
                break;
        }
    }

}
