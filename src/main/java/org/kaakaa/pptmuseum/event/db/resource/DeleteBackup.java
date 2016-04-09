package org.kaakaa.pptmuseum.event.db.resource;

import org.kaakaa.pptmuseum.db.document.util.backup.Backups;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class DeleteBackup implements Event<Object> {
    private final String fileneme;

    private static final Logger logger = LoggerFactory.getLogger(DeleteBackup.class);

    public DeleteBackup(Request rq) {
        this.fileneme = rq.params("filename");
    }

    @Override
    public Object execute() throws EventException {
        new Backups().getBackups().stream()
                .filter(b -> this.fileneme.equals(b.getFilename()))
                .forEach(b -> {
                    try {
                        Files.delete(b.getZipPath());
                    } catch (IOException e) {
                        logger.error("Delete backup zip error: {}", e);
                    }
                });
        return null;
    }
}
