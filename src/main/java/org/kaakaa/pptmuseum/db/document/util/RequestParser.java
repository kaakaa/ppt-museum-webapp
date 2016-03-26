package org.kaakaa.pptmuseum.db.document.util;

import org.apache.commons.fileupload.FileItem;
import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Slide;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Request information handler class.
 * <p>
 * Created by kaakaa on 16/02/18.
 */
public class RequestParser {

    public static Slide parse(List<FileItem> fileItems) {
        // define parse function
        final BiConsumer<FileItem, SlideBuilder> parseItem = (i, sb) -> {
            if (i.isFormField()) {
                parseFormField(i, sb);
            } else {
                parseMultipartItem(i, sb);
            }
        };

        // make Slide model
        SlideBuilder builder = new SlideBuilder();
        fileItems.stream()
                .forEach(item -> parseItem.accept(item, builder));
        return builder.build();
    }

    /**
     * <p>Parse form field item.</p>
     *
     * @param item form field item
     * @param builder slide model builder
     */
    private static void parseFormField(FileItem item, SlideBuilder builder) {
        switch (item.getFieldName()) {
            case "title":
                builder.buildTitle(item);
                break;
            case "desc":
                builder.buildDescription(item);
                break;
            case "tags":
                builder.buildTags(item);
                break;
        }
    }

    /**
     * <p>make Resource class instance of request item</p>
     *
     * @param item  FileItem in multipart request
     * @param builder Slide Model Builder
     */
    public static void parseMultipartItem(FileItem item, SlideBuilder builder) {
        ResourceType resourceType = ResourceType.toSlideResource(item.getContentType());
        switch (resourceType) {
            case PDF:
                builder.buildPdfResource(item);
                break;
            case PPT:
            case PPTX:
            case PPTM:
                builder.buildPowerpointResource(resourceType, item);
                break;
            default:
                return;
        }
    }


}
