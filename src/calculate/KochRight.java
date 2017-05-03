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

/**
 *
 * @author jsf3
 */
public class KochRight implements Runnable, Observer {

    private KochFractal koch;
    private List<Edge> list;
    private List<Edge> tempList = new ArrayList<Edge>();
    
    public KochRight(List<Edge> edgelist,int level) {
        this.koch = new KochFractal();
        koch.addObserver(this);
        list = edgelist;
        koch.setLevel(level);
    }

    @Override
    public void run() {
        koch.generateRightEdge();
        list.addAll(tempList);
    }

    @Override
    public void update(Observable o, Object arg) {
        tempList.add((Edge)arg);
    }
    
}
