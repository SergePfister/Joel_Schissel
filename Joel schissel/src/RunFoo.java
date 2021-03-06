package src;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RunFoo extends Service<Boolean> {

    private ImageFinder imagefinder;
    boolean bool;
    String path;
    int inaccuracy;

    public RunFoo(BooleanProperty boo, BooleanProperty runBol, StringProperty statProp,String path, int inaccuracy) {
        bool = boo.get();
        this.inaccuracy = inaccuracy;
        this.path=path;
        imagefinder = new ImageFinder();
        setOnSucceeded(e -> {
            if ((boolean) e.getSource().getValue()) {
                boo.set((boolean) e.getSource().getValue());
                runBol.set(false);
                statProp.set("Found");
            } else if (runBol.get()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                new RunFoo(boo, runBol, statProp,path,inaccuracy).start();
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
                        return imagefinder.klick(path,inaccuracy);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

        };

    }
}
