package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class AutoTask implements Task {

    String address;
    int matches;

    public AutoTask(String address, int matches){
        this.address = address;
        this.matches = matches;
    }

    @Override
    public boolean try_to_complete() {
       return DBConnector.getInstance().AutoTask(address, matches, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+ matches;
        return out;
    }
}
