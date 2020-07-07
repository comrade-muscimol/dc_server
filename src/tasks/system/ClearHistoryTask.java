package tasks.system;

import entities.DBConnector;
import tasks.Task;

public class ClearHistoryTask implements Task {
    String address;

    public ClearHistoryTask(String address){
        this.address = address;
    }
    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().clear_history(address, get_info());
    }

    @Override
    public String get_info() {
        return address;
    }
}
