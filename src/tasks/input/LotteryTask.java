package tasks.input;

import entities.DBConnector;
import org.hibernate.SessionFactory;
import tasks.Task;

public class LotteryTask implements Task {
    private String address;

    public LotteryTask(String address) {
        this.address = address;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().LotteryTask(address, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address;
        return out;
    }
}
