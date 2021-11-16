package Entity;

import Enums.Party;
import Enums.Size;
import Main.Request;
import Main.RequestStacker;

import java.util.Random;

public class Manufacturer extends Entity {
    public Manufacturer(RequestStacker stacker) {
        super(stacker);
    }

    @Override
    public void run() {
        while (true) {
            if (isActive()) {
                Thread.currentThread().setName("Manufacturer");
                getStacker().add(makeRequest());
            }
            try {
                Thread.sleep(getPeriod());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Request makeRequest() {
        Request request = new Request(Party.MANUFACTURER, randomSize());
        //System.out.println("New request created: " + request.getParty() + " " + request.getSize().getValue());
        return request;
    }

    private Size randomSize() {
        Random random = new Random();
        return Size.values()[random.nextInt(Size.values().length)];
    }
}
