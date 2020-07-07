package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class TransactionTask implements Task {
    String address_from;
    String address_to;

    double dc;
    int dd;

    public TransactionTask(String address_from, String address_to, double dc, int dd) {
        this.address_from = address_from;
        this.address_to = address_to;
        this.dc = dc;
        this.dd = dd;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().TransactionTask(address_from, address_to, dc, dd, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address_from+" to "+address_to+" - "+dc+"dc - "+dd+"dd";
        return out;
    }
}
