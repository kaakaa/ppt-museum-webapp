package org.kaakaa.pptmuseum.db.document.util;

import org.apache.commons.fileupload.FileItem;
import org.kaakaa.pptmuseum.db.ResourceType;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * The instance of <code>Slide</code> class Builder
 *
 * Created by kaakaa on 16/03/26.
 */
class SlideBuilder {
    private Slide slide;

    /**
     * <p>default constructor</p>
     */
    public SlideBuilder() {
        this.slide = new Slide();
    }

    /**
     * set title value of Slide
     *
     * @param item FileItem having title value
     */
    public void buildTitle(FileItem item) {
        slide.setTitle(encodingMultiformString(item));
    }

    /**
     * set description value of Slide
     *
     * @param item FileItem having description value
     */
    public void buildDescription(FileItem item) {
        slide.setDescription(encodingMultiformString(item));
    }

    /**
     * set tags value of Slide
     *
     * @param item FileItem having tags value
     */
    public void buildTags(FileItem item) {
        slide.setTags(Arrays.asList(encodingMultiformString(item).split(",")));
    }

    /**
     * set PDF of Slide
     *
     * @param item FileItem having multipart pdf file
     */
    public void buildPdfResource(FileItem item) {
        buildPdfResource(item.get());
    }

    /**
     * set Pdf of Slide
     *
     * @param bytes pdf resource formatted byte[]
     */
    private void buildPdfResource(byte[] bytes) {
        slide.setPdfResource(new Resource(ResourceType.PDF, bytes));
        buildThumbnailResource(bytes);
    }

    /**
     * set powerpoint of Slide
     *
     * @param type resource type(ppt/pptx/pptm)
     * @param item FileItem having multipart powerpoint filek
     */
    public void buildPowerpointResource(ResourceType type, FileItem item) {
        slide.setPowerpointResource(new Resource(type, item.get()));
        buildPdfResource(JodConverter.convert(type, item.get()));
    }

    /**
     * set thumbinail of Slide
     *
     * @param pdfBytes pdf resource formatted byte[]
     */
    public void buildThumbnailResource(byte[] pdfBytes) {
        byte[] bytes = ThumbnailGenerater.generate(pdfBytes);
        slide.setThumbnail(new Resource(ResourceType.THUMBNAIL, bytes));
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

    public Slide build() {
        return this.slide;
    }
}
