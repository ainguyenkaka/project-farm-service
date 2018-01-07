package com.farm.app.web.rest.app;

import com.farm.app.FarmServiceApp;
import com.farm.app.domain.ServicePackage;
import com.farm.app.repository.ServicePackageRepository;
import com.farm.app.service.ServicePackageService;
import com.farm.app.web.rest.TestUtil;
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

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static com.farm.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmServiceApp.class)
public class AppServicePackageResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_PAY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_LOCATION = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_KILOGRAM = 0D;
    private static final Double UPDATED_TOTAL_KILOGRAM = 1D;

    private static final Integer DEFAULT_USER_NUMBER = 0;
    private static final Integer UPDATED_USER_NUMBER = 1;

    private static final Double DEFAULT_TOTAL_PRICE = 0D;
    private static final Double UPDATED_TOTAL_PRICE = 1D;

    private static final Integer DEFAULT_BEFORE_MONTH = 0;
    private static final Integer UPDATED_BEFORE_MONTH = 1;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ServicePackageRepository servicePackageRepository;

    @Autowired
    private ServicePackageService servicePackageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restServicePackageMockMvc;

    private ServicePackage servicePackage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppServicePackageResource MyServicePackageResource = new AppServicePackageResource(servicePackageService);
        this.restServicePackageMockMvc = MockMvcBuilders.standaloneSetup(MyServicePackageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServicePackage createEntity() {
        ServicePackage servicePackage = new ServicePackage()
            .userId(DEFAULT_USER_ID)
            .category(DEFAULT_CATEGORY)
            .payType(DEFAULT_PAY_TYPE)
            .totalKilogram(DEFAULT_TOTAL_KILOGRAM)
            .userNumber(DEFAULT_USER_NUMBER)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .beforeMonth(DEFAULT_BEFORE_MONTH)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return servicePackage;
    }

    @Before
    public void initTest() {
        servicePackageRepository.deleteAll();
        servicePackage = createEntity();
    }

    @Test
    public void createServicePackage() throws Exception {
        int databaseSizeBeforeCreate = servicePackageRepository.findAll().size();

        // Create the ServicePackage
        restServicePackageMockMvc.perform(post("/api/app/service-packages").param("userId",DEFAULT_USER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicePackage)))
            .andExpect(status().isCreated());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeCreate + 1);
        ServicePackage testServicePackage = servicePackageList.get(servicePackageList.size() - 1);
        assertThat(testServicePackage.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testServicePackage.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testServicePackage.getPayType()).isEqualTo(DEFAULT_PAY_TYPE);
        assertThat(testServicePackage.getTotalKilogram()).isEqualTo(DEFAULT_TOTAL_KILOGRAM);
        assertThat(testServicePackage.getUserNumber()).isEqualTo(DEFAULT_USER_NUMBER);
        assertThat(testServicePackage.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testServicePackage.getBeforeMonth()).isEqualTo(DEFAULT_BEFORE_MONTH);
        assertThat(testServicePackage.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testServicePackage.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }


    @Test
    public void getServicePackage() throws Exception {
        // Initialize the database
        servicePackageRepository.save(servicePackage);

        // Get the servicePackage
        restServicePackageMockMvc.perform(get("/api/app/service-packages/{id}", servicePackage.getId()).param("userId",DEFAULT_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servicePackage.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.payType").value(DEFAULT_PAY_TYPE.toString()))
            .andExpect(jsonPath("$.totalKilogram").value(DEFAULT_TOTAL_KILOGRAM.doubleValue()))
            .andExpect(jsonPath("$.userNumber").value(DEFAULT_USER_NUMBER))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.beforeMonth").value(DEFAULT_BEFORE_MONTH))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }


    @Test
    public void updateServicePackage() throws Exception {
        // Initialize the database
        servicePackageService.save(servicePackage);

        int databaseSizeBeforeUpdate = servicePackageRepository.findAll().size();

        // Update the servicePackage
        ServicePackage updatedServicePackage = servicePackageRepository.findOne(servicePackage.getId());
        updatedServicePackage
            .userId(UPDATED_USER_ID)
            .category(UPDATED_CATEGORY)
            .payType(UPDATED_PAY_TYPE)
            .totalKilogram(UPDATED_TOTAL_KILOGRAM)
            .userNumber(UPDATED_USER_NUMBER)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .beforeMonth(UPDATED_BEFORE_MONTH)
            .startDate(UPDATED_START_DATE);

        restServicePackageMockMvc.perform(put("/api/app/service-packages").param("userId",DEFAULT_USER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedServicePackage)))
            .andExpect(status().isOk());

        // Validate the ServicePackage in the database
        List<ServicePackage> servicePackageList = servicePackageRepository.findAll();
        assertThat(servicePackageList).hasSize(databaseSizeBeforeUpdate);
        ServicePackage testServicePackage = servicePackageList.get(servicePackageList.size() - 1);
        assertThat(testServicePackage.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testServicePackage.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testServicePackage.getPayType()).isEqualTo(UPDATED_PAY_TYPE);
        assertThat(testServicePackage.getTotalKilogram()).isEqualTo(UPDATED_TOTAL_KILOGRAM);
        assertThat(testServicePackage.getUserNumber()).isEqualTo(UPDATED_USER_NUMBER);
        assertThat(testServicePackage.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testServicePackage.getBeforeMonth()).isEqualTo(UPDATED_BEFORE_MONTH);
        assertThat(testServicePackage.getStartDate()).isEqualTo(UPDATED_START_DATE);
    }


}
