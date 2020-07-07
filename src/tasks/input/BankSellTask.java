package tasks.input;


import entities.DBConnector;
import tasks.Task;


public class BankSellTask implements Task {

    String type;
    String address;

    public BankSellTask(String type, String address){
        this.address = address;
        this.type = type;
    }

    @Override
    public boolean try_to_complete() {
        return DBConnector.getInstance().BankSellTask(address, type, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+= address+" --- "+ type;
        return out;
    }
}
