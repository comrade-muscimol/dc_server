package engine;

import entities.AccountsEntity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RatingManager {

    public static final int RATING_MS_REFRESH = 60000;
    private static RatingUpdater updater;
    private SessionFactory sessionFactory;
    private static RatingManager instance;
    public static RatingManager getInstance(){
        if(instance==null) instance = new RatingManager();
        return instance;
    }

    private long last_updated;
    List<String> top_100;

    private RatingManager(){
        top_100 = new ArrayList<>();
        top_100.add("NO ACTUAL INFO");
        last_updated = new Date().getTime();
        sessionFactory = new Configuration().configure().buildSessionFactory();

        updater = new RatingUpdater();
        updater.start();
    }


    private void refresh_top_100(){
        List<AccountsEntity> sorted_accounts = rating_sorted_list();
        double world_capital = 0;

        for(AccountsEntity account : sorted_accounts){
            world_capital+=account.getDc();
        }

        top_100 = new ArrayList<>();

        int i = 1;
        for(AccountsEntity acc : sorted_accounts){
            if(i>=101) break;

            double percent = (acc.getDc()/world_capital)*100;
            top_100.add(i+". "+acc.getAddress()+" --- "+percent+"% dc of world capital --- "+acc.getDc());

            i++;
        }

        last_updated=new Date().getTime();
    }
    public synchronized List rating_sorted_list() {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = null;

        transaction = session.beginTransaction();

        String hql = "FROM AccountsEntity e "
                +  "WHERE e.dc > 0 ORDER BY e.dc DESC";
        Query query = session.createQuery(hql);
        List results = query.list();

        transaction.commit();
        session.close();

        return results;
    }
    public long getLast_updated() {
        return last_updated;
    }
    public List<String> getTop_100() {
        return top_100;
    }

    public class RatingUpdater extends Thread{
    @Override
    public void run() {
        while(true){
            try{
                refresh_top_100();
                Thread.sleep(RATING_MS_REFRESH);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
}
