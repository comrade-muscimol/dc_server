package tasks.input;

import entities.DBConnector;
import tasks.Task;


public class ManualTask implements Task {

    String address;
    int record;

    public ManualTask(String address, int record){
        this.address = address;
        this.record = record;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().ManualTask(address, record, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" - "+record;
        return out;
    }
}
