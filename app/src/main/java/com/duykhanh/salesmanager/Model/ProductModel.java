package com.duykhanh.salesmanager.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.duykhanh.salesmanager.Controller.Interfaces.ProductInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductModel implements Parcelable {
    public long giabanbuon;
    public long giabanle;
    public long gianhap;
    public String mota;
    public String tensanpham;
    public String masanpham;
    public String masanphamkey;
    public String key;
    public String keyImage;

    public String getKeyImage() {
        return keyImage;
    }

    public void setKeyImage(String keyImage) {
        this.keyImage = keyImage;
    }

    List<String> imageProductList;


    protected ProductModel(Parcel in) {
        giabanbuon = in.readLong();
        giabanle = in.readLong();
        gianhap = in.readLong();
        mota = in.readString();
        tensanpham = in.readString();
        masanpham = in.readString();
        masanphamkey = in.readString();
        key = in.readString();
        keyImage = in.readString();
        imageProductList = in.createStringArrayList();
        imageProduct = in.createStringArrayList();
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public String getMasanphamkey() {
        return masanphamkey;
    }

    public void setMasanphamkey(String masanphamkey) {
        this.masanphamkey = masanphamkey;
    }

    private DatabaseReference dataProduct;
    private List<String> imageProduct;


    public List<String> getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(List<String> imageProduct) {
        this.imageProduct = imageProduct;
    }

    public ProductModel() {
        dataProduct = FirebaseDatabase.getInstance().getReference();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public long getGiabanbuon() {
        return giabanbuon;
    }

    public void setGiabanbuon(long giabanbuon) {
        this.giabanbuon = giabanbuon;
    }

    public long getGiabanle() {
        return giabanle;
    }

    public void setGiabanle(long giabanle) {
        this.giabanle = giabanle;
    }

    public long getGianhap() {
        return gianhap;
    }

    public void setGianhap(long gianhap) {
        this.gianhap = gianhap;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public void getListProduct(final ProductInterface productInterface) {
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Truy xuất vào mục SANPHAM trên firebase
                DataSnapshot dataSnapshotProduct = dataSnapshot.child("SANPHAM");
                Log.d("dataProduct", "onDataChange: "+dataSnapshot.child("SANPHAM"));
                // Lấy tất cả từ mục SANPHAM từ firebase
                for (DataSnapshot valueProduct : dataSnapshotProduct.getChildren()) {
                    ProductModel productModel = valueProduct.getValue(ProductModel.class);
                    productModel.setMasanphamkey(valueProduct.getKey());

                    //get value hình ảnh từ firebase
                    DataSnapshot dataSnapshotImageProduct = dataSnapshot.child("HINHANHSP").child(valueProduct.getKey());

                    imageProductList = new ArrayList<>();
                    for (DataSnapshot valueImageProduct : dataSnapshotImageProduct.getChildren()) {
                        // Thêm tất cả hình ảnh lấy được vào danh sách hình ảnh (List<string> imageProductList)
                        imageProductList.add(valueImageProduct.getValue(String.class));
                        Log.d("check", valueImageProduct.getValue() + "");
                    }

                    productModel.setImageProduct(imageProductList);
                    //end get value hình ảnh từ firebase
                    productInterface.getListProductModel(productModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        // gọi sự kiện lắng nghe và trả về value sản phẩm
        dataProduct.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(giabanbuon);
        parcel.writeLong(giabanle);
        parcel.writeLong(gianhap);
        parcel.writeString(mota);
        parcel.writeString(tensanpham);
        parcel.writeString(masanpham);
        parcel.writeString(masanphamkey);
        parcel.writeString(key);
        parcel.writeString(keyImage);
        parcel.writeStringList(imageProductList);
        parcel.writeStringList(imageProduct);
    }
}
