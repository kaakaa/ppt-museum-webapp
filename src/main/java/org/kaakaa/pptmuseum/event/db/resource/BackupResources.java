package org.kaakaa.pptmuseum.event.db.resource;

import org.kaakaa.pptmuseum.Main;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.kaakaa.pptmuseum.db.document.util.backup.SlideDumper;
import org.kaakaa.pptmuseum.event.Event;
import org.kaakaa.pptmuseum.event.EventException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipUtil;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class BackupResources implements Event<Integer>{
    private final String root;

    Logger logger = LoggerFactory.getLogger(getClass());

    public BackupResources(String root) {
        this.root = root;
    }

    @Override
    public Integer execute() throws EventException {
        beforeLogging(logger);


        int numberOfSlides = mongoDBClient.allSlideSize();
        List<Slide> slides = mongoDBClient.searchAll(0, numberOfSlides);

        for(Slide slide : slides) {
            new SlideDumper(this.root).accept(slide);
        }

        try {
            compressDumps(Paths.get(this.root));
        } catch (IOException e) {
            logger.error("Packing to zip error: {}", e.getMessage());
            logger.error("{}", e.getStackTrace());
        }
        afterLogging(logger);
        return numberOfSlides;
    }


    private void compressDumps(Path backupDest) throws IOException {
        Main.BACKUP_ROOT.toFile().mkdirs();
        Path zipFilePath = Main.BACKUP_ROOT.resolve(String.format("%s.%s", backupDest.getFileName(), "zip"));

        ZipUtil.pack(backupDest.toFile(), zipFilePath.toFile());

        // delete backup files and dirs recursively
        Files.walkFileTree(backupDest, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
