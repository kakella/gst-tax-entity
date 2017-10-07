package com.gst.resources.tax_entity.services;

import com.gst.resources.tax_entity.exceptions.ResourceAlreadyExistsException;
import com.gst.resources.tax_entity.persistence.springdata.entities.TaxEntity;
import com.gst.resources.tax_entity.persistence.springdata.repositories.IResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoSQLService {
    private final IResourceRepository resourceRepository;

    @Autowired
    public NoSQLService(IResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public TaxEntity getById(String email) {
        return resourceRepository.findOne(TaxEntity.getIdFromPAN(email));
    }

    public void insertResource(TaxEntity resource) throws ResourceAlreadyExistsException {
        if (resourceRepository.findOne(resource.getId()) != null) {
            throw new ResourceAlreadyExistsException(resource.getEmail());
        }
        resourceRepository.save(resource);
    }

    public void deleteById(String pan) {
        resourceRepository.delete(TaxEntity.getIdFromPAN(pan));
    }
}
