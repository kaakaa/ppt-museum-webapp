package org.kaakaa.pptmuseum.db.document.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.kaakaa.pptmuseum.db.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by kaakaa on 16/03/24.
 */
public class JodConverter {
    private static final String JOD_URL = "http://jod:8080";
    private static final String PATH = "/converter/converted/document.pdf";

    private static final Logger logger = LoggerFactory.getLogger(JodConverter.class);

    /**
     * <p>convert ppt/pptx file to pdf format by JODConverter. </p>
     *
     * @param bytes        ppt/pptx file contents
     * @param resourceType Content-type getting request header
     * @return pdf file contents
     */
    public static byte[] convert(ResourceType resourceType, byte[] bytes) {
        byte[] results = new byte[1024];

        logger.info("Convert powerpoint resource by JODCvonerter ({}{})", JOD_URL, PATH);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(JOD_URL + PATH);

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("outputFormat", "pdf")
                    .addBinaryBody("inputDocument", bytes, ContentType.create(resourceType.getContentType()), "filename.ppt")
                    .build();
            post.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                logger.info("Sending request to JODConvert is success. ({})", response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                results = EntityUtils.toByteArray(resEntity);
                EntityUtils.consume(resEntity);
            }
        } catch (IOException e) {
            logger.info("JODConverting is failed: {}", e.getMessage());
            logger.debug("{}", e.getStackTrace());
            return new byte[1024];
        }
        logger.info("Convert success.");
        return results;
    }
}
