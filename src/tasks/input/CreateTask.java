package tasks.input;

import entities.DBConnector;
import tasks.Task;


public class CreateTask implements Task {
    String address;
    String secret;
    String id;

    public CreateTask(String address, String secret, String id){
        this.address = address;
        this.secret = secret;
        this.id = id;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().CreateTask(address, secret, id, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+secret;
        return out;
    }
}
