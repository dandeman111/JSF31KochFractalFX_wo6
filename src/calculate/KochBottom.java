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
public class KochBottom implements Runnable, Observer {
    
    private KochFractal koch;
    private List<Edge> list;
    private List<Edge> templist = new ArrayList<Edge>();
    
    public KochBottom(List<Edge> edgList,int level) {
        this.koch = new KochFractal();
        koch.addObserver(this);
        list = edgList;
        koch.setLevel(level);
    }

    @Override
    public void run() {
        koch.generateBottomEdge();
        list.addAll(templist);
    }

    @Override
    public void update(Observable o, Object arg) {
        templist.add((Edge)arg);
    }
    
}
