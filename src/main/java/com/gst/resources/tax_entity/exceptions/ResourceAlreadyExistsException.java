package com.gst.resources.tax_entity.exceptions;

public class ResourceAlreadyExistsException extends Exception {
    public ResourceAlreadyExistsException(String email) {
        super("TaxEntity with email `" + email + "` already exists");
    }
}
