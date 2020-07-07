package engine;

public class Prop {

    public static final double SMALL_DC_PRICE = 1.0f;
    public static final double MIDDLE_DC_PRICE = 10.0f;
    public static final double BIG_DC_PRICE = 100.0f;
    public static final int SMALL_DD_PRICE = 1;
    public static final int MIDDLE_DD_PRICE = 5;
    public static final int BIG_DD_PRICE = 10;
    public static final double SMALL_DC_MIN_DC = 0.0f;
    public static final double SMALL_DC_MAX_DC = 2.0f;
    public static final double MIDDLE_DC_MIN_DC = 0.0f;
    public static final double MIDDLE_DC_MAX_DC = 20.0f;
    public static final double BIG_DC_MIN_DC = 0.0f;
    public static final double BIG_DC_MAX_DC = 200.0f;
    public static final double SMALL_DD_MIN_DC = 0.0f;
    public static final double SMALL_DD_MAX_DC = 10.0f;
    public static final int SMALL_DD_MIN_DD = 0;
    public static final int SMALL_DD_MAX_DD = 2;
    public static final double MIDDLE_DD_MIN_DC = 0.0f;
    public static final double MIDDLE_DD_MAX_DC = 20.0f;
    public static final int MIDDLE_DD_MIN_DD = 0;
    public static final int MIDDLE_DD_MAX_DD = 7;
    public static final double BIG_DD_MIN_DC = 0.0f;
    public static final double BIG_DD_MAX_DC = 50.0f;
    public static final int BIG_DD_MIN_DD = 0;
    public static final int BIG_DD_MAX_DD = 15;

    public static final double ACCOUNT_START_DC = 0.0d;
    public static final int ACCOUNT_START_DD = 0;
    public static final int ACCOUNT_START_RECORD = 0;
    public static final int ACCOUNT_START_OP = 0;
    public static final int ACCOUNT_START_AP = 0;
    public static final double ACCOUNT_START_KILL_MAX = 0.02d;
    public static final double ACCOUNT_START_KILL_MIN = 0.01d;
    public static final double ACCOUNT_START_HACK_MAX = 0.02d;
    public static final double ACCOUNT_START_HACK_MIN = 0.01d;
    public static final double ACCOUNT_START_HACK_BOUNTY = 0.05d;
    public static final int ACCOUNT_START_TICKETS = 0;
    public static final double ACCOUNT_START_OP_BOUNTY = 0.0000000010d;

    public static final int HACK_PRICE_DD = 10;
    public static final int HACK_MAX_UPGRADE_PRICE = 1;
    public static final int HACK_MIN_UPGRADE_PRICE = 1;
    public static final int HACK_BOUNTY_UPGRADE_PRICE = 1;
    public static final double HACK_UPGRADE_MAX_RISE = 0.0001f;
    public static final double HACK_UPGRADE_MIN_RISE = 0.0001f;
    public static final double HACK_UPGRADE_BOUNTY_PRICE = 0.0001f;
    public static final double HACK_MAX_CHANCE_MAXIMUM = 0.99f;
    public static final double HACK_MIN_CHANCE_MAXIMUM = 0.98f;
    public static final double HACK_BOUNTY_PERCENT_MAXIMUM = 0.30f;

    public static final int KILL_PRICE_DD = 100;
    public static final int KILL_MAX_UPGRADE_PRICE = 1;
    public static final int KILL_MIN_UPGRADE_PRICE = 1;
    public static final double KILL_UPGRADE_MAX_RISE = 0.0001f;
    public static final double KILL_UPGRADE_MIN_RISE = 0.0001f;
    public static final double KILL_MAX_CHANCE_MAXIMUM = 0.99f;
    public static final double KILL_MIN_CHANCE_MAXIMUM = 0.98f;

    public static final int OP_AMOUNT_TO_EXCHANGE = 60;
    public static final double ONLINE_RISE_MULTIPLIER = 0.2;
    public static final int ONLINE_UPGRADE_PRICE_DD = 10;

    public static final double BANK_CHANCE_TO_UP = 0.51f;
    public static final double BANK_MIN_COAST = 0.0000000001f;
    public static final double BANK_MAX_COAST = 0.0000010000f;
    public static final double BANK_MIN_COAST_STEP = 0.0000000001f;
    public static final double BANK_MAX_COAST_STEP = 0.0000000100f;
    public static final double BANK_START_AUTO_TAX = 0.0000000010f;
    public static final double BANK_START_MANUAL_TAX = 0.0000000101f;
    public static final double BANK_MIN_DD_SELL_COURSE = 1.5f;
    public static final double BANK_MAX_DD_SELL_COURSE = 10.5f;
    public static final double BANK_MIN_DD_SELL_COAST_STEP = 0.0000000001f;
    public static final double BANK_MAX_DD_SELL_COAST_STEP = 0.0000000100f;
    public static final double BANK_START_DD_SELL_COURSE = 2.0f;
    public static final double BANK_DD_SELL_CHANCE_TO_UP = 0.65f;
    public static final double BANK_DD_BUY_COURSE_DIFFERENCE = 1.0f;
    public static final int BANK_MS_CHANGE_TAX =  60000;
    public static final int BANK_MS_CHANGE_SYMBOL =  1000;
    public static final int BANK_MS_CHANGE_COURSE =  60000;

    public static final int CHAT_LAST_AMOUNT =  100;
    public static final int CHAT_MAX_MESSAGES=  1000;

    public static final double LOTTERY_TICKET_COAST = 0.0001f;

    public static final int SYMBOL_CHANGE_TIME = 1000;

    public static final double TRANSACTION_START_TAX = 0.145f;
    public static final double TRANSACTION_CHANCE_TO_UP = 0.50f;
    public static final double TRANSACTION_MIN_TAX = 0.0001f;
    public static final double TRANSACTION_MAX_TAX = 0.30f;
    public static final double TRANSACTION_MIN_TAX_STEP = 0.00001f;
    public static final double TRANSACTION_MAX_TAX_STEP = 1.0f;

    public static final double HIPE_90_CHANCE = 0.9;
    public static final double HIPE_90_MULTIPLIER = 1.01;
    public static final double HIPE_80_CHANCE = 0.8;
    public static final double HIPE_80_MULTIPLIER = 1.05;
    public static final double HIPE_70_CHANCE = 0.7;
    public static final double HIPE_70_MULTIPLIER = 1.10;
    public static final double HIPE_60_CHANCE = 0.6;
    public static final double HIPE_60_MULTIPLIER = 1.15;
    public static final double HIPE_50_CHANCE = 0.5;
    public static final double HIPE_50_MULTIPLIER = 1.40;
    public static final double HIPE_40_CHANCE = 0.4;
    public static final double HIPE_40_MULTIPLIER = 1.55;
    public static final double HIPE_30_CHANCE = 0.3;
    public static final double HIPE_30_MULTIPLIER = 1.70;

    public static final double AP_EXCHANGE_DC_1 = 1;
    public static final double AP_EXCHANGE_DC_10 = 15;
    public static final double AP_EXCHANGE_DC_50 = 80;
    public static final double AP_EXCHANGE_DC_100 = 200;


    public static final String MAIN_ENCODE_KEY = "fgdb53t4bvw3112efrv544tv342cr31234uk0lk776b4wcrxqxfgvht6thy6554xfgyb7buy";
    public static final String SECRET_ENCODE_KEY = "4b765hio073cx1zwdfbyn8kj4rv6j7twd2crrvvjmgr3x90yhgcx32ert7i89otyhv";
}
