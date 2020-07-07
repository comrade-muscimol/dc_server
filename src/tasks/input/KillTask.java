package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class KillTask implements Task {

    String address;
    String address_to;

    public KillTask(String address, String address_to) {
        this.address = address;
        this.address_to = address_to;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().KillTask(address, address_to, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" to "+address_to;
        return out;
    }
}
