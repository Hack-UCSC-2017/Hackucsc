package com.SteadyView.SteadyView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by cole on 1/21/17.
 */

public class RollingQueue {
    int maxsize;
    double datasum;
    Deque data;

    public RollingQueue(int size){
        data = new ArrayDeque<Double>(size);
        maxsize = size;
        datasum = 0;
    }

    public void push(double d) {
        data.addFirst(d);
        datasum += d;
        if(data.size() > maxsize){
            datasum -= (double)data.removeLast();
        }
    }

    public double average(){
        //double avg = 0;

        //System.out.println("avg: "+avg/data.size() +","+ data.size());
        return datasum/data.size();
    }

    public double size(){
        return data.size();
    }

    public double[] getArray(){
        double[] array = new double[data.size()];
        int count = 0;
        for (Iterator<Double> i = data.iterator(); i.hasNext();) {
            array[count++] = i.next();
        }
        return array;
    }

}