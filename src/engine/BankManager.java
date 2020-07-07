package engine;

import java.util.Date;
import java.util.Random;

import static engine.Prop.*;

public class BankManager {

    private static BankManager instance;
    public static BankManager getInstance(){
        if(instance==null) instance=new BankManager();
        return instance;
    }
    private static TaxChanger tax_changer;
    private static SymbolChanger symbol_changer;
    private static CourseChanger course_changer;
    private BankManager(){
        manual_tax = BANK_START_MANUAL_TAX;
        auto_tax = BANK_START_AUTO_TAX;

        current_sell_course = BANK_START_DD_SELL_COURSE;
        current_buy_course = current_sell_course+BANK_DD_BUY_COURSE_DIFFERENCE;

        tax_changer = new TaxChanger();
        tax_changer.start();

        symbol_changer = new SymbolChanger();
        symbol_changer.start();

        course_changer = new CourseChanger();
        course_changer.start();
    }

    private long last_update;

    Character actualSymbol;


    private double manual_tax;
    private double auto_tax;

    private double current_sell_course;
    private double current_buy_course;

    private Character getRandomSymbol(){
       return SymbolManager.Symbols.values()[new Random().nextInt(SymbolManager.Symbols.values().length)].getCharacter();
    }
    private void changeManualTax(){
        boolean up;
        double sum;

        if(Math.random()<BANK_CHANCE_TO_UP){
            up=true;
        }else{
            up = false;
        }

        sum = (Math.random()*((BANK_MAX_COAST_STEP-BANK_MIN_COAST_STEP)))+BANK_MIN_COAST_STEP;

        if(up) manual_tax+=sum;
        else manual_tax-=sum;

        if(manual_tax>=BANK_MAX_COAST||manual_tax<=BANK_MIN_COAST){
            manual_tax = BANK_START_MANUAL_TAX;
        }

    }
    private void changeAutoTax(){
        boolean up;
        double sum;

        if(Math.random()<BANK_CHANCE_TO_UP){
            up=true;
        }else{
            up = false;
        }

        sum = (Math.random()*((BANK_MAX_COAST_STEP-BANK_MIN_COAST_STEP)))+BANK_MIN_COAST_STEP;

        if(up) auto_tax+=sum;
        else auto_tax-=sum;

        if(auto_tax>=BANK_MAX_COAST||auto_tax<=BANK_MIN_COAST){
            auto_tax = BANK_START_AUTO_TAX;
        }
    }
    private void change_dd_course(){
        boolean up;
        double sum;

        if(Math.random()<BANK_DD_SELL_CHANCE_TO_UP){
            up=true;
        }else{
            up = false;
        }

        sum = (Math.random()*((BANK_MAX_DD_SELL_COAST_STEP-BANK_MIN_DD_SELL_COAST_STEP)))+BANK_MIN_DD_SELL_COAST_STEP;

        if(up) current_sell_course+=sum;
        else current_sell_course-=sum;

        if(current_sell_course>=BANK_MAX_DD_SELL_COURSE||current_sell_course<=BANK_MIN_DD_SELL_COURSE){
            current_sell_course = BANK_START_DD_SELL_COURSE;
        }
        current_buy_course=current_sell_course+BANK_DD_BUY_COURSE_DIFFERENCE;
    }

    public Character getActualSymbol() {
        return actualSymbol;
    }

    public double getManual_tax() {
        return manual_tax;
    }
    public double getAuto_tax() {
        return auto_tax;
    }
    public long getLast_update() {
        return last_update;
    }
    public double getCurrent_sell_course() {
        return current_sell_course;
    }
    public double getCurrent_buy_course() {
        return current_buy_course;
    }




    public class TaxChanger extends Thread{
        @Override
        public void run() {
            while(true){
                try{
                    changeManualTax();
                    changeAutoTax();
                    last_update = new Date().getTime();
                    Thread.sleep(BANK_MS_CHANGE_TAX);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public class SymbolChanger extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    actualSymbol = getRandomSymbol();

                    Thread.sleep(BANK_MS_CHANGE_SYMBOL);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public class CourseChanger extends Thread{
        @Override
        public void run() {
            while (true){
                try {
                    change_dd_course();
                    last_update = new Date().getTime();
                    Thread.sleep(BANK_MS_CHANGE_COURSE);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}



