package org.kaakaa.pptmuseum.db.document.util.backup;

import org.json.simple.JSONObject;
import org.kaakaa.pptmuseum.db.document.Resource;
import org.kaakaa.pptmuseum.db.document.Slide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * Created by kaakaa_hoe on 2016/04/03.
 */
public class SlideDumper implements Consumer<Slide> {
    private final String root;

    private static final Logger logger = LoggerFactory.getLogger(SlideDumper.class);

    public SlideDumper(String root) {
        this.root = root;
    }

    @Override
    public void accept(Slide slide) {
        Path dest = getDest(slide);
        dest.toFile().mkdirs();
        try {
            writeMeta(dest, slide);
            writeResources(dest, slide);
        } catch (IOException e) {
            logger.error("Dump data error: {}", e);
            return;
        }
    }

    private Path getDest(Slide slide) {
        return Paths.get(root, slide.getId().toString());
    }

    private void writeMeta(Path dest, Slide slide) throws IOException {
        logger.info("Dump {}'s meta data", slide.getTitle());
        JSONObject metaJson = slide.getMetaData();
        try (FileWriter writer = new FileWriter(dest.resolve(".meta").toFile())) {
            writer.write(metaJson.toJSONString());
        }
    }

    private void writeResources(Path dest, Slide slide) throws IOException {
        final String title = slide.getTitle();

        logger.info("Dump {}'s PDF File", title);
        writeResource(dest, title, slide.getPDFResource(), "PDF");
        logger.info("Dump {}'s PowerPoint File", title);
        writeResource(dest, title, slide.getPowerpointResource(), "PowerPoint");
        logger.info("Dump {}'s Thumbnail File", title);
        writeResource(dest, title, slide.getThumbnail(), "Thumbnail");
    }

    private void writeResource(Path dest, String title, Resource resource, String type) throws IOException {
        if (null == resource) {
            logger.warn("{}'s {} file is empty.", title, type);
            return;
        }

        Path filePath = getFilePath(dest, title, resource);
        try (OutputStream writer = Files.newOutputStream(filePath)) {
            writer.write(resource.getFile());
        }
    }

    private Path getFilePath(Path dest, String title, Resource resource) {
        String filename = String.format("%s.%s", title, resource.getExt());
        try {
            return dest.resolve(filename);
        } catch (InvalidPathException e) {
            return dest.resolve(String.format("%s.%s", "DUMMY", resource.getExt()));
        }
    }

}
