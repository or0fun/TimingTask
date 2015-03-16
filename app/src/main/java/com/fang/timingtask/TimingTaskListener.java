package com.fang.timingtask;

/**
 * Created by fang on 3/16/15.
 */
public interface TimingTaskListener {

    /**
     * sth time consuming should not be executed in this function
     * @return
     */
    public void doTask();
}
