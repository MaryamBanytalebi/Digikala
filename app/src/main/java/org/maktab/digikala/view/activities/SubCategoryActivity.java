package org.maktab.digikala.view.activities;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import org.maktab.digikala.view.fragments.SubCategoryFragment;

public class SubCategoryActivity extends SingleFragmentActivity {

    public static final String PARENT_ID = "org.maktab.digikala.parent_id";

    public static Intent newIntent(Context context, int id){
        Intent intent = new Intent(context,SubCategoryActivity.class);
        intent.putExtra(PARENT_ID,id);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SubCategoryFragment.newInstance(getIntent().getIntExtra(PARENT_ID,0));
    }
}