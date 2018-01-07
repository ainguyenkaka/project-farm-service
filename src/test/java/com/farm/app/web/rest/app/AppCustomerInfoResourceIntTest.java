package com.farm.app.web.rest.app;

import com.farm.app.FarmServiceApp;
import com.farm.app.domain.CustomerInfo;
import com.farm.app.repository.CustomerInfoRepository;
import com.farm.app.service.CustomerInfoService;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmServiceApp.class)
public class AppCustomerInfoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION = "BBBBBBBBBB";

    private static final String DEFAULT_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_PROVINE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "1";
    private static final String UPDATED_NUMBER = "1";

    private static final Integer DEFAULT_MEMBERS = 0;
    private static final Integer UPDATED_MEMBERS = 1;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restCustomerInfoMockMvc;

    private CustomerInfo customerInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppCustomerInfoResource customerInfoResource = new AppCustomerInfoResource(customerInfoService);
        this.restCustomerInfoMockMvc = MockMvcBuilders.standaloneSetup(customerInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerInfo createEntity() {
        CustomerInfo customerInfo = new CustomerInfo()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .organization(DEFAULT_ORGANIZATION)
            .town(DEFAULT_TOWN)
            .street(DEFAULT_STREET)
            .houseNumber(DEFAULT_NUMBER)
            .members(DEFAULT_MEMBERS)
            .userId(DEFAULT_USER_ID);
        return customerInfo;
    }

    @Before
    public void initTest() {
        customerInfoRepository.deleteAll();
        customerInfo = createEntity();
    }

    @Test
    public void createCustomerInfo() throws Exception {
        int databaseSizeBeforeCreate = customerInfoRepository.findAll().size();

        // Create the CustomerInfo
        restCustomerInfoMockMvc.perform(post("/api/app/customer-infos").param("userId", DEFAULT_USER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerInfo)))
            .andExpect(status().isCreated());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCustomerInfo.getOrganization()).isEqualTo(DEFAULT_ORGANIZATION);
        assertThat(testCustomerInfo.getTown()).isEqualTo(DEFAULT_TOWN);
        assertThat(testCustomerInfo.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCustomerInfo.getHouseNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCustomerInfo.getMembers()).isEqualTo(DEFAULT_MEMBERS);
        assertThat(testCustomerInfo.getUserId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    public void getCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoRepository.save(customerInfo);

        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/app/customer-infos/{id}", customerInfo.getId()).param("userId", DEFAULT_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerInfo.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.organization").value(DEFAULT_ORGANIZATION.toString()))
            .andExpect(jsonPath("$.town").value(DEFAULT_TOWN.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.houseNumber").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.members").value(DEFAULT_MEMBERS))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    public void getNonExistingCustomerInfo() throws Exception {
        // Get the customerInfo
        restCustomerInfoMockMvc.perform(get("/api/app/customer-infos/{id}", Long.MAX_VALUE).param("userId", DEFAULT_USER_ID))
            .andExpect(status().isNotFound());
    }


    @Test
    public void updateCustomerInfo() throws Exception {
        // Initialize the database
        customerInfoService.save(customerInfo);

        int databaseSizeBeforeUpdate = customerInfoRepository.findAll().size();

        // Update the customerInfo
        CustomerInfo updatedCustomerInfo = customerInfoRepository.findOne(customerInfo.getId());
        updatedCustomerInfo
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .organization(UPDATED_ORGANIZATION)
            .town(UPDATED_PROVINE)
            .street(UPDATED_STREET)
            .houseNumber(UPDATED_NUMBER)
            .members(UPDATED_MEMBERS);

        restCustomerInfoMockMvc.perform(put("/api/app/customer-infos").param("userId", DEFAULT_USER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomerInfo)))
            .andExpect(status().isOk());

        // Validate the CustomerInfo in the database
        List<CustomerInfo> customerInfoList = customerInfoRepository.findAll();
        assertThat(customerInfoList).hasSize(databaseSizeBeforeUpdate);
        CustomerInfo testCustomerInfo = customerInfoList.get(customerInfoList.size() - 1);
        assertThat(testCustomerInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCustomerInfo.getOrganization()).isEqualTo(UPDATED_ORGANIZATION);
        assertThat(testCustomerInfo.getTown()).isEqualTo(UPDATED_PROVINE);
        assertThat(testCustomerInfo.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCustomerInfo.getHouseNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCustomerInfo.getMembers()).isEqualTo(UPDATED_MEMBERS);
    }

}
