package org.kaakaa.pptmuseum.db.document.util.backup;

import java.nio.file.Path;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class Backup {
    private String filename;
    private Path zipPath;

    public Backup(Path p) {
        this.zipPath = p;
        this.filename = p.toFile().getName();
    }

    public String getFilename() {
        return this.filename;
    }

    public Path getZipPath(){
        return this.zipPath;
    }
}
