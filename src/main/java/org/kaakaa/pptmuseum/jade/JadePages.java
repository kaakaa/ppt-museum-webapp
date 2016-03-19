package org.kaakaa.pptmuseum.jade;

/**
 * Created by kaakaa on 16/03/19.
 */
public enum JadePages {
    /* top page */
    TOP("top/ppt-museum"),
    /* tag search result page */
    TAG_SEARCH("search/tags"),
    /* presentation page */
    PRESENTATION("presentation/presentation");

    /**
     * Jade template file path
     */
    private final String templatePath;

    /**
     * <p>Constructor</p>
     *
     * @param template Jade template file path
     */
    JadePages(String template) {
        this.templatePath = template;
    }

    /**
     * <p>Get template path</p>
     *
     * @return template path
     */
    public String getTemplatePath() {
        return templatePath;
    }
}
