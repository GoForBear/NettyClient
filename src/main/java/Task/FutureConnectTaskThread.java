package Task;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureConnectTaskThread extends  Thread {
    private ConcurrentLinkedQueue<ConnectTask> connectTasks = new ConcurrentLinkedQueue<ConnectTask>();
    private static FutureConnectTaskThread connectTaskThread = new FutureConnectTaskThread();
    private ExecutorService pool = Executors.newFixedThreadPool(10);
    private long sleepTime = 200;

    private FutureConnectTaskThread(){
        this.start();
    }

    public static void add(ConnectTask connectTask){
        connectTaskThread.connectTasks.add(connectTask);
    }

    @Override
    public void run() {
        while(true){
            handleTask();
            sleepThread(sleepTime);

        }
    }

    private void sleepThread(long time){
        try{
            sleep(time);
        }catch (Exception e){
            System.out.println("线程休眠出现异常");
        }
    }

    private void handleTask(){
        try{
            ConnectTask connectTask = null;
            while(null != connectTasks.peek()){
                connectTask = connectTasks.poll();
                startTask(connectTask);
            }

        }catch (Exception e){
            System.out.println("线程池处理出现异常");
        }

    }

    private void startTask(ConnectTask connectTask){
        pool.execute(new ConnectRunable(connectTask));
    }

    class ConnectRunable implements Runnable{
        ConnectTask connectTask;
        ConnectRunable(ConnectTask connectTask){
            this.connectTask = connectTask;
        }

        @Override
        public void run() {
            connectTask.excute();
        }
    }
}
