package org.sakamainty.hibernate.models.visites;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sakamainty.hibernate.models.DataResponse;

public class VisiterResponse extends DataResponse {
    @JsonProperty("_embedded")
    private EmbeddedVisiter embeddedVisiter;

    public EmbeddedVisiter getEmbeddedVisiter() {
        return embeddedVisiter;
    }

    public void setEmbeddedVisiter(EmbeddedVisiter embeddedVisiter) {
        this.embeddedVisiter = embeddedVisiter;
    }
}
