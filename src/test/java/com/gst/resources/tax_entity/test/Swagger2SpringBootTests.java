package com.gst.resources.tax_entity.test;

import com.gst.resources.tax_entity.api.models.V1TaxEntity;
import com.gst.resources.tax_entity.mappers.ResourceEntityMapper;
import com.gst.resources.tax_entity.persistence.springdata.entities.TaxEntity;
import com.gst.resources.tax_entity.services.NoSQLService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class Swagger2SpringBootTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoSQLService persistenceService;

    @Autowired
    private ResourceEntityMapper mapper;

    private V1TaxEntity resource;
    private TaxEntity taxEntity;

    @Before
    public void init() {
        resource = new V1TaxEntity();
        resource.setPan("DUMMY_PAN");
        resource.setEmail("a@b.com");
        resource.setCellPhone("99999-99999");
        resource.setStateOrUnionTerritory(V1TaxEntity.StateOrUnionTerritoryEnum.AD);

        taxEntity = mapper.resourceToEntity(resource);
    }

    @Test
    public void testPOSTRequest_documentDoesNotExist() {
        persistenceService.deleteById(taxEntity.getPan());

        V1TaxEntity response = this.restTemplate.postForObject(
                "/v1/tax-entity",
                resource,
                V1TaxEntity.class);

        assertThat(response).isEqualToComparingFieldByField(resource);
    }

    @Test
    public void testPOSTRequest_documentAlreadyExists() {
        ResponseEntity<V1TaxEntity> response = this.restTemplate.postForEntity(
                "/v1/tax-entity",
                resource,
                V1TaxEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTRequest_invalidEmailAddress() {
        resource.setEmail("ab.com");
        ResponseEntity<V1TaxEntity> response = this.restTemplate.postForEntity(
                "/v1/tax-entity",
                resource,
                V1TaxEntity.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testPOSTRequest_verifyDataPersisted() {
        TaxEntity entity = persistenceService.getById(this.taxEntity.getPan());
        assertThat(entity).isEqualToComparingFieldByField(this.taxEntity);
        assertThat(entity.getId()).isEqualTo("tax-entity:" + entity.getPan());
        assertThat(entity.getDocType()).isEqualTo("tax-entity");
    }

    @Test
    public void testEntityKeyHandling() {
        this.taxEntity.setEmail("x@y.com");
        assertThat(this.taxEntity.getId()).isEqualTo("tax-entity:" + this.taxEntity.getPan());
    }

}
