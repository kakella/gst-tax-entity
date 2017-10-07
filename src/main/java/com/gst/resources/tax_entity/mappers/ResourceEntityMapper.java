package com.gst.resources.tax_entity.mappers;

import com.gst.resources.tax_entity.api.models.V1TaxEntity;
import com.gst.resources.tax_entity.persistence.springdata.entities.TaxEntity;
import org.springframework.stereotype.Component;

@Component
public class ResourceEntityMapper {

    public TaxEntity resourceToEntity(V1TaxEntity resource) {
        return new TaxEntity(resource.getPan(), resource.getEmail(), resource.getCellPhone());
    }

}
