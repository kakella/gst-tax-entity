package com.gst.resources.tax_entity.persistence.springdata.repositories;

import com.gst.resources.tax_entity.persistence.springdata.entities.TaxEntity;
import org.springframework.data.repository.CrudRepository;

public interface IResourceRepository extends CrudRepository<TaxEntity, String> {

}
