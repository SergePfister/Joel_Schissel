package src;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class RunFoo extends Service<Boolean> {

    private ImageFinder imagefinder;

    public RunFoo(boolean boo) {
        imagefinder = new ImageFinder();
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {

            @Override
            protected Boolean call() throws Exception {
                System.out.println(Thread.currentThread().getName() + "im Running");
                boolean result;
                try {
                    result =imagefinder.klick();
                    System.out.println(Thread.currentThread().getName() + "Im done");
                    return result;
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + e.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + "Im done / No Result");
                return null;
            }

        };

    }
}
