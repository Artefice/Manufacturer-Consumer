package Entity;

import Main.Request;
import Main.RequestStacker;

public abstract class Entity implements Runnable {
    private static final int PERIOD = 150;
    private boolean isActive;
    private RequestStacker stacker;


    public Entity(RequestStacker stacker) {
        isActive = true;
        this.stacker = stacker;
    }

    public void start() {
        isActive = true;
    }

    public void stop() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getPeriod() {
        return PERIOD;
    }

    public RequestStacker getStacker() {
        return stacker;
    }

    public abstract Request makeRequest();
}
