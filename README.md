# TimingTask
A lib for doing timing task with AlarmManager and Broadcast.

```java
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
```
