package org.sakamainty.hibernate.models.patients;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.sakamainty.hibernate.models.DataResponse;
import org.sakamainty.hibernate.models.medecins.EmbeddedMedecin;

public class PatientResponse extends DataResponse {
    @JsonProperty("_embedded")
    private EmbeddedPatient embeddedPatient;

    public EmbeddedPatient getEmbeddedPatient() {
        return embeddedPatient;
    }

    public void setEmbeddedPatient(EmbeddedPatient embeddedPatient) {
        this.embeddedPatient = embeddedPatient;
    }
}
