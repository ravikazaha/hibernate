package org.sakamainty.hibernate.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MedecinResponse {
    @JsonProperty("_embedded")
    private Embedded embedded;

    @JsonProperty("_links")
    private Map<String, Link> links;

    private Page page;

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
