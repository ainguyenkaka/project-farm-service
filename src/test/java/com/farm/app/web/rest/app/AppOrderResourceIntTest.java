package com.farm.app.web.rest.app;

import com.farm.app.FarmServiceApp;
import com.farm.app.domain.Order;
import com.farm.app.repository.OrderRepository;
import com.farm.app.service.OrderService;
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
public class AppOrderResourceIntTest {

    private static final String DEFAULT_CUSTOMER_INFO_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_INFO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_PACKAGE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_PACKAGE_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restOrderMockMvc;

    private Order order;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppOrderResource orderResource = new AppOrderResource(orderService);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    public static Order createEntity() {
        Order order = new Order()
            .customerInfoId(DEFAULT_CUSTOMER_INFO_ID)
            .servicePackageId(DEFAULT_SERVICE_PACKAGE_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .status(DEFAULT_STATUS)
            .userId(DEFAULT_USER_ID);
        return order;
    }

    @Before
    public void initTest() {
        orderRepository.deleteAll();
        order = createEntity();
    }

    @Test
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        restOrderMockMvc.perform(post("/api/app/orders").param("userId",DEFAULT_USER_ID)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCustomerInfoId()).isEqualTo(DEFAULT_CUSTOMER_INFO_ID);
        assertThat(testOrder.getServicePackageId()).isEqualTo(DEFAULT_SERVICE_PACKAGE_ID);
        assertThat(testOrder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/app/orders?sort=id,desc").param("userId",DEFAULT_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId())))
            .andExpect(jsonPath("$.[*].customerInfoId").value(hasItem(DEFAULT_CUSTOMER_INFO_ID.toString())))
            .andExpect(jsonPath("$.[*].servicePackageId").value(hasItem(DEFAULT_SERVICE_PACKAGE_ID.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.save(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/app/orders/{id}", order.getId()).param("userId",DEFAULT_USER_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId()))
            .andExpect(jsonPath("$.customerInfoId").value(DEFAULT_CUSTOMER_INFO_ID.toString()))
            .andExpect(jsonPath("$.servicePackageId").value(DEFAULT_SERVICE_PACKAGE_ID.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }


    @Test
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Get the order
        restOrderMockMvc.perform(delete("/api/app/orders/{id}", order.getId()).param("userId",DEFAULT_USER_ID)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }


}
