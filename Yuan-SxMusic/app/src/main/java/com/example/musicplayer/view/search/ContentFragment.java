package com.example.musicplayer.view.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayer.R;
import com.example.musicplayer.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 残渊 on 2018/11/25.
 */

public class ContentFragment extends Fragment {

    private List<String> mTitleList;
    private List<Fragment> mFragments;
    private ViewPager mPager;
    private TabAdapter mAdapter;
    private TabLayout mTabLayout;
    private String[] mTitles = {"歌曲", "专辑"};
    private String[] mTypes = {"song", "album"};
    private Bundle mBundle;
    private String mSeek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mBundle = getArguments();
        if (mBundle != null) {
            mSeek = mBundle.getString(SearchContentFragment.SEEK_KEY);
        }

        mPager = view.findViewById(R.id.page);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mTitleList = new ArrayList<>();
        mFragments = new ArrayList<>();

        initTab();
        return view;
    }

    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTitleList.add(mTitles[i]);
            mFragments.add(SearchContentFragment.newInstance(mSeek, mTypes[i]));
        }
        mAdapter = new TabAdapter(getChildFragmentManager(), mFragments, mTitleList);
        mPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);



    }

    public static Fragment newInstance(String seek) {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SearchContentFragment.SEEK_KEY, seek);
        fragment.setArguments(bundle);
        return fragment;
    }
}
