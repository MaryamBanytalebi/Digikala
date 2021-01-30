package org.maktab.digikala.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.maktab.digikala.view.fragments.OrderFragment;

public class OrderActivity extends SingleFragmentActivity {

    public static Intent newIntent (Context context){
        return new Intent(context,OrderActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return OrderFragment.newInstance();
    }
}