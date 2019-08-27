package com.duykhanh.salesmanager.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.duykhanh.salesmanager.R;
import com.duykhanh.salesmanager.View.Fragment.MoreFragment;
import com.duykhanh.salesmanager.View.Fragment.OrderFragment;
import com.duykhanh.salesmanager.View.Fragment.OverViewFragment;
import com.duykhanh.salesmanager.View.Fragment.ProductFragment;
import com.duykhanh.salesmanager.View.Fragment.StatisticalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class IndexActivity extends AppCompatActivity {
    private TextView txtToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        txtToolbar = findViewById(R.id.txtToolbar);


        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        loadFragment(new OverViewFragment());


    }

    // Gán sự kiện click cho button tương ứng với mỗi fragment
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.overview:
                    fragment = new OverViewFragment();
                    txtToolbar.setText(getString(R.string.title_tong_quan));
                    loadFragment(fragment);
                    return true;
                case R.id.order:
                    fragment = new OrderFragment();
                    txtToolbar.setText(getString(R.string.title_don_hang));
                    loadFragment(fragment);
                    return true;
                case R.id.product:
                    fragment = new ProductFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.statistical:
                    fragment = new StatisticalFragment();
                    txtToolbar.setText(getString(R.string.title_bao_cao));
                    loadFragment(fragment);
                    return true;
                case R.id.more:
                    fragment = new MoreFragment();
                    txtToolbar.setText(getString(R.string.title_them));
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    // end Gán sự kiện click cho mỗi button tương ứng với mỗi fragment

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }


}
