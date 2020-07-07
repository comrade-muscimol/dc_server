package tasks.input;


import entities.DBConnector;
import tasks.Task;


public class CaseTask implements Task {

    String address;
    String offer;

    public CaseTask(String address, String offer) {
        this.address = address;
        this.offer = offer;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().CaseTask(address, offer, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+offer;
        return out;
    }
}
