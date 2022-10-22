package me.bryang.labority.data;

public class JobData {

    private final String jobName;

    public JobData(String jobName){
        this.jobName = jobName;
    }

    private int xpPoints = 0;
    private int maxXP = 0;

    private int level = 1;

    public String getName(){
        return jobName;
    }

    public void addXPPoints(){
        xpPoints++;
    }


    public void removeXPPoints(){
        xpPoints--;
    }

    public int getXpPoints(){
        return xpPoints;
    }

    public void addLevel(){
        level++;
    }

    public void removeLevel(){
        level--;
    }

    public int getLevel(){
        return level;
    }

    public void setXPPoints(int xpPoints){
        this.xpPoints = xpPoints;
    }


    public void setLevel(int level){
        this.level = level;
    }

    public int getMaxXP(){
        return maxXP;
    }

    public void setMaxXP(int maxXP){
        this.maxXP = maxXP;
    }
}
