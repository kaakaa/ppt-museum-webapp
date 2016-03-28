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

import java.io.IOException;

/**
 * Created by kaakaa on 16/03/24.
 */
public class JodConverter {
    private static final String JOD_URL = "http://jod:8080";
    private static final String PATH = "/converter/converted/document.pdf";

    /**
     * <p>convert ppt/pptx file to pdf format by JODConverter. </p>
     *
     * @param bytes        ppt/pptx file contents
     * @param resourceType Content-type getting request header
     * @return pdf file contents
     */
    public static byte[] convert(ResourceType resourceType, byte[] bytes) {
        byte[] results = new byte[1024];

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(JOD_URL + PATH);

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("outputFormat", "pdf")
                    .addBinaryBody("inputDocument", bytes, ContentType.create(resourceType.getContentType()), "filename.ppt")
                    .build();
            post.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                HttpEntity resEntity = response.getEntity();
                results = EntityUtils.toByteArray(resEntity);
                EntityUtils.consume(resEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
