package org.kaakaa.pptmuseum.db.document.util;

import org.apache.commons.fileupload.FileItem;
import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Request information handler class.
 * <p>
 * Created by kaakaa on 16/02/18.
 */
public class RequestParser {

    public static Slide parse(List<FileItem> fileItems) {
        final BiConsumer<FileItem, Slide> parseItem = (i, s) -> {
            if (i.isFormField()) {
                parseFormField(i, s);
            } else {
                parseMultipartItem(i, s);
            }
        };

        Slide slide = new Slide();
        fileItems.stream()
                .forEach(item -> parseItem.accept(item, slide));

        return slide;
    }

    /**
     * <p>Parse form field item.</p>
     *
     * @param item form field item
     * @param slide slide model
     */
    private static void parseFormField(FileItem item, Slide slide) {
        switch (item.getFieldName()) {
            case "title":
                slide.setTitle(encodingMultiformString(item));
                break;
            case "desc":
                slide.setDescription(encodingMultiformString(item));
                break;
            case "tags":
                slide.setTags(Arrays.asList(encodingMultiformString(item).split(",")));
                break;
        }
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
    public static void parseMultipartItem(FileItem item, Slide slide) {
        ResourceType resourceType = ResourceType.toSlideResource(item.getContentType());
        switch (resourceType) {
            case PDF:
                slide.setPdfResource(new Resource(resourceType, item.get()));
                break;
            case PPT:
            case PPTX:
            case PPTM:
                slide.setPowerpointResource(new Resource(resourceType, item.get()));
                // convert powerpoint to pdf by JODConverter
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
