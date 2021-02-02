package org.maktab.digikala.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.maktab.digikala.DigikalaApplication;
import org.maktab.digikala.R;
import org.maktab.digikala.event.NotificationEvent;

public class VisibleFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onNotificationEventListener(NotificationEvent notificationEvent) {
        String msg = "The fragment received the notification event";
        Log.d(DigikalaApplication.TAG_EVENT_BUS, msg);

        EventBus.getDefault().cancelEventDelivery(notificationEvent);
    }
}