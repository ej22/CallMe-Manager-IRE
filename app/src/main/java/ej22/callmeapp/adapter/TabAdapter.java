package ej22.callmeapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ej22.callmeapp.CallMe;
import ej22.callmeapp.ContactList;

/**
 * Created by stephenhanley on 06/07/2014.
 */
public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new CallMe();
            case 1:
                return new ContactList();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
