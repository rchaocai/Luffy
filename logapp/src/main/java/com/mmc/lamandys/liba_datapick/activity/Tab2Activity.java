package com.mmc.lamandys.liba_datapick.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mmc.lamandys.liba_datapick.R;
import com.mmc.lamandys.liba_datapick.dummy.DummyContent;
import com.mmc.lamandys.liba_datapick.fragment.ExpendListviewFragment;
import com.mmc.lamandys.liba_datapick.fragment.ItemFragment;
import com.mmc.lamandys.liba_datapick.fragment.ListItemFragment;

import java.util.ArrayList;
import java.util.List;


public class Tab2Activity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, ViewPager.OnPageChangeListener {

    ViewPager mViewPager;

    Fragment mFragment1;

    Fragment mFragment2;

    Fragment mFragment3;

    private TabLayout mTabLayout;
    private MyFragmentStatePagerAdapter mPageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        initView(savedInstanceState);

    }


    private void initView(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragment1 = ItemFragment.newInstance("RecycleView1");
            mFragment2 = ExpendListviewFragment.newInstance("ExpendListview");
            mFragment3 = ListItemFragment.newInstance("ListView");
        }

        mPageAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout = (TabLayout) findViewById(R.id.toolbar_tab);

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("测试:" + this.getClass().getSimpleName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * viewpagerAdapter
     */
    private class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragmentList;

        public MyFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentList = new ArrayList<>();

            mFragmentList.add(mFragment1);
            mFragmentList.add(mFragment2);
            mFragmentList.add(mFragment3);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] titles = {"我第一", "我第二", "我第三"};
            return titles[position];
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
