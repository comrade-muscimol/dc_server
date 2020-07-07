package engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static engine.Prop.*;

public class TransactionManager {

    private static TransactionManager instance;
    public static TransactionManager getInstance(){
        if(instance==null) instance = new TransactionManager();
        return instance;
    }

    private long last_update;
    private double current_tax;
    private List<String> transactions;

    private TransactionManager(){

        last_update = new Date().getTime();
        transactions = new ArrayList<>();
        current_tax = TRANSACTION_START_TAX;

    }

    public synchronized void addToHistory(String address_from, String address_to, double dc, int dd){
        try {
            String out = address_from+" -to-> "+address_to+" --- "+dc+"dc & "+dd+"dd";
            transactions.add(0, out);
            last_update = new Date().getTime();

            changeTax();
        }catch (Exception e){

        }
    }

    public List<String> get_last_100(){
        if(transactions==null) transactions = new ArrayList<>();

        List<String> out = new ArrayList<>();
        int i = 0;

        for (String s: transactions) {
            out.add(s);
            i++;
            if(i>100) break;
        }
        return out;

    }

//    public synchronized List transactions_sorted_list() {
//        Session session = this.sessionFactory.openSession();
//        Transaction transaction = null;
//
//        transaction = session.beginTransaction();
//
//        String hql = "FROM TransactionsEntity e "
//                +  "WHERE e.date > 100 ORDER BY e.date DESC";
//        Query query = session.createQuery(hql);
//        List results = query.list();
//
//        transaction.commit();
//        session.close();
//        return results;
//    }
private void changeTax(){
    boolean up;
    double sum;

    if(Math.random()<TRANSACTION_CHANCE_TO_UP){
        up=true;
    }else{
        up = false;
    }

    sum = (Math.random()*((TRANSACTION_MAX_TAX_STEP - TRANSACTION_MIN_TAX_STEP)))+ TRANSACTION_MIN_TAX_STEP;

    if(up) current_tax+=sum;
    else current_tax-=sum;

    if(current_tax>= TRANSACTION_MAX_TAX ||current_tax<= TRANSACTION_MIN_TAX){
        current_tax = TRANSACTION_START_TAX;
    }
    last_update = new Date().getTime();
}

    public long getLast_update() {
        return last_update;
    }
    public double getCurrent_tax() {
        return current_tax;
    }

}
