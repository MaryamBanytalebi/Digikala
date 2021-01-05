package org.maktab.digikala.controller.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.model.ProductCategory;

import java.util.List;

public class SubCategoryFragment extends Fragment {

    public static final String BUNDLE_PARENT_ID = "bundle_parent_id";
    public static final String BUNDLE_PARENT_NAME = "bundle_parent_name";

    private int mParentId;
    private String mParentName;

    public SubCategoryFragment() {

    }


    public static SubCategoryFragment newInstance(int id, String parentName) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_PARENT_ID,id);
        args.putString(BUNDLE_PARENT_NAME,parentName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_category, container, false);

    }
}