package org.kaakaa.pptmuseum.jade.helper

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by kaakaa_hoe on 2016/04/10.
 */
class JadeHelperTest extends Specification {
    private JadeHelper target;

    def "EachSlice"() {
        setup:
        target = new JadeHelper(0, 0)

        expect:
        target.eachSlice(list, n) == result

        where:
        list                 | n  | result
        [1]                  | 1 || [[1]]
        [1]                  | 3 || [[1]]
        [1, 2, 3]            | 1 || [[1], [2], [3]]
        [1, 2, 3]            | 2 || [[1, 2], [3]]
        [1, 2, 3]            | 3 || [[1, 2, 3]]
        ["1", "2", "3", "4"] | 3 || [["1", "2", "3"], ["4"]]
    }

    @Unroll
    def "GetCeilSize: #numOfSlide / #limit"() {
        setup:
        target = new JadeHelper(numOfSlide, limit);

        expect:
        target.getCeilSize() == result

        where:
        numOfSlide | limit || result
        -1         | -1    || 1
        -1         | 1     || 1
        1          | -1    || 1
        0          | 0     || 1
        0          | 1     || 1
        1          | 0     || 1
        1          | 10    || 1
        2          | 2     || 1
        3          | 2     || 2
        100        | 15    || 7
    }

    def "GetPagination: #numOfSlide / #limit"() {
        setup:
        target = new JadeHelper(numOfSlide, limit);

        expect:
        target.getPagination() == result

        where:
        numOfSlide | limit || result
        -1         | -1    || [1]
        0          | 0     || [1]
        1          | 1     || [1]
        2          | 1     || [1, 2]
        10         | 2     || [1, 2, 3, 4, 5]
        11         | 2     || [1, 2, 3, 4, 5, 6]
    }
}
