package com.farm.app.service;

import com.farm.app.domain.CustomerInfo;
import com.farm.app.repository.CustomerInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomerInfoService {

    private final Logger log = LoggerFactory.getLogger(CustomerInfoService.class);

    private final CustomerInfoRepository customerInfoRepository;

    public CustomerInfoService(CustomerInfoRepository customerInfoRepository) {
        this.customerInfoRepository = customerInfoRepository;
    }


    public CustomerInfo save(CustomerInfo customerInfo) {
        log.debug("Request to save CustomerInfo : {}", customerInfo);
        CustomerInfo result = customerInfoRepository.save(customerInfo);
        return result;
    }


    public List<CustomerInfo> findAll() {
        log.debug("Request to get all CustomerInfos");
        List<CustomerInfo> result = customerInfoRepository.findAll();

        return result;
    }




    public CustomerInfo findOne(String id) {
        log.debug("Request to get CustomerInfo : {}", id);
        CustomerInfo customerInfo = customerInfoRepository.findOne(id);
        return customerInfo;
    }

    public CustomerInfo findOneByIdAndUserId(String id,String userId) {
        log.debug("Request to get CustomerInfo : {}", id);
        CustomerInfo customerInfo = customerInfoRepository.findOneByIdAndUserId(id, userId);
        return customerInfo;
    }

    public void delete(String id) {
        log.debug("Request to delete CustomerInfo : {}", id);
        customerInfoRepository.delete(id);
    }

    public List<CustomerInfo> findAllByUserId(String userId) {
        log.debug("Request to get all CustomerInfos with userId: {}", userId);
        return customerInfoRepository.findAllByUserId(userId);
    }
}
