package tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private Refresher refresher;
    private static TaskManager instance;
    public static TaskManager getInstance(){
        if(instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    private TaskManager(){
        queue = new ArrayList<>();
    }

    private List<Task> queue;

    public synchronized void add_to_queue(Task task_to_add){
        if(queue==null) queue = new ArrayList<>();
        queue.add(task_to_add);

        if(refresher==null) {
            refresher = new Refresher();
            refresher.start();
        }else{
            if(refresher.isInterrupted()){
                refresher = null;
            }
        }


    }
    private synchronized void try_all(){
        if(queue==null) queue = new ArrayList<>();
        if(queue.isEmpty()) return;



        for(Task current_task : queue){

           current_task.try_to_complete();
        }
        queue = new ArrayList<>();
    }

    public class Refresher extends Thread{
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
               if(!queue.isEmpty())
                try {
                    try_all();
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


}
