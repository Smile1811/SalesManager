package com.duykhanh.salesmanager.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duykhanh.salesmanager.Model.ProductModel;
import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.crud.DetailProductActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterRecyclerProduct extends RecyclerView.Adapter<AdapterRecyclerProduct.ViewHolder> {

    List<ProductModel> productModelList;
    Context context;
    int resource;

    public AdapterRecyclerProduct(Context context, List<ProductModel> productModelList, int resource) {
        this.productModelList = productModelList;
        this.resource = resource;
        this.context = context;

    }

    @NonNull
    @Override
    public AdapterRecyclerProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerProduct.ViewHolder holder, final int position) {
        final ProductModel productModel = productModelList.get(position);
        holder.txtNameProduct.setText(productModel.getTensanpham());
        holder.txtCanSell.setText("Giá: " + convertText(String.valueOf(productModel.getGiabanle())) + "đ");
        holder.txtVersionProduct.setText("Mã SP: " + productModel.getMasanpham());

        // Kiểm tra nếu productModel có ảnh -> truy cập thư mục chứa ảnh trên firebase -> lấy ảnh theo tên được lưu ở ProductModel ở vị trí đầu tiên
        if (productModel.getImageProduct().size() > 0) {
            Log.d("checkImage", " " + productModel.getImageProduct().get(0));
            StorageReference storageImage = FirebaseStorage.getInstance().getReference().child("product").child(productModel.getImageProduct().get(0));
            long TWO_MEGABYTE = 2048 * 2048;
            storageImage.getBytes(TWO_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imgProduct.setImageBitmap(bitmap);
                }
            });
        }
        // end Kiểm tra nếu productModel có ảnh -> truy cập thư mục chứa ảnh trên firebase -> lấy ảnh theo tên được lưu ở ProductModel

        // Với mỗi item sản phẩm tương ứng sẽ gửi dữ liệu tương ứng sang phía chi tiết phẩm phẩm
        holder.recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDetailProduct = new Intent(context, DetailProductActivity.class);
                iDetailProduct.putExtra("SANPHAM", productModel);
                iDetailProduct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(iDetailProduct);
                Log.d("rechekk", " " + position);
            }
        });
        // end Với mỗi item sản phẩm tương ứng sẽ gửi dữ liệu tương ứng sang phía chi tiết phẩm phẩm
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameProduct, txtVersionProduct, txtCanSell;
        ImageView imgProduct;
        RelativeLayout recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtVersionProduct = itemView.findViewById(R.id.txtVersionProduct);
            txtCanSell = itemView.findViewById(R.id.txtCanSell);
            imgProduct = itemView.findViewById(R.id.imgProductList);
            recyclerView = itemView.findViewById(R.id.rlvItem);
        }
    }

    // Mỗi 3 chữ số sẽ có một khoảng trắng
    private String convertText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = stringBuilder.length(); i > 0; i -= 3) {
            stringBuilder.insert(i, " ");
        }
        return stringBuilder.toString();
    }
    //end // Mỗi 3 chữ số sẽ có một khoảng trắng
}
