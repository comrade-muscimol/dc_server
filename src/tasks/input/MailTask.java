package tasks.input;

import entities.DBConnector;
import tasks.Task;

public class MailTask implements Task {
    public MailTask(String address,String address_to, String name, String message) {
        this.address = address;
        this.address_to = address_to;
        this.message = message;
        this.name = name;
    }

    String address;
    String address_to;
    String message;
    String name;

    @Override
    public boolean try_to_complete() {

return DBConnector.getInstance().MailTask(address, address_to, name, message, get_info());

    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" to "+address_to+" - "+name+" - "+message;
        return out;
    }
}
