package tasks.input;

import entities.DBConnector;
import org.hibernate.SessionFactory;
import tasks.Task;


public class CodeTask implements Task {

    String address;
    String code;

    public CodeTask(String address, String code) {
        this.address = address;
        this.code = code;
    }

    @Override
    public boolean try_to_complete() {

        return DBConnector.getInstance().CodeTask(address, code, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+code;
        return out;
    }
}
