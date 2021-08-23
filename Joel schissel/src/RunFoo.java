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
        setOnSucceeded(e -> {
            if ((boolean) e.getSource().getValue()) {
                boo.set((boolean) e.getSource().getValue());
                runBol.set(false);
                statProp.set("Found");
            } else if (runBol.get()) {
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
                if (!bool) {
                    try {
                        return imagefinder.klick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

        };

    }
}
