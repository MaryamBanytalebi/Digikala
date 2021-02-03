package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentNotificationBinding;
import org.maktab.digikala.viewmodel.SettingViewModel;

public class NotificationFragment extends Fragment {

    private FragmentNotificationBinding mNotificationBinding;
    private SettingViewModel mSettingViewModel;

    public NotificationFragment() {
        // Required empty public constructor
    }


    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNotificationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_notification,
                container,
                false);


        mSettingViewModel.setNotificationBinding(mNotificationBinding);
        mNotificationBinding.setSettingViewModel(mSettingViewModel);
        mSettingViewModel.setContext(getActivity());
        checkTime();
        initView();
        return mNotificationBinding.getRoot();
    }

    private void checkTime() {
        mNotificationBinding.editTextTime.setVisibility(View.GONE);
        long time = mSettingViewModel.getNotificationTime();
        if (time == 3)
            mNotificationBinding.radioButton3.setChecked(true);
        else if (time == 5)
            mNotificationBinding.radioButton5.setChecked(true);
        else if (time == 8)
            mNotificationBinding.radioButton8.setChecked(true);
        else if (time == 12)
            mNotificationBinding.radioButton12.setChecked(true);
        else {
            mNotificationBinding.radioButtonNoneOfThem.setChecked(true);
            mNotificationBinding.editTextTime.setVisibility(View.VISIBLE);
            mNotificationBinding.editTextTime.setText(String.valueOf(time));
        }

    }

    private void initView() {
        if (mSettingViewModel.isTaskScheduled()) {
            mNotificationBinding.switchNotification.setText(R.string.on);
            mNotificationBinding.switchNotification.setChecked(true);
            mNotificationBinding.radioGroupNotification.setVisibility(View.VISIBLE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.VISIBLE);

            mSettingViewModel.togglePolling();
        } else {
            mNotificationBinding.switchNotification.setText(R.string.off);
            mNotificationBinding.switchNotification.setChecked(false);
            mNotificationBinding.radioGroupNotification.setVisibility(View.GONE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.GONE);

        }
    }
}