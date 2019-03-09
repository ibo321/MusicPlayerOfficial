package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibo.musicplayerofficial.Adapters.ViewPagerAdapter;
import com.example.ibo.musicplayerofficial.R;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CollectionFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tablayout);

        adapter = new ViewPagerAdapter(getActivity(), getChildFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//
//                viewPager.getAdapter().notifyDataSetChanged();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });

        //        viewPager.setId(R.id.fragment_container);
        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//    }
}
