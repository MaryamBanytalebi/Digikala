package org.maktab.digikala.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkerParameters;

import org.maktab.digikala.utilities.ServicesUtils;
import org.maktab.digikala.viewmodel.SettingViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PollWorker extends androidx.work.Worker {

    private static final String TAG = "Worker";
    private static final String WORKER_NAME = "WorkerName";

    public PollWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        ServicesUtils.pollAndShowNotification(getApplicationContext(), TAG);
        return Result.success();
    }

    public static void enqueueWork(Context context, boolean isOn, long notificationTime) {
        Log.d(TAG, "enqueueWork");
        WorkManager workManager = WorkManager.getInstance(context);

        if (isOn) {
            Log.d(TAG, "enqueued");
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.UNMETERED)
                    .build();

            PeriodicWorkRequest periodicWorkRequest =
                    new PeriodicWorkRequest.Builder(PollWorker.class, notificationTime, TimeUnit.HOURS)
                            .setConstraints(constraints)
                            .build();

            workManager.enqueueUniquePeriodicWork(
                    WORKER_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    periodicWorkRequest);
        } else {
            Log.d(TAG, "canceled");
            workManager.cancelUniqueWork(WORKER_NAME);
        }
    }

    public static boolean isWorkEnqueued(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        try {
            List<WorkInfo> workInfos =
                    workManager.getWorkInfosForUniqueWork(WORKER_NAME).get();

            for (WorkInfo workInfo: workInfos) {
                if (workInfo.getState() == WorkInfo.State.ENQUEUED ||
                        workInfo.getState() == WorkInfo.State.RUNNING)
                    return true;
            }

            return false;
        } catch (ExecutionException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        } catch (InterruptedException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }
}
