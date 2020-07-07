package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class OnlineTask implements Task {
    private String address;

    public OnlineTask(String address) {
        this.address = address;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().OnlineTask(address, get_info());

    }

    @Override
    public String get_info() {
        String out = "";
        out+= address;
        return out;
    }

}
