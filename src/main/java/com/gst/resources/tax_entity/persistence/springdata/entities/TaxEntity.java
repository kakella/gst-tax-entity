package com.gst.resources.tax_entity.persistence.springdata.entities;

import com.couchbase.client.java.repository.annotation.Field;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.couchbase.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
public class TaxEntity {
    @Field
    @ReadOnlyProperty
    private static final String docType = "tax-entity";

    @Id
    private String id;

    @Field
    @NotNull
    private String pan;

    @Field
    @NotNull
    @Email
    private String email;

    @Field
    private String cellPhone;

    public TaxEntity(String pan, String email, String cellPhone) {
        this.id = TaxEntity.getIdFromPAN(pan);
        this.pan = pan;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public static String getIdFromPAN(String pan) {
        return TaxEntity.docType + ":" + pan;
    }

    public String getId() {
        return id;
    }

    public String getDocType() {
        return docType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}
