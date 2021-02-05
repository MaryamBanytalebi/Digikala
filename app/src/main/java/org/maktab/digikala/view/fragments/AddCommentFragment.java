package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentAddCommentBinding;

public class AddCommentFragment extends DialogFragment {

    public static final String BUNDLE_KEY = "bundle_key";
    private int mProductId;
    private FragmentAddCommentBinding mAddCommentBinding;

    public AddCommentFragment() {
        // Required empty public constructor
    }

    public static AddCommentFragment newInstance(int productId) {
        AddCommentFragment fragment = new AddCommentFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY,productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mProductId = getArguments().getInt(BUNDLE_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAddCommentBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_comment,
                container,
                false);
        listeners();
        return mAddCommentBinding.getRoot();
    }

    private void listeners() {
        mAddCommentBinding.btnCancelInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}