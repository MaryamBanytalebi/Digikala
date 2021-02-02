package org.maktab.digikala.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.LayoutBottomSheetFilterBinding;
import org.maktab.digikala.databinding.LayoutBottomSheetFilterCategoryBinding;
import org.maktab.digikala.viewmodel.SearchViewModel;

public class BottomSheetFilter extends BottomSheetDialogFragment {

    public static final String EXTRA_FILTER_COLOR = "extra_filter_color";
    BottomSheetBehavior bottomSheetBehavior;
    LayoutBottomSheetFilterBinding mFilterBinding;
    LayoutBottomSheetFilterCategoryBinding mFilterCategoryBinding;
    private SearchViewModel mSearchViewModel;
    private int REQUEST_CODE;
    String mColor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        REQUEST_CODE = getTargetRequestCode();
        //inflating layout
        if (REQUEST_CODE == 0) {

            setLayoutForFilterHome(bottomSheet);

            mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
            //aap bar cancel button clicked
            listeners();


            //hiding app bar at the start
            hideAppBar(mFilterBinding.appBarLayout);

        }else {
            setLayoutForFilter(bottomSheet);
            //aap bar cancel button clicked
            listenersForFilterCategory();

            //hiding app bar at the start
            hideAppBar(mFilterCategoryBinding.appBarLayout);

        }

        return bottomSheet;
    }

    private void updateUI() {
        String color = mSearchViewModel.getColorFromPreferences();
        if (color != null){
            if (color.equalsIgnoreCase("مشکی"))
                mFilterBinding.black.setChecked(true);
            else if (color.equalsIgnoreCase("سفید"))
                mFilterBinding.white.setChecked(true);
            else if (color.equalsIgnoreCase("قهوه ای"))
                mFilterBinding.brown.setChecked(true);
            else if (color.equalsIgnoreCase("قرمز"))
                mFilterBinding.red.setChecked(true);
            else if (color.equalsIgnoreCase("نارنجی"))
                mFilterBinding.orange.setChecked(true);
            else if (color.equalsIgnoreCase("زرد"))
                mFilterBinding.yellow.setChecked(true);
            else if (color.equalsIgnoreCase("سبز"))
                mFilterBinding.green.setChecked(true);
            else if (color.equalsIgnoreCase("آبی"))
                mFilterBinding.blue.setChecked(true);
            else if (color.equalsIgnoreCase("بنفش"))
                mFilterBinding.purple.setChecked(true);
            else if (color.equalsIgnoreCase("صورتی"))
                mFilterBinding.pink.setChecked(true);
            else if (color.equalsIgnoreCase(""))
                mFilterBinding.noFilter.setChecked(true);
        }
    }

    private void setLayoutForFilterHome(BottomSheetDialog bottomSheet) {
        View view = View.inflate(getContext(), R.layout.layout_bottom_sheet_filter, null);

        //binding views to data binding.
        mFilterBinding = DataBindingUtil.bind(view);

        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //setting max height of bottom sheet
        mFilterBinding.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(mFilterBinding.appBarLayout, getActionBarSize());
                    hideAppBar(mFilterBinding.colorLayout);

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(mFilterBinding.appBarLayout);
                    showView(mFilterBinding.colorLayout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void setLayoutForFilter(BottomSheetDialog bottomSheet) {
        View view = View.inflate(getContext(), R.layout.layout_bottom_sheet_filter_category, null);

        //binding views to data binding.
        mFilterCategoryBinding = DataBindingUtil.bind(view);

        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //setting max height of bottom sheet
        mFilterCategoryBinding.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(mFilterCategoryBinding.appBarLayout, getActionBarSize());
                    hideAppBar(mFilterCategoryBinding.colorLayout);

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(mFilterCategoryBinding.appBarLayout);
                    showView(mFilterCategoryBinding.colorLayout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void listenersForFilterCategory() {

    }

    private void listeners() {
        mFilterBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor();
                dismiss();
                sendResult(mColor);
            }
        });
        mFilterBinding.black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "مشکی";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.white.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "سفید";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.brown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "قهوه ای";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "قرمز";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.orange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "نارنجی";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.yellow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "زرد";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "سبز";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "آبی";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.purple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "بنفش";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });
        mFilterBinding.pink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "صورتی";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });

        mFilterBinding.noFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mColor = "";
                mSearchViewModel.setColorInPreferences(null);
                mSearchViewModel.setColorInPreferences(mColor);
                sendResult(mColor);
            }
        });

    }

    private void setColor() {
        if (mFilterBinding.black.isChecked())
            mColor = "مشکی";
        else if (mFilterBinding.white.isChecked())
            mColor = "سفید";
        else if (mFilterBinding.white.isChecked())
            mColor = "قهوه ای";
        else if (mFilterBinding.white.isChecked())
            mColor = "قرمز";
        else if (mFilterBinding.white.isChecked())
            mColor = "نارنجی";
        else if (mFilterBinding.white.isChecked())
            mColor = "زرد";
        else if (mFilterBinding.white.isChecked())
            mColor = "سبز";
        else if (mFilterBinding.white.isChecked())
            mColor = "آبی";
        else if (mFilterBinding.white.isChecked())
            mColor = "بنفش";
        else if (mFilterBinding.white.isChecked())
            mColor = "صورتی";
        else if (mFilterBinding.noFilter.isChecked())
            mColor = "";
    }

    @Override
    public void onStart() {
        super.onStart();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideAppBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);

    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) array.getDimension(0, 0);
        return size;
    }

    private void sendResult(String color) {
        mSearchViewModel.setColorInPreferences(color);
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILTER_COLOR, color);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}
