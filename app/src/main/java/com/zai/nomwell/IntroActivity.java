package com.zai.nomwell;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.zai.nomwell.fragments.PageFragment;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private AppCompatImageView[] indicators = new AppCompatImageView[3];
    private AppCompatImageView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        indicators[0] = (AppCompatImageView) findViewById(R.id.imvw1);
        indicators[1] = (AppCompatImageView) findViewById(R.id.imvw2);
        indicators[2] = (AppCompatImageView) findViewById(R.id.imvw3);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(changeListener);

        btnNext = (AppCompatImageView) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Glide.with(this).load(R.drawable.intro_next).into(btnNext);
    }

    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < indicators.length; i++) {
                if (position == i) {
                    indicators[i].setImageResource(R.drawable.indicator_selected);
                } else {
                    indicators[i].setImageResource(R.drawable.indicator_normal);
                }
            }

            btnNext.setVisibility(position == 2 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
