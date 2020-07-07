package engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class LotteryManager {
    private static ArrayList<String> players;
    private static LotteryRefresher refresher;

    private static LotteryManager instance;
    public static LotteryManager getInstance(){
        if(instance==null) instance = new LotteryManager();
        return instance;
    }

    private long last_update;
    private long end;
    private double dc;
    private int circulation;

    private void second(){
        if(new Date().getTime()>end){
            winner();
            reset();
        }
    }
    private void winner(){
        if(!players.isEmpty()){
            String winner_address = players.get(new Random().nextInt(players.size()));
            //DBManager.getInstance().account_add_dc(winner_address, dc);
            //DBManager.getInstance().account_add_to_history(winner_address, "You win in lottery "+dc+"dc");
            reset();
            last_update = new Date().getTime();
        }

    }
    private void reset(){
        instance = null;
    }

    public synchronized void buy_ticket(String address){
        players.add(address);
        circulation+=1;
        dc+=Prop.LOTTERY_TICKET_COAST;

        last_update = new Date().getTime();

    }
    public synchronized int my_tickets(String address){
        int con = 0;
        for (String s : players){
            if (address.equals(s)) con++;
        }
        return con;
    }

    private LotteryManager(){
        end=new Date().getTime() + ((3600000)*24*7);
        dc=0;
        circulation=0;
        last_update = new Date().getTime();

        players = new ArrayList<>();

        refresher = new LotteryRefresher();
        refresher.start();
    }

    public long getLast_update() {
        return last_update;
    }
    public long getEnd() {
        return end;
    }
    public double getDc() {
        return dc;
    }
    public int getCirculation() {
        return circulation;
    }

    public class LotteryRefresher extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    second();
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
