package org.maktab.digikala.controller.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.maktab.digikala.controller.fragments.SubCategoryFragment;

public class SubCategoryActivity extends SingleFragmentActivity {

    public static final String PARENT_ID = "org.maktab.digikala.parent_id";
    public static final String PARENT_NAME = "parent_name";

    public static Intent newIntent(Context context, int id, String parentName){
        Intent intent = new Intent(context,SubCategoryActivity.class);
        intent.putExtra(PARENT_ID,id);
        intent.putExtra(PARENT_NAME,parentName);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SubCategoryFragment.newInstance(getIntent().getIntExtra(PARENT_ID,0),
                getIntent().getStringExtra(PARENT_NAME));
    }
}