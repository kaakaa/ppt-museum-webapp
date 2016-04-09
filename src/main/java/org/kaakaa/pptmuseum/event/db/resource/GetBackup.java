package org.kaakaa.pptmuseum.event.db.resource;

import org.kaakaa.pptmuseum.db.document.util.backup.Backups;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class GetBackup implements Event<byte[]> {
    private String filename;

    private static final Logger logger = LoggerFactory.getLogger(GetBackup.class);

    public GetBackup(Request rq) {
        this.filename = rq.params("filename");
    }

    @Override
    public byte[] execute() throws EventException {
        Optional<byte[]> bytes = new Backups().getBackups().stream()
                .filter(b -> this.filename.equals(b.getFilename()))
                .map(b -> {
                    try (InputStream stream = Files.newInputStream(b.getZipPath()); ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                        byte[] buf = new byte[1024];
                        while (true) {
                            int len = stream.read(buf);
                            if (len < 0) {
                                break;
                            }
                            bout.write(buf, 0, len);
                        }
                        return bout.toByteArray();
                    } catch (IOException e) {
                        logger.error("Read zip file error: {}", e);
                        return new byte[1024];
                    }
                })
                .findFirst();

        return bytes.orElse(new byte[1024]);
    }
}
