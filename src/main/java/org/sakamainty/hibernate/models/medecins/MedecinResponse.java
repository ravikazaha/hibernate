package org.sakamainty.hibernate.models.medecins;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sakamainty.hibernate.models.DataResponse;

public class MedecinResponse extends DataResponse {
    @JsonProperty("_embedded")
    private EmbeddedMedecin embeddedMedecin;

    public EmbeddedMedecin getEmbedded() {
        return embeddedMedecin;
    }

    public void setEmbeddedList(EmbeddedMedecin embeddedMedecin) {
        this.embeddedMedecin = embeddedMedecin;
    }
}
