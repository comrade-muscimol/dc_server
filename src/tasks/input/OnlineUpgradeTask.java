package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class OnlineUpgradeTask implements Task {
    String address;

    public OnlineUpgradeTask(String address ) {
        this.address = address;

    }

    @Override
    public boolean try_to_complete() {

        return DBConnector.getInstance().OnlineUpgradeTask(address, get_info());

    }

    @Override
    public String get_info() {
        String out = "";
        out+= address;
        return out;
    }

}
