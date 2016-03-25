package org.kaakaa.pptmuseum.db.document.util;

import org.apache.commons.fileupload.FileItem;
import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.document.util.JodConverter;
import org.kaakaa.pptmuseum.db.document.util.ThumbnailGenerater;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Upload info handler class.
 * <p>
 * Created by kaakaa on 16/02/18.
 */
public class RequestUtil {
    /**
     * <p>make Slide class instance of request item</p>
     *
     * @param fileItems FileItem in multipart request
     * @return Slide model
     */
    public static Slide makeSlideModel(List<FileItem> fileItems) {
        Slide slide = new Slide();
        // form data
        fileItems.stream()
                .filter(i -> i.isFormField())
                .forEach(i -> {
                    switch (i.getFieldName()) {
                        case "title":
                            slide.setTitle(encodingMultiformString(i));
                            break;
                        case "desc":
                            slide.setDescription(encodingMultiformString(i));
                            break;
                        case "tags":
                            slide.setTags(Arrays.asList(encodingMultiformString(i).split(",")));
                            break;
                    }
                });
        return slide;
    }

    /**
     * <p>Encoding form string to utf-8.</p>
     *
     * @param item form item
     * @return encoded string
     */
    private static String encodingMultiformString(FileItem item) {
        String s = null;
        try {
            s = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * <p>make Resource class instance of request item</p>
     *
     * @param item  FileItem in multipart request
     * @param slide Slide Model
     */
    public static void makeDocumentModel(FileItem item, Slide slide) {
        ResourceType resourceType = ResourceType.toSlideResource(item.getContentType());
        switch (resourceType) {
            case PDF:
                slide.setPdfResource(new Resource(resourceType, item.get()));
                break;
            case PPT:
            case PPTX:
            case PPTM:
                slide.setPowerpointResource(new Resource(resourceType, item.get()));
                slide.setPdfResource(new Resource(ResourceType.PDF, JodConverter.convertByJodConverter(item.get(), resourceType)));
                break;
            default:
                return;
        }

        // make pdf thumbnail
        byte[] bytes = ThumbnailGenerater.generate(slide.getPDFResource().getFile());
        slide.setThumbnail(new Resource(ResourceType.THUMBNAIL, bytes));
    }

}
