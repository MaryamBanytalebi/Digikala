package org.maktab.digikala.controller.activities;

import androidx.fragment.app.Fragment;

import org.maktab.digikala.controller.fragments.HomePageFragment;

public class HomePageActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return HomePageFragment.newInstance();
    }
}