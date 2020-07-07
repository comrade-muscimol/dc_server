package engine;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class LogManager {
    private SessionFactory sessionFactory;
    private static LogManager instance;
    public static LogManager getInstance(){
        if(instance==null) instance = new LogManager();
        return instance;
    }

    private LogManager(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public synchronized void log_add_message(Type type, String message, Total total){
        System.out.println(type.text+" --- "+message+" --- "+total.text);
    }

        public enum Type{
            AD("AD"),
            AUTO("AUTO"),
            BANK("BANK"),
            SYSTEM_CLEAR("SYSTEM_CLEAR"),
            STACK_TRACE("STACK_TRACE"),
            CHAT("CHAT"),
            MAIL("MAIL"),
            CREATE("CREATE"),
            REMOVE("REMOVE"),
            TRANSACTION("TRANSACTION"),
            KILL("KILL"),
            HACK("HACK"),
            CODE("CODE"),

            CREATE_SERVLET("CREATE_SERVLET"),
            IN_SERVLET("IN_SERVLET"),
            REFRESH_INDEX_SERVLET("REFRESH_INDEX_SERVLET"),
            REFRESH_MAIN_SERVLET("REFRESH_MAIN_SERVLET"),
            CASE("CASE"),
            HACK_UPGRADE("HACK_UPGRADE"),
            KILL_UPGRADE("KILL_UPGRADE"),
            LOTTERY("LOTTERY"),
            HIPE("HIPE"),
            MANUAL("MANUAL"),
            OP("OP"),
            OP_UPGRADE("OP_UPGRADE");

            String text;

            Type(String text){
                this.text = text;
            }
        }
        public enum Total{
            SUCCESS("SUCCESS"),
            FAIL("FAIL"),
            STACK_TRACE("STACK_TRACE"),
            NOT_ENOUGH_MONEY("NOT_ENOUGH_MONEY"),
            DEFAULT("DEFAULT"),
            USAGES_LEFT("USAGES_LEFT"),
            MAXIMUM("MAXIMUM");

            String text;

             Total(String text){
                this.text = text;
            }
        }
}
