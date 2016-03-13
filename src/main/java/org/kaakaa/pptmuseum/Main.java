package org.kaakaa.pptmuseum;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kaakaa.pptmuseum.db.MongoDBClient;
import org.kaakaa.pptmuseum.db.document.Document;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.http.RequestUtil;
import org.kaakaa.pptmuseum.jade.ListHelper;
import spark.ModelAndView;
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
        get("/ppt-museum/upload", (rq, rs) -> new ModelAndView(new HashMap(), "upload"), new JadeTemplateEngine());
        post("/ppt-museum/upload", (rq, rs) -> {
            // parse request
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> fileItems = servletFileUpload.parseRequest(rq.raw());

            // make Slide model
            Slide slide = RequestUtil.makeSlideModel(fileItems);

            // make Document model
            fileItems.stream()
                    .filter(i -> !i.isFormField())
                    .findFirst()
                    .ifPresent(i -> {
                        RequestUtil.makeDocumentModel(i, slide)
                                // upload document
                                .ifPresent(d -> mongoDBClient.upload(slide, d));
                    });
            rs.status(302);
            rs.header("Location", "/");
            return "redirect to /";
        });

        // delete slide
        delete("/ppt-museum/slide/delete/:id", (rq, rs) -> {
            mongoDBClient.delete(rq.params("id"));
            return 0;
        });

        // slide view page
        get("/ppt-museum/slide/:id", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("id", rq.params("id"));
            return new ModelAndView(map, "slide");
        }, new JadeTemplateEngine());
        get("/ppt-museum/document/pdf/:id", (rq, rs) -> {
            Document document = mongoDBClient.getPDF(rq.params(":id"));
            rs.type(document.getContentType());
            return document.getFile();
        });
        get("/ppt-museum/thumbnail/:id", (rq, rs) -> {
            byte[] file = mongoDBClient.getThumbnail(rq.params("id"));
            rs.type("image/png");
            rs.header("Content-length", String.valueOf(file.length));
            rs.raw().getOutputStream().write(file);
            rs.raw().getOutputStream().flush();
            rs.raw().getOutputStream().close();
            return rs.raw();
        });
    }

    private static ModelAndView getFilteredPageView(String keyword) {
        Map<String, Object> map = new HashMap<>();
        List<Slide> result = mongoDBClient.searchFilteredKeyword(keyword);
        map.put("slides", result);
        map.put("helper", new ListHelper(result.size(), 99));
        map.put("index", 1);
        map.put("keyword", keyword);
        return new ModelAndView(map, "search_result");
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
        return new ModelAndView(map, "ppt-museum");
    }
}
