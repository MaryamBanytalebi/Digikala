package org.maktab.digikala.controller.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentDigikalaBinding;
import org.maktab.digikala.databinding.ListItemProductBinding;
import org.maktab.digikala.model.ProductItem;

import java.util.List;

public class DigiKalaFragment extends Fragment {

    private FragmentDigikalaBinding mBinding;
    private Context mContext;
    public DigiKalaFragment() {
        // Required empty public constructor
    }

    public static DigiKalaFragment newInstance() {
        DigiKalaFragment fragment = new DigiKalaFragment();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_digikala,
                container,
                false);

        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));
    }
}