package com.gst.resources.tax_entity.api;

import com.gst.resources.tax_entity.api.models.V1ErrorResponse;
import com.gst.resources.tax_entity.api.models.V1TaxEntity;
import com.gst.resources.tax_entity.exceptions.ResourceAlreadyExistsException;
import com.gst.resources.tax_entity.mappers.ResourceEntityMapper;
import com.gst.resources.tax_entity.services.NoSQLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;

@Component
public class DelegateImpl implements V1ApiDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(DelegateImpl.class);

    private NoSQLService persistenceService;

    private ResourceEntityMapper resourceEntityMapper;

    @Autowired
    public DelegateImpl(NoSQLService persistenceService, ResourceEntityMapper mapper) {
        this.persistenceService = persistenceService;
        this.resourceEntityMapper = mapper;
    }

    @Override
    public ResponseEntity<V1TaxEntity> createResource(V1TaxEntity resource) {

        try {
            persistenceService.insertResource(resourceEntityMapper.resourceToEntity(resource));
        } catch (ResourceAlreadyExistsException | ConstraintViolationException e) {
            LOGGER.error(e.getMessage());
            LOGGER.error(e.getStackTrace().toString());

            V1ErrorResponse error = new V1ErrorResponse();
            error.setCode(HttpStatus.BAD_REQUEST.value());
            error.setMessage(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(resource);
    }
}
