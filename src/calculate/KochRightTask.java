package calculate;

import javafx.concurrent.Task;

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

    public KochRightTask(List<Edge> list, int level) {
        this.koch = new KochFractal();
        koch.addObserver(this);
        this.list = list;
        tempList = new ArrayList<Edge>();
        koch.setLevel(level);
    }


    @Override
    public List<Edge> call() throws Exception{
        koch.generateRightEdge();
        list.addAll(tempList);
        return list;
    }

    @Override
    public void update(Observable o, Object arg) {
        tempList.add((Edge)arg);
    }

}
