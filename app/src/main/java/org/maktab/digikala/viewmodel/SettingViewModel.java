package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentNotificationBinding;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.LocationActivity;
import org.maktab.digikala.view.activities.NotificationActivity;
import org.maktab.digikala.worker.PollWorker;

public class SettingViewModel extends AndroidViewModel {

    private ProductRepository mRepository;
    private Context mContext;
    private FragmentNotificationBinding mNotificationBinding;

    public void setNotificationBinding(FragmentNotificationBinding notificationBinding) {
        mNotificationBinding = notificationBinding;
    }

    public SettingViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ProductRepository();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void onClickNotificationItem() {
        mContext.startActivity(NotificationActivity.newIntent(mContext));
    }

    public void onClickLocationItem() {
        mContext.startActivity(LocationActivity.newIntent(mContext));
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void onClickNotificationSwitch() {
        togglePolling();
        if (isTaskScheduled()) {
            mNotificationBinding.switchNotification.setText(R.string.on);
            mNotificationBinding.switchNotification.setChecked(true);
        } else {
            mNotificationBinding.switchNotification.setText(R.string.off);
            mNotificationBinding.switchNotification.setChecked(false);
        }
    }
}
