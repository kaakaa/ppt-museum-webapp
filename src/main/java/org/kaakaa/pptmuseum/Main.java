package org.kaakaa.pptmuseum;

import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.kaakaa.pptmuseum.event.db.slide.DeleteSlide;
import org.kaakaa.pptmuseum.event.db.slide.UpdateSlide;
import org.kaakaa.pptmuseum.event.db.slide.UploadSlide;
import org.kaakaa.pptmuseum.event.db.resource.GetResource;
import org.kaakaa.pptmuseum.event.db.search.AllSlideSearch;
import org.kaakaa.pptmuseum.event.db.search.TagSearch;
import org.kaakaa.pptmuseum.event.EventExecuter;
import org.kaakaa.pptmuseum.jade.JadePages;
import spark.ModelAndView;
import spark.Response;
import spark.template.jade.JadeTemplateEngine;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static spark.Spark.*;

/**
 * Created by kaakaa on 16/02/09.
 */
public class Main {
    public static void main(String[] args) throws TimeoutException, UnknownHostException {
        port(80);
        staticFileLocation("/public");

        // top page
        get("/", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum/slides", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum/slides/:index", (rq, rs) -> getTopPageView(rq.params("index")), new JadeTemplateEngine());
        get("/ppt-museum/keywords/:key", (rq, rs) -> getTagSearchResultView(rq.params("key")), new JadeTemplateEngine());

        // upload page
        post("/ppt-museum/upload", (rq, rs) -> {
            Event uploadEvent = new UploadSlide(rq);
            EventExecuter.execute(uploadEvent);
            return redirectToTop(rs);
        });

        // delete slide
        delete("/ppt-museum/slide/:id", (rq, rs) -> {
            Event deleteEvent = new DeleteSlide(rq);
            EventExecuter.execute(deleteEvent);
            return 0;
        });

        // update slide info
        post("/ppt-museum/slide/:id", (rq,rs) -> {
            Event updateDocument = new UpdateSlide(rq);
            EventExecuter.execute(updateDocument);
            return redirectToTop(rs);
        });

        // slide view page
        get("/ppt-museum/slide/:id", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", rq.params("id"));
            return new ModelAndView(map, JadePages.PRESENTATION.getTemplatePath());
        }, new JadeTemplateEngine());

        // get resource file
        get("/ppt-museum/resource/pdf/:id", (rq, rs) -> {
            Event<Resource> getResource = new GetResource(rq, ResourceType.PDF);
            Resource resource = EventExecuter.execute(getResource);

            rs.type(resource.getContentType());
            return resource.getFile();
        });
        get("/ppt-museum/resource/powerpoint/:id", (rq, rs) -> {
            Event<Resource> getResource = new GetResource(rq, ResourceType.PPT);
            Resource resource = EventExecuter.execute(getResource);

            rs.type(resource.getContentType());
            return resource.getFile();
        });
        get("/ppt-museum/resource/thumbnail/:id", (rq, rs) -> {
            Event<Resource> getResource = new GetResource(rq, ResourceType.THUMBNAIL);
            Resource resource = EventExecuter.execute(getResource);

            rs.type(resource.getContentType());
            return resource.getFile();
        });
    }

    /**
     * <p>Get view page filtered tag</p>
     *
     * @param tag tag word
     * @return tag serach view
     * @throws EventException search exception
     */
    private static ModelAndView getTagSearchResultView(String tag) throws EventException {
        Event<Map<String, Object>> searchEvent = new TagSearch(tag);
        Map<String, Object> map = EventExecuter.execute(searchEvent);
        return new ModelAndView(map, JadePages.TAG_SEARCH.getTemplatePath());
    }

    /**
     * <p>Get top page view</p>
     *
     * @param index pagenation index
     * @return indexed page view
     * @throws EventException get exception
     */
    private static ModelAndView getTopPageView(String index) throws EventException {
        Event<Map<String, Object>> searchAll = new AllSlideSearch(index);
        Map<String, Object> map = EventExecuter.execute(searchAll);
        return new ModelAndView(map, JadePages.TOP.getTemplatePath());
    }

    /**
     * <p>Set redirecting to top page to response object</p>
     * @param rs response
     * @return dummy string
     */
    private static String redirectToTop(Response rs) {
        rs.status(302);
        rs.header("Location", "/");
        return "redirect to /";
    }
}
