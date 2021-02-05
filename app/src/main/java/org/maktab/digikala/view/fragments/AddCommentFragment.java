package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentAddCommentBinding;
import org.maktab.digikala.model.Comment;
import org.maktab.digikala.viewmodel.OrderViewModel;

import java.util.Date;
import java.util.Random;

public class AddCommentFragment extends DialogFragment {

    public static final String BUNDLE_KEY = "bundle_key";
    private int mProductId;
    private FragmentAddCommentBinding mAddCommentBinding;
    private OrderViewModel mCartViewModel;
    private Random mRandom;
    private int mRate;
    private MutableLiveData<Integer> mLiveDataRate;

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
        mCartViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mRandom = new Random();
        mRate = 0;
        mLiveDataRate = mCartViewModel.getLiveDataRate();
        observer();
    }

    private void observer() {
        mLiveDataRate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mRate = integer;
                checkRate(integer);
            }
        });
    }

    private void checkRate(Integer integer) {
        if (integer == 1) {
            mAddCommentBinding.star1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 2){
            mAddCommentBinding.star1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 3){
            mAddCommentBinding.star1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mAddCommentBinding.star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 4){
            mAddCommentBinding.star1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 5){
            mAddCommentBinding.star1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mAddCommentBinding.star5.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAddCommentBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_comment,
                container,
                false);

        mAddCommentBinding.setOrderViewModel(mCartViewModel);
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

        mAddCommentBinding.btnSaveInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    Comment comment = new Comment(mRandom.nextInt(),mProductId,
                            new Date().toString(), mAddCommentBinding.nameInsert.getText().toString(),
                            mAddCommentBinding.emailInsert.getText().toString(),
                            mAddCommentBinding.commentInsert.getText().toString(),
                            mRate, false);
                    mCartViewModel.onClickAddComment(comment);
                    dismiss();
                }
                else {
                    checkInput();
                }
            }
        });
    }

    private boolean validateInput() {
        return !mAddCommentBinding.nameInsert.getText().toString().trim().isEmpty() &&
                !mAddCommentBinding.emailInsert.getText().toString().trim().isEmpty() &&
                !mAddCommentBinding.commentInsert.getText().toString().trim().isEmpty();
    }

    private void checkInput() {
        mAddCommentBinding.nameFormInsert.setErrorEnabled(false);
        mAddCommentBinding.emailFormInsert.setErrorEnabled(false);
        mAddCommentBinding.commentFormInsert.setErrorEnabled(false);
        if (mAddCommentBinding.nameInsert.getText().toString().trim().isEmpty()) {
            mAddCommentBinding.nameFormInsert.setErrorEnabled(true);
            mAddCommentBinding.nameFormInsert.setError("Field cannot be empty!");
        }
        if (mAddCommentBinding.emailInsert.getText().toString().trim().isEmpty()) {
            mAddCommentBinding.emailFormInsert.setErrorEnabled(true);
            mAddCommentBinding.emailFormInsert.setError("Field cannot be empty!");
        }
        if (mAddCommentBinding.commentInsert.getText().toString().trim().isEmpty()) {
            mAddCommentBinding.commentFormInsert.setErrorEnabled(true);
            mAddCommentBinding.commentFormInsert.setError("Field cannot be empty!");
        }
    }
}