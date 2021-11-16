package Main;

import Entity.Consumer;
import Entity.Manufacturer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        RequestStacker requestStacker = new RequestStacker();

        Manufacturer manufacturer = new Manufacturer(requestStacker);
        Consumer consumer = new Consumer(requestStacker);

        Warehouse warehouse = new Warehouse(60, requestStacker, manufacturer, consumer);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        service.execute(manufacturer);
        service.execute(consumer);
        service.execute(warehouse);

        service.shutdown();
    }
}
