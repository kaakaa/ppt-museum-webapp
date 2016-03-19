package org.kaakaa.pptmuseum.event;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kaakaa.pptmuseum.db.document.Slide;
import spark.Request;

import java.util.List;

import static org.kaakaa.pptmuseum.http.RequestUtil.makeDocumentModel;
import static org.kaakaa.pptmuseum.http.RequestUtil.makeSlideModel;

/**
 * Created by kaakaa on 16/03/19.
 */
public class UploadDocument implements Event {

    private final Request request;

    public UploadDocument(Request rq) {
        this.request = rq;
    }

    @Override
    public void execute() throws EventException {
        // parse request
        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> fileItems = null;
        try {
            fileItems = servletFileUpload.parseRequest(this.request.raw());
        } catch (FileUploadException e) {
            throw new EventException("Upload File Error", e);
        }

        // make Slide model
        Slide slide = makeSlideModel(fileItems);

        // make Resource model
        fileItems.stream()
                .filter(i -> !i.isFormField())
                .findFirst()
                .ifPresent(i -> {
                    makeDocumentModel(i, slide);
                    // upload document
                    mongoDBClient.upload(slide);
                });

    }
}
