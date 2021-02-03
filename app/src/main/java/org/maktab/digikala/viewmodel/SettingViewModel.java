package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.FragmentNotificationBinding;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.utilities.QueryPreferences;
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
        long time = QueryPreferences.getNotificationTime(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn, time);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void onClickNotificationSwitch() {
        togglePolling();
        if (isTaskScheduled()) {
            mNotificationBinding.switchNotification.setText(R.string.on);
            mNotificationBinding.switchNotification.setChecked(true);
            mNotificationBinding.radioGroupNotification.setVisibility(View.VISIBLE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.VISIBLE);
        } else {
            mNotificationBinding.switchNotification.setText(R.string.off);
            mNotificationBinding.switchNotification.setChecked(false);
            mNotificationBinding.radioGroupNotification.setVisibility(View.GONE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.GONE);
        }
    }

    public void onClickNotificationRadioButtonTime() {
        mNotificationBinding.editTextTime.setVisibility(View.GONE);
    }

    public void onClickNotificationTime() {
        mNotificationBinding.editTextTime.setVisibility(View.VISIBLE);
    }

    public void onClickNotificationSave() {
        if (mNotificationBinding.editTextTime.getVisibility() == View.VISIBLE) {
            String userTime = mNotificationBinding.editTextTime.getText().toString();
            if (userTime.equals("")) {
                Toast.makeText(mContext, "Enter Time for show notification", Toast.LENGTH_SHORT).show();
            } else {
                QueryPreferences.setNotificationTime(getApplication(), Long.parseLong(userTime));
                Toast.makeText(mContext, "Notification Time is:  " + userTime, Toast.LENGTH_SHORT).show();
            }
        }else {
            if (mNotificationBinding.radioButton3.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 3, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(),3);
            }
            else if (mNotificationBinding.radioButton5.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 5, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(),5);
            }
            else if (mNotificationBinding.radioButton8.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 8, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(),8);
            }
            else if (mNotificationBinding.radioButton12.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 12, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(),12);
            }
        }
    }

    public long getNotificationTime() {
        return QueryPreferences.getNotificationTime(getApplication());
    }

    public void setNotificationTime(long notificationTime) {
        QueryPreferences.setNotificationTime(getApplication(),notificationTime);
    }
}
