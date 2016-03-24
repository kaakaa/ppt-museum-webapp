package org.kaakaa.pptmuseum;

import org.kaakaa.pptmuseum.db.MongoDBClient;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.document.UpdateDocument;
import org.kaakaa.pptmuseum.event.document.UploadDocument;
import org.kaakaa.pptmuseum.event.document.DeleteDocument;
import org.kaakaa.pptmuseum.event.execute.EventExecuter;
import org.kaakaa.pptmuseum.jade.JadePages;
import org.kaakaa.pptmuseum.jade.ListHelper;
import spark.ModelAndView;
import spark.Response;
import spark.template.jade.JadeTemplateEngine;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static spark.Spark.*;

/**
 * Created by kaakaa on 16/02/09.
 */
public class Main {
    /**
     * MongoDB client
     */
    private static final MongoDBClient mongoDBClient = new MongoDBClient();

    public static void main(String[] args) throws TimeoutException, UnknownHostException {
        port(80);
        staticFileLocation("/public");

        // top page
        get("/", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum/slides", (rq, rs) -> getTopPageView("1"), new JadeTemplateEngine());
        get("/ppt-museum/slides/:index", (rq, rs) -> getTopPageView(rq.params("index")), new JadeTemplateEngine());
        get("/ppt-museum/keywords/:key", (rq, rs) -> getFilteredPageView(rq.params("key")), new JadeTemplateEngine());

        // upload page
        post("/ppt-museum/upload", (rq, rs) -> {
            Event uploadEvent = new UploadDocument(rq);
            EventExecuter.execute(uploadEvent);
            return redirectToTop(rs);
        });

        // delete slide
        delete("/ppt-museum/slide/:id", (rq, rs) -> {
            Event deleteEvent = new DeleteDocument(rq);
            EventExecuter.execute(deleteEvent);
            return 0;
        });

        // update slide info
        post("/ppt-museum/slide/:id", (rq,rs) -> {
            Event updateDocument = new UpdateDocument(rq);
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
            Resource resource = mongoDBClient.getPDF(rq.params(":id"));
            rs.type(resource.getContentType());
            return resource.getFile();
        });
        get("/ppt-museum/resource/powerpoint/:id", (rq, rs) -> {
            Resource resource = mongoDBClient.getPowerpoint(rq.params(":id"));
            rs.type(resource.getContentType());
            return resource.getFile();
        });
        get("/ppt-museum/resource/thumbnail/:id", (rq, rs) -> {
            Resource resource = mongoDBClient.getThumbnail(rq.params("id"));
            rs.type(resource.getContentType());
            return resource.getFile();
        });
    }

    private static ModelAndView getFilteredPageView(String keyword) {
        Map<String, Object> map = new HashMap<>();
        List<Slide> result = mongoDBClient.searchFilteredKeyword(keyword);
        map.put("slides", result);
        map.put("helper", new ListHelper(result.size(), 99));
        map.put("index", 1);
        map.put("keyword", keyword);
        return new ModelAndView(map, JadePages.TAG_SEARCH.getTemplatePath());
    }

    private static ModelAndView getTopPageView(String index) {
        int i = 0;
        try {
            i = Integer.parseInt(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("slides", mongoDBClient.searchAll(i, 15));
        map.put("helper", new ListHelper(mongoDBClient.allSlideSize(), 15));
        map.put("index", index);
        return new ModelAndView(map, JadePages.TOP.getTemplatePath());
    }

    private static String redirectToTop(Response rs) {
        rs.status(302);
        rs.header("Location", "/");
        return "redirect to /";
    }
}
