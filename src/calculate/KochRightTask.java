package calculate;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dande on 3-5-2017.
 */
public class KochRightTask extends Task<List> implements Observer {
    private KochFractal koch;
    private List<Edge> list;
    private List<Edge> tempList = new ArrayList<Edge>();
    private KochManager kochManager;
    private int edgesCal;
    public ProgressBar progressBar;

    public KochRightTask(List<Edge> list, int level, KochManager kochManager, ProgressBar pb) {
        this.kochManager = kochManager;
        this.koch = new KochFractal();
        koch.addObserver(this);
        this.list=list;
        tempList = new ArrayList<Edge>();
        koch.setLevel(level);
        edgesCal = 0;
        pb.progressProperty().bind(this.progressProperty());

    }


    @Override
    public List<Edge> call() throws Exception{
        koch.generateRightEdge();
        list.addAll(tempList);
        return list;
    }

    @Override
    public void update(Observable o, Object arg) {
        kochManager.addEdge((Edge) arg);
        edgesCal++;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                kochManager.drawOneEdge();
                if (progressBar != null) {
                    updateProgress(edgesCal, koch.getNrOfEdges() / 3);
                    updateMessage(edgesCal + " / " + koch.getNrOfEdges() / 3);
                }
            }
        });
        tempList.add((Edge) arg);
        try {
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            super.cancelled();
        }
    }

}
