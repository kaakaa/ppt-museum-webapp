package org.kaakaa.pptmuseum.db.document.util.backup;

import org.kaakaa.pptmuseum.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class Backups {
    private List<Backup> backups = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(Backups.class);

    public Backups() {
        try {
            Files.newDirectoryStream(Main.BACKUP_ROOT, "*.zip").forEach(p -> this.backups.add(new Backup(p)));
        } catch (NoSuchFileException e) {
            logger.debug("There is no backup files.");
        } catch (IOException e) {
            logger.error("Search backup zip file error: ", e);
        }
    }

    public List<Backup> getBackups() {
        return this.backups;
    }
}
