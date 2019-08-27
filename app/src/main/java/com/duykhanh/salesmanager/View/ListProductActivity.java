package com.duykhanh.salesmanager.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.duykhanh.salesmanager.Controller.ProductController;
import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;

public class ListProductActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private ImageView imgBackListProdcut,imgSearchListProduct;
    private ProductController productController;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        MapListProduct();

        recyclerView = findViewById(R.id.recyListProduct);
        productController = new ProductController(this);
        productController.getListProductController(recyclerView);

        imgBackListProdcut.setOnClickListener(this);
        imgSearchListProduct.setOnClickListener(this);
    }

    private void MapListProduct() {
        view = findViewById(R.id.iclListProduct);
        imgBackListProdcut = view.findViewById(R.id.imgBackListProdcut);
        imgSearchListProduct = view.findViewById(R.id.imgSearchListProduct);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBackListProdcut:
                finish();
                break;
            case R.id.imgSearchListProduct:
                Toast.makeText(this, "Chức năng đang phát triển", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
