package com.farm.app.web.rest;

import com.farm.app.FarmServiceApp;

import com.farm.app.domain.Farm;
import com.farm.app.repository.FarmRepository;
import com.farm.app.service.FarmService;
import com.farm.app.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmServiceApp.class)
public class FarmResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_ACREAGE = 0D;
    private static final Double UPDATED_ACREAGE = 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "aaaa@gmail.com";
    private static final String UPDATED_EMAIL = "bbbb@gmail.com";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmService farmService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restFarmMockMvc;

    private Farm farm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FarmResource farmResource = new FarmResource(farmService);
        this.restFarmMockMvc = MockMvcBuilders.standaloneSetup(farmResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }


    public static Farm createEntity() {
        Farm farm = new Farm()
            .title(DEFAULT_TITLE)
            .acreage(DEFAULT_ACREAGE)
            .description(DEFAULT_DESCRIPTION)
            .contact(DEFAULT_CONTACT)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .imageUrl(DEFAULT_IMAGE_URL);
        return farm;
    }

    @Before
    public void initTest() {
        farmRepository.deleteAll();
        farm = createEntity();
    }

    @Test
    public void createFarm() throws Exception {
        int databaseSizeBeforeCreate = farmRepository.findAll().size();

        // Create the Farm
        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isCreated());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeCreate + 1);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFarm.getAcreage()).isEqualTo(DEFAULT_ACREAGE);
        assertThat(testFarm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFarm.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testFarm.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFarm.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFarm.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
    }

    @Test
    public void createFarmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = farmRepository.findAll().size();

        // Create the Farm with an existing ID
        farm.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setTitle(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAcreageIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setAcreage(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setDescription(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setContact(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setEmail(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = farmRepository.findAll().size();
        // set the field null
        farm.setPhone(null);

        // Create the Farm, which fails.

        restFarmMockMvc.perform(post("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isBadRequest());

        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllFarms() throws Exception {
        // Initialize the database
        farmRepository.save(farm);

        // Get all the farmList
        restFarmMockMvc.perform(get("/api/farms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farm.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].acreage").value(hasItem(DEFAULT_ACREAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL.toString())));
    }

    @Test
    public void getFarm() throws Exception {
        // Initialize the database
        farmRepository.save(farm);

        // Get the farm
        restFarmMockMvc.perform(get("/api/farms/{id}", farm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(farm.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.acreage").value(DEFAULT_ACREAGE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL.toString()));
    }

    @Test
    public void getNonExistingFarm() throws Exception {
        // Get the farm
        restFarmMockMvc.perform(get("/api/farms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFarm() throws Exception {
        // Initialize the database
        farmService.save(farm);

        int databaseSizeBeforeUpdate = farmRepository.findAll().size();

        // Update the farm
        Farm updatedFarm = farmRepository.findOne(farm.getId());
        updatedFarm
            .title(UPDATED_TITLE)
            .acreage(UPDATED_ACREAGE)
            .description(UPDATED_DESCRIPTION)
            .contact(UPDATED_CONTACT)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .imageUrl(UPDATED_IMAGE_URL);

        restFarmMockMvc.perform(put("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFarm)))
            .andExpect(status().isOk());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate);
        Farm testFarm = farmList.get(farmList.size() - 1);
        assertThat(testFarm.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFarm.getAcreage()).isEqualTo(UPDATED_ACREAGE);
        assertThat(testFarm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFarm.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testFarm.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFarm.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFarm.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    public void updateNonExistingFarm() throws Exception {
        int databaseSizeBeforeUpdate = farmRepository.findAll().size();

        // Create the Farm

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFarmMockMvc.perform(put("/api/farms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(farm)))
            .andExpect(status().isCreated());

        // Validate the Farm in the database
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteFarm() throws Exception {
        // Initialize the database
        farmService.save(farm);

        int databaseSizeBeforeDelete = farmRepository.findAll().size();

        // Get the farm
        restFarmMockMvc.perform(delete("/api/farms/{id}", farm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Farm> farmList = farmRepository.findAll();
        assertThat(farmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farm.class);
    }
}
