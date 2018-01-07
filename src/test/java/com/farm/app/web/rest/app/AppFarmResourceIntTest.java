package com.farm.app.web.rest.app;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmServiceApp.class)
public class AppFarmResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Double DEFAULT_ACREAGE = 0D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";

    private static final String DEFAULT_EMAIL = "aaaa@gmail.com";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";

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
        AppFarmResource farmResource = new AppFarmResource(farmService);
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
    public void getAllFarms() throws Exception {
        // Initialize the database
        farmRepository.save(farm);

        // Get all the farmList
        restFarmMockMvc.perform(get("/api/app/farms?sort=id,desc"))
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
        restFarmMockMvc.perform(get("/api/app/farms/{id}", farm.getId()))
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
    public void getTopFarm() throws Exception {
        // Initialize the database
        farmRepository.save(farm);

        // Get the farm
        restFarmMockMvc.perform(get("/api/app/farms/top"))
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
}
