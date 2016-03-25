package org.kaakaa.pptmuseum.event.db.document;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.document.util.RequestParser;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import spark.Request;

import java.util.List;

/**
 * Created by kaakaa on 16/03/19.
 */
public class UploadSlide implements Event {

    private final Request request;

    public UploadSlide(Request rq) {
        this.request = rq;
    }

    @Override
    public Object execute() throws EventException {
        // parse request
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> fileItems = null;
        try {
            fileItems = servletFileUpload.parseRequest(this.request.raw());
        } catch (FileUploadException e) {
            throw new EventException("Upload File Error", e);
        }

        Slide slide = RequestParser.parse(fileItems);
        return mongoDBClient.upload(slide);
    }
}
