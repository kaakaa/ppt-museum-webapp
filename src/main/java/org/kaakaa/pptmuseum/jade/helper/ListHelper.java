package org.kaakaa.pptmuseum.jade.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by kaakaa on 16/02/27.
 */
public class ListHelper {
    private final int numOfSlide;
    private final int pageLimit;

    public ListHelper(int numOfSlide, int pageLimit) {
        this.numOfSlide = numOfSlide;
        this.pageLimit = pageLimit;
    }

    public <T> List<List<T>> eachSlice(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        List<T> l = new ArrayList<>();
        for(T t: list) {
            l.add(t);
            if(l.size() == 3) {
                result.add(l);
                l = new ArrayList<>();
            }
        }
        result.add(l);
        return result;
    }

    public int getCeilSize(){
        if(this.numOfSlide == 0){
            return 1;
        }
        return ((int) this.numOfSlide / this.pageLimit) + 1;
    }

    public int[] getPagination() {
        return IntStream.rangeClosed(1, getCeilSize()).toArray();
    }
}
