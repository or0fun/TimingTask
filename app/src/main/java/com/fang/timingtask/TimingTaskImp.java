package com.fang.timingtask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.util.SparseArray;

/**
 * Timing task implements TimingTaskInterface
 * suggest to start task in Application
 *
 * Created by fang on 3/16/15.
 */
public class TimingTaskImp implements ITimingTask {

    private final String TAG = "TimingTaskImp";
    private static TimingTaskImp mInstance;

    private Context mContext;

    private int mCount = 0;
    private SparseArray<TimingTaskListener> mListeners;

    private TimingTaskImp(Context context){
        mContext = context;
        init(context);
    }

    public static TimingTaskImp getInstance(Context context){
        if (null == mInstance) {
            synchronized (TimingTaskImp.class) {
                if (null == mInstance) {
                    mInstance = new TimingTaskImp(context);
                }
            }
        }
        return mInstance;
    }

    private boolean init(Context context) {
        if (null == context) {
            return false;
        }
        mListeners = new SparseArray<TimingTaskListener>();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TimingTaskConstant.ACTION);
        context.registerReceiver(selfBroadcastReceiver, intentFilter);
        return true;
    }

    @Override
    public synchronized int startTask(long startTime, long intervalMillis, TimingTaskListener listener) {
        if (null == listener) {
            Log.d(TAG, "startTask: listener is null");
            return TimingTaskConstant.INVALID_PARAM;
        }
        int taskID = mCount++;
        Log.d(TAG, "startTask: repeat taskID is " + taskID);
        mListeners.put(taskID, listener);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, startTime,
                intervalMillis, getPendingIntent(mContext, TimingTaskConstant.ACTION, taskID));
        return taskID;
    }

    @Override
    public synchronized int startTask(long startTime, TimingTaskListener listener) {
        if (null == mContext) {
            Log.d(TAG, "startTask: context is null");
            return TimingTaskConstant.INVALID_PARAM;
        }
        if (null == listener) {
            Log.d(TAG, "startTask: listener is null");
            return TimingTaskConstant.INVALID_PARAM;
        }
        int taskID = mCount++;
        Log.d(TAG, "startTask: no-repeat taskID is " + taskID);
        mListeners.put(taskID, listener);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, startTime,
                getPendingIntent(mContext, TimingTaskConstant.ACTION, taskID));
        return taskID;
    }

    @Override
    public synchronized void stopTask(int taskID) {
        if (null == mContext) {
            Log.d(TAG, "startTask: context is null");
            return;
        }
        Log.d(TAG, "stopTask: taskID is " + taskID);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(getPendingIntent(mContext, TimingTaskConstant.ACTION, taskID));
        mListeners.delete(taskID);
    }

    @Override
    public synchronized void stopAllTasks() {
        Log.d(TAG, "stopAllTasks: begin");
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        int len = mListeners.size();
        for (int i = 0; i < len; i++) {
            manager.cancel(getPendingIntent(mContext, TimingTaskConstant.ACTION, mListeners.keyAt(i)));
            mListeners.delete(mListeners.keyAt(i));
        }
    }

    private PendingIntent getPendingIntent(Context context, String action, int param) {
        Intent intent = new Intent(context, selfBroadcastReceiver.getClass());
        intent.setAction(action);
        intent.putExtra(TimingTaskConstant.PARAM, param);
        return PendingIntent.getBroadcast(context, action.hashCode(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private BroadcastReceiver selfBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != intent) {
                String action = intent.getAction();
                if (TimingTaskConstant.ACTION.equals(action)) {
                    int param = intent.getIntExtra(TimingTaskConstant.PARAM, TimingTaskConstant.INVALID_PARAM);
                    TimingTaskListener listener = mListeners.get(param);
                    if (null != listener) {
                        Log.d(TAG, "onReceive: listener doTask");
                        listener.doTask();
                    } else {
                        Log.d(TAG, "onReceive: listener is null");
                    }
                }
            }
        }
    };
}
