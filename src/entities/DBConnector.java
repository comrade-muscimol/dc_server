package entities;

import engine.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static engine.Prop.*;


public class DBConnector {

    private static DBConnector instance;
    public static DBConnector getInstance(){
        if(instance==null) instance = new DBConnector();
        return instance;
    }

    private SessionFactory sessionFactory;
    private DBConnector(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public synchronized boolean AdTask(String address, String info){
        try{
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            account.setAp(account.getAp()+1);
            account.setAccountLastUpdate(new Date().getTime());

            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
            history_temp.add("id_1");
            account.setHistory(Toolbox.list_to_json(history_temp));
            account.setHistoryLastUpdate(new Date().getTime());

            session.update(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean ApExchangeTask(String address, String offer, String info){

        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            switch (offer) {
                case "ap1":
                    if (account.getAp() >= 1) {

                        account.setAp(account.getAp()-1);
                        account.setDc(account.getDc()+ AP_EXCHANGE_DC_1);
                        account.setAccountLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.SUCCESS);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_2");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                    } else {
                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.NOT_ENOUGH_MONEY);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_6");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                    }

                    break;

                case "ap10":

                    if (account.getAp() >= 10) {

                        account.setAp(account.getAp()-10);
                        account.setDc(account.getDc()+AP_EXCHANGE_DC_10);
                        account.setAccountLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.SUCCESS);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_3");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                    } else {
                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.NOT_ENOUGH_MONEY);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_6");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                    }

                    break;

                case "ap50":
                    if (account.getAp() >= 50) {

                        account.setAp(account.getAp()-50);
                        account.setDc(account.getDc()+AP_EXCHANGE_DC_50);
                        account.setAccountLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.SUCCESS);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_4");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                    } else {
                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.NOT_ENOUGH_MONEY);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_6");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                    }

                    break;
                case "ap100":
                    if (account.getAp() >= 100) {

                        account.setAp(account.getAp()-100);
                        account.setDc(account.getDc()+AP_EXCHANGE_DC_100);
                        account.setAccountLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.SUCCESS);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_5");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                    } else {
                        LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.NOT_ENOUGH_MONEY);
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_6");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                    }

                    break;

                default:
                    LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.DEFAULT);
            }

            session.update(account);
            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.AD,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean AutoTask(String address, int matches, String info){

        try {
            double sum = Integer.valueOf(matches)* BankManager.getInstance().getAuto_tax();
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            account.setDc(account.getDc()+sum);
            account.setOp(account.getOp()+1);
            account.setAccountLastUpdate(new Date().getTime());

            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
            history_temp.add("id_7");
            account.setHistory(Toolbox.list_to_json(history_temp));
            account.setHistoryLastUpdate(new Date().getTime());

            session.update(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean BankSellTask(String address, String type, String info){
        try{
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

        double sell_course = BankManager.getInstance().getCurrent_sell_course();
        double buy_course = BankManager.getInstance().getCurrent_buy_course();

        switch (type){

            case "buy1dd":
                    if(account.getDc()>=buy_course*1){

                        account.setDc( account.getDc() - (buy_course*1) );
                        account.setDd( account.getDd() + 1 );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_8");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                break;
            case "buy10dd":
                    if(account.getDc()>=buy_course*10){

                        account.setDc( account.getDc() - (buy_course*10) );
                        account.setDd( account.getDd() + 10 );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_9");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                    break;
            case "buy100dd":

                    if(account.getDc()>=buy_course*100){

                        account.setDc( account.getDc() - (buy_course*100) );
                        account.setDd( account.getDd() + 100 );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_10");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                break;
            case "sell1dd":

                    if(account.getDd()>=1){

                        account.setDd( account.getDd() - 1 );
                        account.setDc( account.getDc() + (sell_course*1) );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_11");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                break;
            case "sell10dd":

                    if(account.getDd()>=10){

                        account.setDd( account.getDd() - 10 );
                        account.setDc( account.getDc() + (sell_course*10) );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_12");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }

                break;
            case "sell100dd":
                    if(account.getDd()>=100){

                        account.setDd( account.getDd() - 100 );
                        account.setDc( account.getDc() + (sell_course*100) );
                        account.setAccountLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_13");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.SUCCESS);

                    }else{

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_14");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                break;

            default:
                LogManager.getInstance().log_add_message(LogManager.Type.AUTO,info, LogManager.Total.DEFAULT);
                return false;
        }

            session.update(account);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){

            LogManager.getInstance().log_add_message(LogManager.Type.BANK,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;

        }
    }
    public synchronized boolean CaseTask(String address,String offer,  String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            switch (offer) {
                case "small_dc":

                    if (account.getDc() >= SMALL_DC_PRICE) {

                        account.setDc(account.getDc() - SMALL_DC_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_small_dc();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("SMALL_DC code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_15");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE,info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }

                    break;
                case "middle_dc":

                    if (account.getDc() >= MIDDLE_DC_PRICE) {

                        account.setDc(account.getDc() - MIDDLE_DC_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_middle_dc();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("MIDDLE_DC code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_16");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }

                    break;
                case "big_dc":

                    if (account.getDc() >= BIG_DC_PRICE) {

                        account.setDc(account.getDc() - BIG_DC_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_big_dc();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("BIG_DC code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_17");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                    break;

                case "small_dd":


                    if (account.getDd() >= SMALL_DD_PRICE) {

                        account.setDd(account.getDd() - SMALL_DD_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_small_dd();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("SMALL_DD code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_18");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                    break;
                case "middle_dd":

                    if (account.getDd() >= MIDDLE_DD_PRICE) {

                        account.setDd(account.getDd() - MIDDLE_DD_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_middle_dd();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("MIDDLE_DD code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_19");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                    break;
                case "big_dd":

                    if (account.getDd() >= BIG_DD_PRICE) {

                        account.setDd(account.getDd() - BIG_DD_PRICE);
                        account.setAccountLastUpdate(new Date().getTime());

                        String code = generate_big_dd();

                        List<String> mail_temp = Toolbox.json_to_list(account.getMail());
                        mail_temp.add("BIG_DD code is: " + code);
                        account.setMail(Toolbox.list_to_json(mail_temp));
                        account.setMailLastUpdate(new Date().getTime());

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_20");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.SUCCESS);


                    } else {
                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_21");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());
                        LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.NOT_ENOUGH_MONEY);
                    }
                    break;
                default:
                    LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.DEFAULT);
                    return false;
            }

            session.update(account);
            transaction.commit();
            session.close();
            return true;

        } catch (Exception e) {
            LogManager.getInstance().log_add_message(LogManager.Type.CASE, info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean ChatTask(String address, String message, String info){
        try {
//            Session session = this.sessionFactory.openSession();
//            Transaction transaction = null;
//
//            transaction = session.beginTransaction();
//
//            ChatEntity entity = new ChatEntity();
//            entity.setAddressFrom(address);
//            entity.setDate(new Date().getTime());
//            entity.setMessage(message);
//
//            session.save(entity);
//            transaction.commit();
//            session.close();
//
//            ChatManager.getInstance().chat_sorted_list_refresh();

            ChatManager.getInstance().add_message(address, message);

            LogManager.getInstance().log_add_message(LogManager.Type.CHAT,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.CHAT,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean CodeTask(String address, String code, String info){

        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();

            AccountsEntity account_entity = (AccountsEntity) session.get(AccountsEntity.class, address);
            CodesEntity code_entity = (CodesEntity) session.get(CodesEntity.class, code);

            if(code_entity.getUsagesLeft()>0){

                code_entity.setUsagesLeft(code_entity.getUsagesLeft()-1);

                account_entity.setDc(account_entity.getDc()+code_entity.getDc());
                account_entity.setDd(account_entity.getDd()+code_entity.getDd());
                account_entity.setAccountLastUpdate(new Date().getTime());

                List<String> history_temp = Toolbox.json_to_list(account_entity.getHistory());
                history_temp.add("id_22");
                account_entity.setHistory(Toolbox.list_to_json(history_temp));
                account_entity.setHistoryLastUpdate(new Date().getTime());
                LogManager.getInstance().log_add_message(LogManager.Type.CODE,info, LogManager.Total.SUCCESS);

            }else{
                List<String> history_temp = Toolbox.json_to_list(account_entity.getHistory());
                history_temp.add("id_23");
                account_entity.setHistory(Toolbox.list_to_json(history_temp));
                account_entity.setHistoryLastUpdate(new Date().getTime());
                LogManager.getInstance().log_add_message(LogManager.Type.CODE,info, LogManager.Total.USAGES_LEFT);
            }

            session.update(account_entity);
            session.update(code_entity);

            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.CODE,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean CreateTask(String address, String secret, String id, String info){
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = new AccountsEntity();

            account.setAddress(address);
            account.setSecret(secret);
            account.setId(id);

            account.setDc(ACCOUNT_START_DC);
            account.setDd(ACCOUNT_START_DD);
            account.setRecord(ACCOUNT_START_RECORD);

            account.setLastLogin(new Date().getTime());
            account.setAccountLastUpdate(new Date().getTime());

            account.setOp(ACCOUNT_START_OP);
            account.setAp(ACCOUNT_START_AP);
            account.setKillMin(ACCOUNT_START_KILL_MIN);
            account.setKillMax(ACCOUNT_START_KILL_MAX);
            account.setHackMin(ACCOUNT_START_HACK_MIN);
            account.setHackMax(ACCOUNT_START_HACK_MAX);
            account.setHackBounty(ACCOUNT_START_HACK_BOUNTY);
            account.setLotteryTickets(ACCOUNT_START_TICKETS);

            account.setOpBounty(ACCOUNT_START_OP_BOUNTY);

            List<String> history = new ArrayList<String>();
            history.add("id_24");
            account.setHistory(Toolbox.list_to_json(history));
            account.setHistoryLastUpdate(new Date().getTime());

            List<String> mail = new ArrayList<>();
            account.setMail(Toolbox.list_to_json(mail));
            account.setMailLastUpdate(new Date().getTime());

            session.save(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.CREATE, info, LogManager.Total.SUCCESS);
            return true;
        }

        catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.CREATE, info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean HackTask(String address, String address_to, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account_hacker = (AccountsEntity) session.get(AccountsEntity.class, address);
            AccountsEntity account_victim = (AccountsEntity) session.get(AccountsEntity.class, address_to);

            if(account_hacker.getDd()>= HACK_PRICE_DD){

                account_hacker.setDd(account_hacker.getDd()- HACK_PRICE_DD);

                if(roll_hack_kill(account_hacker.getHackMin(), account_hacker.getHackMax())){

                    account_hacker.setDc( account_hacker.getDc() + (account_victim.getDc()*account_hacker.getHackBounty()) );
                    account_victim.setDc (account_victim.getDc() - (account_victim.getDc()*account_hacker.getHackBounty()) );

                    account_hacker.setAccountLastUpdate(new Date().getTime());
                    account_victim.setAccountLastUpdate(new Date().getTime());

                    List<String> history_temp_hacker = Toolbox.json_to_list(account_hacker.getHistory());
                    history_temp_hacker.add("id_25");
                    account_hacker.setHistory(Toolbox.list_to_json(history_temp_hacker));
                    account_hacker.setHistoryLastUpdate(new Date().getTime());

                    List<String> history_temp_victim = Toolbox.json_to_list(account_victim.getHistory());
                    history_temp_victim.add("id_26");
                    account_victim.setHistory(Toolbox.list_to_json(history_temp_victim));
                    account_victim.setHistoryLastUpdate(new Date().getTime());

                }else {
                    List<String> history_temp_hacker = Toolbox.json_to_list(account_hacker.getHistory());
                    history_temp_hacker.add("id_27");
                    account_hacker.setHistory(Toolbox.list_to_json(history_temp_hacker));
                    account_hacker.setHistoryLastUpdate(new Date().getTime());

                    List<String> history_temp_victim = Toolbox.json_to_list(account_victim.getHistory());
                    history_temp_victim.add("id_28");
                    account_victim.setHistory(Toolbox.list_to_json(history_temp_victim));
                    account_victim.setHistoryLastUpdate(new Date().getTime());
                }

            }else{
                List<String> history_temp_hacker = Toolbox.json_to_list(account_hacker.getHistory());
                history_temp_hacker.add("id_29");
                account_hacker.setHistory(Toolbox.list_to_json(history_temp_hacker));
                account_hacker.setHistoryLastUpdate(new Date().getTime());
            }

            session.update(account_hacker);
            session.update(account_victim);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.HACK,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.HACK,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean HackUpgradeTask(String address, String offer, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            switch (offer) {
                case "hack_upgrade_max":
                    if (account.getDd() >= HACK_MAX_UPGRADE_PRICE) {

                        if (account.getHackMax() < HACK_MAX_CHANCE_MAXIMUM) {

                            account.setDd(account.getDd()- HACK_MAX_UPGRADE_PRICE);
                            account.setHackMax(account.getHackMax()+ HACK_UPGRADE_MAX_RISE);
                            account.setAccountLastUpdate(new Date().getTime());


                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_30");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.SUCCESS);
                        } else {
                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_31");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.MAXIMUM);
                        }
                    } else {

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_32");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                    break;

                case "hack_upgrade_min":
                    if (account.getDd() >= HACK_MIN_UPGRADE_PRICE) {

                        if (
                                account.getHackMin() < HACK_MIN_CHANCE_MAXIMUM
                                        &&
                                        account.getHackMin() < account.getHackMax()) {

                            account.setDd(account.getDd()- HACK_MIN_UPGRADE_PRICE);
                            account.setHackMin(account.getHackMin()+ HACK_UPGRADE_MIN_RISE);
                            account.setAccountLastUpdate(new Date().getTime());

                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_33");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.SUCCESS);
                        } else {
                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_34");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.MAXIMUM);
                        }
                    } else {

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_35");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                    break;
                case "hack_upgrade_bounty":
                    if (account.getDd() >= HACK_BOUNTY_UPGRADE_PRICE) {

                        if (account.getHackBounty() < HACK_BOUNTY_PERCENT_MAXIMUM) {

                            account.setDd(account.getDd()- HACK_BOUNTY_UPGRADE_PRICE);
                            account.setHackBounty(account.getHackBounty()+ HACK_UPGRADE_BOUNTY_PRICE);
                            account.setAccountLastUpdate(new Date().getTime());

                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_36");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.SUCCESS);
                        } else {
                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_37");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.MAXIMUM);
                        }
                    } else {

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_38");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                    break;

                default:
                    LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.DEFAULT);
                    session.close();
                    return false;
            }

            session.update(account);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean HipeTask(String address, double dc, double offer_chance, double offer_multiplier, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);



            if (account.getDc() >= dc) {

                account.setDc(account.getDc()-dc);
                account.setAccountLastUpdate(new Date().getTime());


                if (roll_hipe(offer_chance)) {

                    account.setDc(account.getDc()+ (dc*offer_multiplier));

                    List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                    history_temp.add("id_39");
                    account.setHistory(Toolbox.list_to_json(history_temp));
                    account.setHistoryLastUpdate(new Date().getTime());
                } else {
                    List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                    history_temp.add("id_40");
                    account.setHistory(Toolbox.list_to_json(history_temp));
                    account.setHistoryLastUpdate(new Date().getTime());
                }


                LogManager.getInstance().log_add_message(LogManager.Type.HIPE,info, LogManager.Total.SUCCESS);


            } else {

                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_41");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.HIPE,info, LogManager.Total.NOT_ENOUGH_MONEY);

            }

            session.update(account);
            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.HIPE,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean KillTask(String address, String address_to, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account_killer = (AccountsEntity) session.get(AccountsEntity.class, address);
            AccountsEntity account_victim = (AccountsEntity) session.get(AccountsEntity.class, address_to);

            if(account_killer.getDd()>= KILL_PRICE_DD){

                account_killer.setDd(account_killer.getDd()- KILL_PRICE_DD);

                if(roll_hack_kill(account_killer.getKillMin(), account_killer.getKillMax())){

                    account_killer.setDc( account_killer.getDc() + account_victim.getDc() );

                    account_killer.setAccountLastUpdate(new Date().getTime());

                    List<String> history_temp_killer = Toolbox.json_to_list(account_killer.getHistory());
                    history_temp_killer.add("id_42");
                    account_killer.setHistory(Toolbox.list_to_json(history_temp_killer));
                    account_killer.setHistoryLastUpdate(new Date().getTime());

                    session.update(account_killer);
                    session.delete(account_victim);

                    transaction.commit();
                    session.close();

                    LogManager.getInstance().log_add_message(LogManager.Type.KILL,info, LogManager.Total.SUCCESS);
                    LogManager.getInstance().log_add_message(LogManager.Type.REMOVE,info, LogManager.Total.SUCCESS);


                    return true;
                }else {
                    List<String> history_temp_killer = Toolbox.json_to_list(account_killer.getHistory());
                    history_temp_killer.add("id_43");
                    account_killer.setHistory(Toolbox.list_to_json(history_temp_killer));
                    account_killer.setHistoryLastUpdate(new Date().getTime());

                    List<String> history_temp_victim = Toolbox.json_to_list(account_victim.getHistory());
                    history_temp_victim.add("id_44");
                    account_victim.setHistory(Toolbox.list_to_json(history_temp_victim));
                    account_victim.setHistoryLastUpdate(new Date().getTime());

                    LogManager.getInstance().log_add_message(LogManager.Type.KILL,info, LogManager.Total.SUCCESS);

                }


            }else{
                List<String> history_temp_killer = Toolbox.json_to_list(account_killer.getHistory());
                history_temp_killer.add("id_45");
                account_killer.setHistory(Toolbox.list_to_json(history_temp_killer));
                account_killer.setHistoryLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.KILL,info, LogManager.Total.NOT_ENOUGH_MONEY);
            }

            session.update(account_killer);
            session.update(account_victim);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.KILL,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }

    }
    public synchronized boolean KillUpgradeTask(String address, String offer, String info){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);


            switch (offer) {
                case "kill_upgrade_max":
                    if (account.getDd() >= KILL_MAX_UPGRADE_PRICE) {

                        if (account.getKillMax() < KILL_MAX_CHANCE_MAXIMUM) {

                            account.setDd(account.getDd()- KILL_MAX_UPGRADE_PRICE);
                            account.setKillMax(account.getKillMax()+ KILL_UPGRADE_MAX_RISE);
                            account.setAccountLastUpdate(new Date().getTime());


                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_46");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.KILL_UPGRADE,info, LogManager.Total.SUCCESS);
                        } else {
                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_47");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.KILL_UPGRADE,info, LogManager.Total.MAXIMUM);
                        }
                    } else {

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_48");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.KILL_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                    break;
                case "kill_upgrade_min":
                    if (account.getDd() >= KILL_MIN_UPGRADE_PRICE) {

                        if (
                                account.getKillMin() < KILL_MIN_CHANCE_MAXIMUM
                                        &&
                                        account.getKillMin() < account.getKillMax()) {

                            account.setDd(account.getDd()- KILL_MIN_UPGRADE_PRICE);
                            account.setKillMin(account.getKillMin()+ KILL_UPGRADE_MIN_RISE);
                            account.setAccountLastUpdate(new Date().getTime());

                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_49");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.SUCCESS);
                        } else {
                            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                            history_temp.add("id_50");
                            account.setHistory(Toolbox.list_to_json(history_temp));
                            account.setHistoryLastUpdate(new Date().getTime());

                            LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.MAXIMUM);
                        }
                    } else {

                        List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                        history_temp.add("id_51");
                        account.setHistory(Toolbox.list_to_json(history_temp));
                        account.setHistoryLastUpdate(new Date().getTime());

                        LogManager.getInstance().log_add_message(LogManager.Type.HACK_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);

                    }
                    break;
                default:
                    LogManager.getInstance().log_add_message(LogManager.Type.KILL_UPGRADE,info, LogManager.Total.DEFAULT);
                    session.close();
                    return false;

            }

            session.update(account);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.KILL_UPGRADE,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }

    }
    public synchronized boolean LotteryTask(String address, String info){

        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);


            if (account.getDc()>= LOTTERY_TICKET_COAST){

                account.setDc(account.getDc()- LOTTERY_TICKET_COAST);
                account.setLotteryTickets(account.getLotteryTickets()+1);
                account.setAccountLastUpdate(new Date().getTime());

                LotteryManager.getInstance().buy_ticket(address);

                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_52");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.LOTTERY,info, LogManager.Total.SUCCESS);

            }else{
                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_53");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.LOTTERY,info, LogManager.Total.NOT_ENOUGH_MONEY);

            }

            session.update(account);
            transaction.commit();
            session.close();

            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.LOTTERY,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean MailTask(String address, String address_to, String name, String message, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity sender = (AccountsEntity) session.get(AccountsEntity.class, address);
            AccountsEntity recipient = (AccountsEntity) session.get(AccountsEntity.class, address_to);

            List<String> mail_temp = Toolbox.json_to_list(recipient.getMail());
            mail_temp.add("<"+new Date().getTime()+" - "+address+" - "+name+" - "+message+">");
            recipient.setMail(Toolbox.list_to_json(mail_temp));
            recipient.setMailLastUpdate(new Date().getTime());

            List<String> history_temp = Toolbox.json_to_list(sender.getHistory());
            history_temp.add("id_54");
            sender.setHistory(Toolbox.list_to_json(history_temp));
            sender.setHistoryLastUpdate(new Date().getTime());

            session.update(sender);
            session.update(recipient);

            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.MAIL,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.MAIL,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean ManualTask(String address, int record, String info){
        try {
            double sum = Integer.valueOf(record)* BankManager.getInstance().getManual_tax();


            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            account.setDc(account.getDc()+sum);


            if(account.getRecord()<record){
                account.setRecord(record);
                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_56");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());
            }
            account.setAccountLastUpdate(new Date().getTime());


            List<String> history_temp = Toolbox.json_to_list(account.getHistory());
            history_temp.add("id_55");
            account.setHistory(Toolbox.list_to_json(history_temp));
            account.setHistoryLastUpdate(new Date().getTime());

            session.update(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.MANUAL,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.MANUAL,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean OnlineTask(String address, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            if (account.getOp() >= OP_AMOUNT_TO_EXCHANGE) {

                account.setOp(account.getOp()- OP_AMOUNT_TO_EXCHANGE);
                account.setDc(account.getDc()+account.getOpBounty());
                account.setAccountLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.OP,info, LogManager.Total.SUCCESS);

                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_57");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());

            } else {
                LogManager.getInstance().log_add_message(LogManager.Type.OP,info, LogManager.Total.NOT_ENOUGH_MONEY);
                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_58");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());
            }


            session.update(account);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.OP,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean OnlineUpgradeTask(String address, String info){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            if (account.getDd() >= ONLINE_UPGRADE_PRICE_DD) {

                account.setDd(account.getDd()- ONLINE_UPGRADE_PRICE_DD);
                account.setOpBounty(account.getOpBounty()* ONLINE_RISE_MULTIPLIER);
                account.setAccountLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.OP_UPGRADE,info, LogManager.Total.SUCCESS);
                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_59");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());
            } else {
                LogManager.getInstance().log_add_message(LogManager.Type.OP_UPGRADE,info, LogManager.Total.NOT_ENOUGH_MONEY);
                List<String> history_temp = Toolbox.json_to_list(account.getHistory());
                history_temp.add("id_60");
                account.setHistory(Toolbox.list_to_json(history_temp));
                account.setHistoryLastUpdate(new Date().getTime());
            }


            session.update(account);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.OP_UPGRADE,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean TransactionTask(String address_from, String address_to, double dc, int dd, String info){
        try {
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity donor = (AccountsEntity) session.get(AccountsEntity.class, address_from);
            AccountsEntity recipient = (AccountsEntity) session.get(AccountsEntity.class, address_to);

            double tax = dc * TransactionManager.getInstance().getCurrent_tax();
            double dc_with_tax = dc + tax;


            if (donor.getDc() >= dc_with_tax && donor.getDd() >= dd + 1) {

                donor.setDc(donor.getDc() - dc_with_tax);
                recipient.setDc(recipient.getDc()+dc);

                donor.setDd(donor.getDd()-(dd+1));
                recipient.setDd(recipient.getDd()+dd);

                donor.setAccountLastUpdate(new Date().getTime());
                recipient.setAccountLastUpdate(new Date().getTime());

                TransactionManager.getInstance().addToHistory(address_from, address_to, dc, dd);

                List<String> history_temp = Toolbox.json_to_list(donor.getHistory());
                history_temp.add("id_61");
                donor.setHistory(Toolbox.list_to_json(history_temp));
                donor.setHistoryLastUpdate(new Date().getTime());

                List<String> history_temp_2 = Toolbox.json_to_list(recipient.getHistory());
                history_temp_2.add("id_62");
                recipient.setHistory(Toolbox.list_to_json(history_temp_2));
                recipient.setHistoryLastUpdate(new Date().getTime());

                LogManager.getInstance().log_add_message(LogManager.Type.TRANSACTION,info, LogManager.Total.SUCCESS);


            } else {
                LogManager.getInstance().log_add_message(LogManager.Type.TRANSACTION,info, LogManager.Total.NOT_ENOUGH_MONEY);

                List<String> history_temp = Toolbox.json_to_list(donor.getHistory());
                history_temp.add("id_63");
                donor.setHistory(Toolbox.list_to_json(history_temp));
                donor.setHistoryLastUpdate(new Date().getTime());
            }

            session.update(donor);
            session.update(recipient);

            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.TRANSACTION,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }

    public synchronized AccountsEntity getAccountEntity(String address){
        try{
            Session session = this.sessionFactory.openSession();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);
            session.close();
            return account;
        }catch (Exception e){
         return null;
        }
    }
    public synchronized boolean clear_history(String address, String info){
        try{
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            account.setHistory(Toolbox.list_to_json(new ArrayList<>()));

            session.update(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.SYSTEM_CLEAR,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.SYSTEM_CLEAR,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }
    public synchronized boolean clear_mail(String address, String info){
        try{
            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            AccountsEntity account = (AccountsEntity) session.get(AccountsEntity.class, address);

            account.setMail(Toolbox.list_to_json(new ArrayList<>()));

            session.save(account);
            transaction.commit();
            session.close();

            LogManager.getInstance().log_add_message(LogManager.Type.SYSTEM_CLEAR,info, LogManager.Total.SUCCESS);
            return true;

        }catch (Exception e){
            LogManager.getInstance().log_add_message(LogManager.Type.SYSTEM_CLEAR,info, LogManager.Total.FAIL);
            LogManager.getInstance().log_add_message(LogManager.Type.STACK_TRACE, e.getMessage(), LogManager.Total.STACK_TRACE);
            return false;
        }
    }

    private boolean roll_hipe(double chance){
        if (ThreadLocalRandom.current().nextDouble(0.0, 1.0)<=chance) return true;
        else return false;
    }
    private boolean roll_hack_kill(double min, double max){
        double current_chance = ThreadLocalRandom.current().nextDouble(min, max);

        if(ThreadLocalRandom.current().nextDouble(0.0, 1.0)<current_chance) return true;
        else return false;

    }

    private synchronized String generate_small_dc(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(SMALL_DC_MIN_DC, SMALL_DC_MAX_DC));
            entity.setDd(0);
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }

    }
    private synchronized String generate_middle_dc(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(MIDDLE_DC_MIN_DC, MIDDLE_DC_MAX_DC));
            entity.setDd(0);
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }
    }
    private synchronized String generate_big_dc(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(BIG_DC_MIN_DC, BIG_DC_MAX_DC));
            entity.setDd(0);
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }
    }
    private synchronized String generate_small_dd(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(SMALL_DD_MIN_DC, SMALL_DD_MAX_DC));
            entity.setDd(ThreadLocalRandom.current().nextInt(SMALL_DD_MIN_DD, SMALL_DD_MAX_DD));
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }
    }
    private synchronized String generate_middle_dd(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(MIDDLE_DD_MIN_DC, MIDDLE_DD_MAX_DC));
            entity.setDd(ThreadLocalRandom.current().nextInt(MIDDLE_DD_MIN_DD, MIDDLE_DD_MAX_DD));
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }
    }
    private synchronized String generate_big_dd(){
        try {

            Session session = this.sessionFactory.openSession();
            Transaction transaction = null;

            transaction = session.beginTransaction();
            CodesEntity entity = new CodesEntity();

            String current_code = generate_vacant_code(5);
            entity.setCode(current_code);
            entity.setDc(ThreadLocalRandom.current().nextDouble(BIG_DD_MIN_DC, BIG_DD_MAX_DC));
            entity.setDd(ThreadLocalRandom.current().nextInt(BIG_DD_MIN_DD, BIG_DD_MAX_DD));
            entity.setUsagesLeft(1);

            session.save(entity);
            transaction.commit();
            session.close();
            return current_code;

        } catch (Exception e) {
            return null;
        }
    }

    private synchronized String generate_vacant_code(int length){
        String out = "";
        while(true){

            for (int i = 0; i < length; i++) {
                out += SymbolManager.Symbols.values()[new Random().nextInt(SymbolManager.Symbols.values().length)].getCharacter();
            }
            try {

                Session session = this.sessionFactory.openSession();
                Transaction transaction = null;

                transaction = session.beginTransaction();
                CodesEntity entity = (CodesEntity) session.get(CodesEntity.class, out);
                entity.getCode();
                entity.getDc();
                entity.getDd();
                entity.getUsagesLeft();

            } catch (Exception e) {
                return out;
            }

        }
    }
}
