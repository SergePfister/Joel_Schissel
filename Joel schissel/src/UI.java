package src;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class UI extends VBox {
    StringProperty statusProp;
    Label status;
    BooleanProperty runBol;
    BooleanProperty found;
    Slider inaccuracy;
    HBox hBox1;
    HBox hBox2;
    Button run;
    Button stop;
    Button restart;
    Label label;
    Label labeltxt;
    private final IntegerProperty inaccuracyProp;
    private double insets = 20;

    public UI(double width, double height) {
        inaccuracyProp = new SimpleIntegerProperty(0);
        runBol = new SimpleBooleanProperty(false);
        found = new SimpleBooleanProperty(false);
        initializeControlls();
        layoutControlls(width, height);
    }

    private void layoutControlls(double width, double height) {
        hBox1.setMinSize(width, height / 3);
        hBox2.setMinSize(width, height / 3);

        run.setMinSize(width / 3, height / 6);
        stop.setMinSize(width / 3, height / 6);
        status.setMinSize(width / 3, height / 6);

        run.setPadding(new Insets(insets));
        stop.setPadding(new Insets(insets));
        status.setPadding(new Insets(insets));

        inaccuracy.setShowTickMarks(true);
        inaccuracy.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        inaccuracy.setShowTickLabels(true);
        inaccuracy.showTickLabelsProperty().set(true);
        inaccuracy.setMajorTickUnit(500);
        inaccuracy.setPadding(new Insets(insets));

        label.setPadding(new Insets(insets));
        label.setMinSize(width / 2, height / 6);
        labeltxt.setPadding(new Insets(insets));
        labeltxt.setMinSize(width / 2, height / 6);

        hBox1.getChildren().addAll(run, stop, status);
        hBox2.getChildren().addAll(labeltxt, label);
        this.getChildren().addAll(hBox1, hBox2, inaccuracy);

    }

    private void initializeControlls() {
        statusProp = new SimpleStringProperty("Stoped");
        status = new Label();
        status.textProperty().bindBidirectional(statusProp);
        labeltxt = new Label("Inaccuracy");
        label = new Label(0.0 + "");
        hBox1 = new HBox();
        hBox2 = new HBox();
        inaccuracy = new Slider(0, 101, 0);
        run = new Button("Run");
        stop = new Button("Stop");
        restart = new Button("Restart");
        label.textProperty().bindBidirectional(inaccuracyProp, new NumberStringConverter());
        inaccuracy.valueProperty().addListener((target, newV, oldV) -> {
            inaccuracyProp.set(newV.intValue());
        });
        run.setOnAction(e -> {
            if (runBol.get()) {
                System.out.println("im still running");
                statusProp.set("Still Running");
            } else {
                statusProp.set("Running");
                found.set(false);
                runBol.set(true);
                new RunFoo(found, runBol, statusProp).start();
            }
        });
        stop.setOnAction(e -> {
            statusProp.set("Stoped");
            runBol.set(false);
            found.set(false);
        });
    }
}
