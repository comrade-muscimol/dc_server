package tasks.system;

import entities.DBConnector;
import tasks.Task;

public class ClearMailTask implements Task {
    String address;

    public ClearMailTask(String address){
        this.address = address;
    }
    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().clear_mail(address, get_info());
    }

    @Override
    public String get_info() {
        return address;
    }
}
