package org.maktab.digikala.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.maktab.digikala.view.fragments.LocationFragment;

public class LocationActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return LocationFragment.newInstance();
    }

    public static Intent newIntent(Context context){
        return new Intent(context,LocationActivity.class);
    }
}