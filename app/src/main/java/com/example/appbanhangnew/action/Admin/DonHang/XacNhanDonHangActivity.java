package com.example.appbanhangnew.action.Admin.DonHang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaGiaoFragment;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaHuyFragment;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.DaNhanFragment;
import com.example.appbanhangnew.action.Admin.DonHang.Fragment.XacNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.DonMuaActivity;
import com.example.appbanhangnew.action.User.DonHang.Fragment.ChoXacNhanFragment;
import com.example.appbanhangnew.action.User.DonHang.Fragment.DaXacNhanFragment;
import com.example.appbanhangnew.action.User.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class XacNhanDonHangActivity extends AppCompatActivity {
    ViewPager viewPager;
    Toolbar toolbar;
    public  static TabLayout tabLayout;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);
        AnhXa();
        ActionBar();
        setupViewPage(viewPager);
        if(status==0){
            tabLayout.getTabAt(1).select();
        }else {
            tabLayout.getTabAt(0).select();
        }
    }
    public void AnhXa(){
        toolbar=(Toolbar) findViewById(R.id.toolbarTTDonMua);
        viewPager=(ViewPager) findViewById(R.id.tab_viewpaper);
        tabLayout=(TabLayout) findViewById(R.id.tablayout);
        setupViewPage(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }
    public void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theo dõi đơn hàng");
        toolbar.setNavigationIcon(R.drawable.abc_vector_test);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(XacNhanDonHangActivity.this, MainActivity.class);
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.Me);
                startActivity(intent);
                finish();
            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mfragmentList= new ArrayList<>();
        private final List<String> mFragmentTitleList= new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        void addFrag(Fragment fragment,String tille){
            mfragmentList.add(fragment);
            mFragmentTitleList.add(tille);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mfragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mfragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //Toast.makeText(getApplicationContext(),mFragmentTitleList.get(position),Toast.LENGTH_SHORT).show();
            return mFragmentTitleList.get(position);
        }


    }
    private void setupViewPage(ViewPager viewPager){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new XacNhanFragment(),"Chờ xác nhận");
        adapter.addFrag(new DaHuyFragment(),"Bị hủy");
        adapter.addFrag(new DaGiaoFragment(),"Chờ giao");
        adapter.addFrag(new DaNhanFragment(),"Giao thành công");

        viewPager.setAdapter(adapter);
        status=getIntent().getIntExtra("status",0);

    }
}