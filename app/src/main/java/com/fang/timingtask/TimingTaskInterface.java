package com.fang.timingtask;

/**
 * Created by fang on 3/16/15.
 */
public interface TimingTaskInterface {

    /**
     * return the id of the task if success, else return -1
     *
     * startTime in {@link System#currentTimeMillis System.currentTimeMillis()}
     *
     * AlarmManager.RTC_WAKEUP used in the function, {@link android.app.AlarmManager#RTC_WAKEUP}
     *
     * @param startTime    the time to execute the task first time
     * @param intervalMillis  the interval  to repeat the task
     * @param listener
     * @return
     */
    public int startTask(long startTime, long intervalMillis, TimingTaskListener listener);

    /**
     * return the id of the task if success, else return -1
     *
     * the task only be executed once
     * startTime in {@link System#currentTimeMillis System.currentTimeMillis()}
     *
     * AlarmManager.RTC_WAKEUP used in the function, {@link android.app.AlarmManager#RTC_WAKEUP}
     *
     * @param startTime    the time to execute the task first time
     * @param listener
     * @return
     */
    public int startTask(long startTime, TimingTaskListener listener);


    /**
     * stop a timing task,  the param taskID is returned by startTask
     * @param taskID
     * @return
     */
    public void stopTask(int taskID);
}
