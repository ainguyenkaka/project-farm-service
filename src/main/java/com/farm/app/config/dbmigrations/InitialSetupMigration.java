package com.farm.app.config.dbmigrations;

import com.farm.app.domain.*;
import com.farm.app.security.AuthoritiesConstants;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.ZonedDateTime;
import java.util.*;

@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addDefaultAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addDefaultUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User adminUser = new User();
        adminUser.setId("user-01");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setEmail("admin@localhost");
        adminUser.setPhone("123456789");
        adminUser.setCreatedDate(ZonedDateTime.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-02");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setEmail("user@localhost");
        adminUser.setPhone("123456789");
        userUser.setCreatedDate(ZonedDateTime.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }


    @ChangeSet(order = "03", author = "initiator", id = "03-addDefaultFarms")
    public void addFarms(MongoTemplate mongoTemplate) {
        String des = "\n Tăng 10% giá trị đơn hàng/1 lần giao hàng thêm."
            + "\n Cộng thêm 25.000/1 lần giao hàng tại nhà."
            + "\n Tăng 10% giá trị đơn hàng."
            + "\n Giảm 5% giá trị tổng đơn hàng nếu thanh toán trước 3 tháng."
            + "\n Giảm 10% giá trị tổng đơn hàng nếu thanh toán trước 6 tháng."
            + "\n Giảm 20% giá trị tổng đơn hàng nếu thanh toán trước 12 tháng.";
        Farm farm = new Farm();
        farm.setId("farm-01");
        farm.setTitle("Rau sạch hoà lạc");
        farm.setAcreage(new Double(2500));
        farm.setContact("Phạm Thị Lương - Trung Tâm CNTT Viettel 3");
        farm.setDescription(des);
        farm.setPhone("0978.999.999");
        farm.setEmail("luongpt4@viettel.com.vn");
        farm.getMethods().add("Thổ canh");
        farm.getMethods().add("Thuỷ canh");
        farm.setImageUrl("assets/img/home.jpg");
        mongoTemplate.save(farm);

    }

    @ChangeSet(order = "04", author = "initiator", id = "04-addDefaultProducts")
    public void addProducts(MongoTemplate mongoTemplate) {
        Product product = new Product();
        product.setId("product-01");
        product.setName("Rau mồng tơi");
        product.setCategory("rau sạch");
        product.setPrice(30.0);
        product.setImageUrl("assets/img/prod-mong-toi.jpg");
        product.setFarmId("farm-01");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau muống");
        product.setImageUrl("assets/img/prod-muong.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau đay");
        product.setImageUrl("assets/img/prod-day.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau cải bó xôi");
        product.setImageUrl("assets/img/prod-cai-bo-xoi.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau đậu đũa");
        product.setImageUrl("assets/img/prod-dau-duoi.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau dền");
        product.setImageUrl("assets/img/prod-den.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Cà tím");
        product.setImageUrl("assets/img/prod-ca-tim.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau ngót");
        product.setImageUrl("assets/img/prod-ngot.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau xà lách");
        product.setImageUrl("assets/img/prod-xa-lach.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rưa chuột");
        product.setImageUrl("assets/img/prod-dua-chuot.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Chuối xanh");
        product.setImageUrl("assets/img/prod-chuoi-xanh.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Chuối chín");
        product.setImageUrl("assets/img/prod-chuoi-chin.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Rau ngải cứu");
        product.setImageUrl("assets/img/prod-ngai-cuu.jpg");
        mongoTemplate.save(product);

        product.setId(null);
        product.setName("Cà chua");
        product.setImageUrl("assets/img/prod-ca-chua.jpg");
        mongoTemplate.save(product);
    }

    @ChangeSet(order = "05", author = "initiator", id = "05-addDefaultCustomerInfo")
    public void addCustomerInfo(MongoTemplate mongoTemplate) {
        CustomerInfo customerInfo = new CustomerInfo();
        customerInfo.setId("customInfo-01");
        customerInfo.setUserId("user-02");
        customerInfo.setName("name");
        customerInfo.setOrganization("organization");
        customerInfo.setMembers(3);
        customerInfo.setHouseNumber("32");
        customerInfo.setPhone("0123456789");
        customerInfo.setTown("town");
        customerInfo.setStreet("street");
        mongoTemplate.save(customerInfo);
    }

    @ChangeSet(order = "06", author = "initiator", id = "06-addDefaultServicePackages")
    public void addServicePackage(MongoTemplate mongoTemplate) {
        ServicePackage servicePackage = new ServicePackage();
        servicePackage.setId("servicePackage-01");
        servicePackage.setUserId("user-02");
        servicePackage.setBeforeMonth(3);
        servicePackage.setCategory("category");
        servicePackage.setPayType("pay type");
        servicePackage.setTotalKilogram(20.0);
        servicePackage.setUserNumber(3);
        servicePackage.setTotalPrice(300.0);
        mongoTemplate.save(servicePackage);
    }


    @ChangeSet(order = "07", author = "initiator", id = "07-addDefaultNotifications")
    public void addNotifications(MongoTemplate mongoTemplate) {
        Notification notification = new Notification();
        notification.setMessage("message");
        notification.setTitle("title");
        notification.getSentUsers().add("user-02");
        mongoTemplate.save(notification);
    }

    @ChangeSet(order = "08", author = "initiator", id = "07-addOrders")
    public void addOrders(MongoTemplate mongoTemplate) {
        Order order = new Order();
        order.setPaid(true);
        order.setUserId("user-02");
        order.setServicePackageId("servicePackage-01");
        order.setCustomerInfoId("customInfo-01");
        order.setStatus(1);
        Set<Order.ChosenProduct> chosenProducts = new HashSet<>();
        chosenProducts.add(new Order.ChosenProduct("product-01", Double.valueOf(2.0)));
        order.setChosenProducts(chosenProducts);
        mongoTemplate.save(order);
    }

    @ChangeSet(order = "09", author = "initiator", id = "07-addDefaultServicePrices")
    public void addServicePrices(MongoTemplate mongoTemplate) {
        ServicePrice servicePrice = new ServicePrice();
        servicePrice.setAveragePrice(new Double(20000));
        servicePrice.setAdvancedLocationPrice(new Double(25000));
        servicePrice.setAdvancedProductPrice(new Double(0.2));
        servicePrice.setAdvancedDeliveryPrice(new Double(0.1));
        mongoTemplate.save(servicePrice);
    }

    @ChangeSet(order = "10", author = "initiator", id = "07-addDefaultLocations")
    public void addLocations(MongoTemplate mongoTemplate) {
        Location location = new Location();
        location.addBasicLocation("Keangnam");
        location.addBasicLocation("Crowne");
        location.addBasicLocation("CT2");
        location.addBasicLocation("01 Giang Văn Minh");
        location.addBasicLocation("Viettel Hòa Lạc");
        mongoTemplate.save(location);
    }
}
