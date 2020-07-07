package tasks.input;

import entities.DBConnector;
import tasks.Task;

import static engine.Prop.*;

public class HipeTask implements Task {

    Offer offer;
    String address;
    double dc;

    public HipeTask(Offer offer, String address, double dc) {
        this.offer = offer;
        this.address = address;
        this.dc = dc;
    }


    @Override
    public boolean try_to_complete() {
       return DBConnector.getInstance().HipeTask(address, dc, offer.chance, offer.multiplier, get_info());
    }

    @Override
    public String get_info() {
        String out = "";
        out+=address+" - "+offer+" - "+dc;
        return out;
    }

    public enum Offer{
        HIPE_90(HIPE_90_CHANCE,HIPE_90_MULTIPLIER),
        HIPE_80(HIPE_80_CHANCE,HIPE_80_MULTIPLIER),
        HIPE_70(HIPE_70_CHANCE,HIPE_70_MULTIPLIER),
        HIPE_60(HIPE_60_CHANCE,HIPE_60_MULTIPLIER),
        HIPE_50(HIPE_50_CHANCE,HIPE_50_MULTIPLIER),
        HIPE_40(HIPE_40_CHANCE,HIPE_40_MULTIPLIER),
        HIPE_30(HIPE_30_CHANCE,HIPE_30_MULTIPLIER);

        double chance;
        double multiplier;

        Offer(double chance, double multiplier) {
            this.chance = chance;
            this.multiplier = multiplier;
        }

    }
}
