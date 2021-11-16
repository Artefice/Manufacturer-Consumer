package Main;

import java.util.ArrayList;
import java.util.List;

public class RequestStacker {
    private static final int MAX_REQUESTS = 10;
    private static final int MIN_REQUESTS = 0;
    private List<Request> requests;
    private int requestCounter = 0;


    public RequestStacker() {
        requests = new ArrayList<>();
    }

    public synchronized boolean add(Request element) {
        try {
            if (requestCounter < MAX_REQUESTS) {
                notifyAll();
                requests.add(element);
                requestCounter++;
                String info = String.format("New request placed: %s %s %s. Requests: %s.",
                        element.getParty(), element.getSize(), Thread.currentThread().getName(), requests.size());
                System.out.println(info);
            } else {
                System.out.println("Requests: " + requests.size() + " There is no place for new requests. "
                        + Thread.currentThread().getName());
                wait();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public synchronized Request get() {
        try {
            if (requestCounter > MIN_REQUESTS) {
                notifyAll();
                Request request = requests.get(0);
                requests.remove(request);
                requestCounter--;
                System.out.printf("Main.Request was removed: %s %s. Thread: %s.\n",
                        request.getParty(), request.getSize(), Thread.currentThread().getName());
                return request;
            }
            System.out.println("There is no requests.");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
