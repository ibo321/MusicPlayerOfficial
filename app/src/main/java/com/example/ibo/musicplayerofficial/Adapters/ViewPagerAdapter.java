package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibo.musicplayerofficial.Fragments.FavoriteFragment;
import com.example.ibo.musicplayerofficial.Fragments.HistoryFragment;
import com.example.ibo.musicplayerofficial.Fragments.PlaylistFragment;
import com.example.ibo.musicplayerofficial.R;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager manager) {
        super(manager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new FavoriteFragment();
        } else if (position == 1) {
            return new HistoryFragment();
        } else {
            return new PlaylistFragment();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_favorite);
            case 1:
                return mContext.getString(R.string.tab_history);
            case 2:
                return mContext.getString(R.string.tab_playlist);
            default:
                return null;
        }
    }
}
