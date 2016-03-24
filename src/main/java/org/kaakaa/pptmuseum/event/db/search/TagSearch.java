package org.kaakaa.pptmuseum.event.db.search;

import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.kaakaa.pptmuseum.jade.ListHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaakaa on 16/03/24.
 */
public class TagSearch implements Event<Map<String, Object>> {

    private final String keyword;

    public TagSearch(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Map<String, Object> execute() throws EventException {
        Map<String, Object> map = new HashMap<>();
        List<Slide> result = mongoDBClient.searchFilteredKeyword(keyword);
        map.put("slides", result);
        map.put("helper", new ListHelper(result.size(),99));
        map.put("index", 1);
        map.put("keyword", keyword);
        return map;
    }

}
