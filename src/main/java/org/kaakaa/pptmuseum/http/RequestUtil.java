package org.kaakaa.pptmuseum.http;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.kaakaa.pptmuseum.db.SlideResource;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        SlideResource resourceType = SlideResource.toSlideResource(item.getContentType());
        switch (resourceType) {
            case PDF:
                slide.setPdfResource(new Resource(resourceType, item.get()));
                break;
            case PPT:
            case PPTX:
            case PPTM:
                slide.setPowerpointResource(new Resource(resourceType, item.get()));
                slide.setPdfResource(new Resource(SlideResource.PDF, convertByJodConverter(item.get(), resourceType)));
                break;
            default:
                return;
        }

        // make pdf thumbnail
        InputStream input = new ByteArrayInputStream(slide.getPDFResource().getFile());
        try {
            PDDocument pdDoc = PDDocument.load(input);
            PDFRenderer render = new PDFRenderer(pdDoc);
            BufferedImage bufferedImage = render.renderImage(0);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            slide.setThumbnail(new Resource(SlideResource.THUMBNAIL, baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>convert ppt/pptx file to pdf format by JODConverter. </p>
     *
     * @param bytes        ppt/pptx file contents
     * @param resourceType Content-type getting request header
     * @return pdf file contents
     */
    private static byte[] convertByJodConverter(byte[] bytes, SlideResource resourceType) {
        byte[] results = new byte[1024];

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("http://jod:8080/converter/converted/document.pdf");

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("outputFormat", "pdf")
                    .addBinaryBody("inputDocument", bytes, ContentType.create(resourceType.getContentType()), "filename.ppt")
                    .build();
            post.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(post);
            try {
                HttpEntity resEntity = response.getEntity();
                results = EntityUtils.toByteArray(resEntity);
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
