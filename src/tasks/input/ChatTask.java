package tasks.input;

import entities.DBConnector;
import org.hibernate.SessionFactory;
import tasks.Task;


public class ChatTask implements Task {

    public ChatTask(String address, String message) {
        this.address = address;
        this.message = message;
    }

    String address;
    String message;

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().ChatTask(address, message, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+message;
        return out;
    }
}
