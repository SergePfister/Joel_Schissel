package src;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RunFoo extends Service<Boolean> {

    private ImageFinder imagefinder;
    boolean bool;

    public RunFoo(BooleanProperty boo, BooleanProperty runBol, StringProperty statProp) {
        bool = boo.get();
        imagefinder = new ImageFinder();
        setOnCancelled(e -> {
            System.out.println("Cancelled");
        });
        setOnFailed(e -> {
            System.out.println("Failed");
        });
        setOnSucceeded(e -> {
            if ((boolean) e.getSource().getValue()) {
                System.out.println("Succeeded and Found");
                boo.set((boolean) e.getSource().getValue());
                runBol.set(false);
                statProp.set("Found");
            } else if (runBol.get()) {
                System.out.println("Succeeded but not Found");
                new RunFoo(boo, runBol, statProp).start();
                ;
            }
        });

    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {

            @Override
            protected Boolean call() throws Exception {
                if (bool) {
                    System.out.println("Alredy Found");
                } else {
                    System.out.println(Thread.currentThread().getName() + "im Running");
                    boolean result;
                    try {
                        result = imagefinder.klick();
                        System.out.println(Thread.currentThread().getName() + "Im done");
                        return result;
                    } catch (Exception e) {
                        System.out.println(Thread.currentThread().getName() + e.getMessage());
                    }
                }
                System.out.println(Thread.currentThread().getName() + "Im done / No Result");
                return null;
            }

        };

    }
}
