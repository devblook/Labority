package me.bryang.workity.data.jobs;

public class BlockJobData {

    private final int gainXP;
    private final int gainMoney;


    public BlockJobData(int gainMoney, int gainXP) {

        this.gainMoney = gainMoney;
        this.gainXP = gainXP;


    }

    public int getGainXP() {
        return gainXP;
    }


    public int getGainMoney() {
        return gainMoney;
    }


}
