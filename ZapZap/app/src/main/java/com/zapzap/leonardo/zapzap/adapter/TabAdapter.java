package com.zapzap.leonardo.zapzap.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.zapzap.leonardo.zapzap.fragment.ContatosFragment;
import com.zapzap.leonardo.zapzap.fragment.ConversasFragment;

public class TabAdapter extends FragmentStatePagerAdapter{

    private String[] tituloTabs = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        switch (i){
            case 0:
                fragment = new ConversasFragment();
                break;
            case 1:
                fragment = new ContatosFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloTabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tituloTabs[position];
    }
}
