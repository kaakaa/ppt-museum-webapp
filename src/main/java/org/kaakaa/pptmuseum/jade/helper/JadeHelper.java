package org.kaakaa.pptmuseum.jade.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Helper methods used in Jade template
 * <p>
 * Created by kaakaa on 16/02/27.
 */
public class JadeHelper {
    /* The number of uploaded slides */
    private final int numOfSlide;
    /* The number of slides per pagination */
    private final int pageLimit;

    /**
     * Constructor
     *
     * @param numOfSlide The number os uploaded slides
     * @Param pageLimit The number of slides per pagination
     */
    public JadeHelper(int numOfSlide, int pageLimit) {
        this.numOfSlide = numOfSlide;
        this.pageLimit = pageLimit;
    }

    /**
     * Groups items into chunk of the given size.
     *
     * @param list group items
     * @param n    given size
     * @param <T>  type of group items
     * @return chunk list
     */
    public <T> List<List<T>> eachSlice(List<T> list, int n) {
        List<List<T>> result = new ArrayList<>();
        List<T> l = new ArrayList<>();
        for (T t : list) {
            l.add(t);
            if (l.size() == n) {
                result.add(l);
                l = new ArrayList<>();
            }
        }
        if (l.size() > 0) {
            result.add(l);
        }
        return result;
    }

    /**
     * Get maximum pagination
     *
     * @return maximum pagination
     */
    public int getCeilSize() {
        if (this.numOfSlide <= 0 || this.pageLimit <= 0) {
            return 1;
        }
        return (int) Math.ceil((double) this.numOfSlide / this.pageLimit);
    }

    /**
     * Get pagination's array
     *
     * @return array
     */
    public int[] getPagination() {
        return IntStream.rangeClosed(1, getCeilSize()).toArray();
    }
}
