package com.duykhanh.salesmanager.Controller;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.salesmanager.Adapter.AdapterRecyclerProduct;
import com.duykhanh.salesmanager.Controller.Interfaces.ProductInterface;
import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    Context context;
    AdapterRecyclerProduct adapterRecyclerProduct;
    ProductModel productModel;

    public ProductController(Context context) {
        this.context = context;
        productModel = new ProductModel();

    }

    public void getListProductController(RecyclerView recyclerProduct) {
        final List<ProductModel> productModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerProduct.setLayoutManager(layoutManager);
        adapterRecyclerProduct = new AdapterRecyclerProduct(context,productModelList, R.layout.layout_custom_list_product);
        recyclerProduct.setAdapter(adapterRecyclerProduct);

        final ProductInterface productInterface = new ProductInterface() {
            @Override
            public void getListProductModel(ProductModel productModel) {
                productModelList.add(productModel);
                adapterRecyclerProduct.notifyDataSetChanged();
            }
        };
        productModel.getListProduct(productInterface);
    }

}
