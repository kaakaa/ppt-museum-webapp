package org.kaakaa.pptmuseum.event.db.search;

import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.kaakaa.pptmuseum.jade.helper.ListHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaakaa on 16/03/24.
 */
public class AllSlideSearch implements Event<Map<String,Object>> {
    private final String index;

    public AllSlideSearch(String index) {
        this.index = index;
    }

    @Override
    public Map<String, Object> execute() throws EventException {
        Map<String, Object> map = new HashMap<>();
        map.put("slides", mongoDBClient.searchAll(Integer.parseInt(index), 15));
        map.put("helper", new ListHelper(mongoDBClient.allSlideSize(), 15));
        map.put("index", index);
        return map;
    }
}
