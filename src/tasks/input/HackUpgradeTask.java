package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class HackUpgradeTask implements Task {

    String address;
    String offer;

    public HackUpgradeTask(String address, String offer) {
        this.address = address;
        this.offer = offer;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().HackUpgradeTask(address, offer, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+offer;
        return out;
    }

}
