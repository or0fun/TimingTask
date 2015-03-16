package com.fang.test;

import android.app.Application;

import com.fang.timingtask.TimingTaskImp;
import com.fang.timingtask.TimingTaskListener;

/**
 * Created by fang on 3/16/15.
 */
public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Example I
        TimingTaskImp.getInstance(this).startTask(System.currentTimeMillis(), 60*1000, new TimingTaskListener() {
            @Override
            public void doTask() {
                //TODO code here
            }
        });
        //Example II
        TimingTaskImp.getInstance(this).startTask(System.currentTimeMillis(), new TimingTaskListener() {
            @Override
            public void doTask() {
                //TODO code here
            }
        });
    }
}
