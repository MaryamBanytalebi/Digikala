package org.maktab.digikala.controller.activities;

import androidx.fragment.app.Fragment;

import org.maktab.digikala.controller.fragments.DigiKalaFragment;

public class DigikalaActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return DigiKalaFragment.newInstance();
    }
}