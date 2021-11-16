package Main;

import Entity.Consumer;
import Entity.Manufacturer;
import Enums.Party;

public class Warehouse implements Runnable {
    private final Integer WAREHOUSE_SIZE;
    private int goodsAtWarehouse;
    private final RequestStacker stacker;
    private final Manufacturer manufacturer;
    private final Consumer consumer;


    public Warehouse(int STORAGE_SIZE, RequestStacker stacker, Manufacturer manufacturer, Consumer consumer) {
        this.WAREHOUSE_SIZE = STORAGE_SIZE;
        this.stacker = stacker;
        this.manufacturer = manufacturer;
        this.consumer = consumer;
        goodsAtWarehouse = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().setName("Main.Warehouse");
                Thread.sleep(200);
                Request request = stacker.get();
                processRequest(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(Request request) {
        if (request != null) {
            if (request.getParty().equals(Party.MANUFACTURER)) {
                boolean isFull = addGoodsToStorage(request.getSize().getValue());
                if (!consumer.isActive()) {
                    consumer.start();
                    System.out.println("Consumption is activated!");
                }
                if (isFull && manufacturer.isActive()) {
                    manufacturer.stop();
                    System.out.println("Production is stopped!");
                }
            } else if (request.getParty().equals(Party.CONSUMER)) {
                boolean isEmpty = removeGoodsFromStorage(request.getSize().getValue());
                if (!manufacturer.isActive()) {
                    manufacturer.start();
                    System.out.println("Production is activated!");
                }
                if (isEmpty && consumer.isActive()) {
                    consumer.stop();
                    System.out.println("Consumption is stopped!");
                }
            }
        }
    }

    public boolean addGoodsToStorage(int goodsInRequest) {
        int freeSpace = WAREHOUSE_SIZE - goodsAtWarehouse;
        if (freeSpace > goodsInRequest) {
            goodsAtWarehouse += goodsInRequest;
            System.out.printf("Goods: +%s. Now at storage: %s. Thread: %s.\n",
                    goodsInRequest, goodsAtWarehouse, Thread.currentThread().getName());
            return false;
        } else {
            goodsAtWarehouse = WAREHOUSE_SIZE;
            System.out.printf("Goods: +%s. Storage IS FULL: %s. Thread: %s.\n",
                    freeSpace, WAREHOUSE_SIZE, Thread.currentThread().getName());
            return true;
        }
    }

    public boolean removeGoodsFromStorage(int goodsInRequest) {
        int remains = goodsAtWarehouse;
        if (remains > goodsInRequest) {
            goodsAtWarehouse -= goodsInRequest;
            System.out.printf("Goods: -%s. Now at storage: %s. Thread: %s.\n",
                    goodsInRequest, goodsAtWarehouse, Thread.currentThread().getName());
            return false;
        } else {
            goodsAtWarehouse = 0;
            System.out.printf("Goods: -%s. Storage IS EMPTY: 0. Thread: %s.\n",
                    remains, Thread.currentThread().getName());
            return true;
        }
    }
}