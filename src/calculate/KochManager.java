/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

/**
 *
 * @author jsf3
 */
public class KochManager  {
    
    private JSF31KochFractalFX application;
    private KochFractal koch;
    private List<Edge> list;

    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.koch = new KochFractal();
        list = Collections.synchronizedList(new ArrayList());
    }


    public void changeLevel(int nxt) {
        list.clear();
        koch.setLevel(nxt);
        TimeStamp time = new TimeStamp();
        time.setBegin();
        
//threads
    //maakt de threadpool
        ExecutorService executor = Executors.newFixedThreadPool(10);
    //maakt de lijst met future objecten
        Future<List<Edge>> leftCall;
        Future<List<Edge>> rightCall;
        Future<List<Edge>> bottomCall;
    //maakt een instantie van de callable class
        KochLeftCallable klc = new KochLeftCallable(list,nxt);
        KochRightCallable krc = new KochRightCallable(list,nxt);
        KochBottomCallable kbc = new KochBottomCallable(list,nxt);
        
        
        leftCall = executor.submit(klc);
        rightCall = executor.submit(krc);
        bottomCall = executor.submit(kbc);
        
        
        try {
            list.addAll(leftCall.get());
            list.addAll(rightCall.get());
            list.addAll(bottomCall.get());
        } catch (InterruptedException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(KochManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        application.setTextCalc(time.toString());
        //join gaat toch pas verder als alle threads af zijn dus ik hoef geen COUNT bij te gaan houden.
        application.requestDrawEdges();
        
        
        System.out.println(list.size());
    }
    
    public void drawEdges(){
        TimeStamp time = new TimeStamp();
        time.setBegin();
        application.clearKochPanel();
        for(Edge e : list){
            application.drawEdge(e);
        }
        time.setEnd();
        application.setTextDraw(time.toString());
        application.setTextNrEdges(Integer.toString(koch.getNrOfEdges()));
    }
    

}
