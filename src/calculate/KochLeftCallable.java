/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 *
 * @author dande
 */
public class KochLeftCallable implements Callable<List<Edge>>, Observer {
    private KochFractal koch;
    private List<Edge> list;
    private List<Edge> tempList = new ArrayList<Edge>();

    public KochLeftCallable( List<Edge> list, int level) {
        this.koch = new KochFractal();
        koch.addObserver(this);
        this.list = list;
        tempList = new ArrayList<Edge>();
        koch.setLevel(level);
    }
    
    
    @Override
    public List<Edge> call() throws Exception{
        koch.generateLeftEdge();
        list.addAll(tempList);
        return list;
    }

    @Override
    public void update(Observable o, Object arg) {
    tempList.add((Edge)arg);
    }
    
}
