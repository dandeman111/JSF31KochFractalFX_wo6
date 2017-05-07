/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        final Future<List<Edge>> leftCall;
        final Future<List<Edge>> rightCall;
        final Future<List<Edge>> bottomCall;
    //maakt een instantie van de callable class
        KochLeftTask klt = new KochLeftTask(list,nxt,this,application.pbLeft);
        KochRightTask  krt = new KochRightTask(list,nxt);
        KochBottomTask kbt = new KochBottomTask(list,nxt);


        executor.submit(kbt);
        executor.submit(krt);
        executor.submit(klt);


        




        application.setTextCalc(time.toString());
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

    public synchronized void drawOneEdge(){
        for(Edge e : this.list){
            Color color = e.color;
            e.color = Color.WHITE;
            this.application.drawEdge(e);
            e.color = color;
        }
    }
    public synchronized void addEdge(Edge e){
        list.add(e);
    }

}
