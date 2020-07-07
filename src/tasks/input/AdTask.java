package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class AdTask implements Task {

    public AdTask(String address) {
        this.address = address;
    }

    String address;

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().AdTask(address, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address;
        return out;
    }
}
