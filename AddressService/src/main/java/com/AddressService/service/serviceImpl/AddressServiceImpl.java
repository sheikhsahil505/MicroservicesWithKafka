package com.AddressService.service.serviceImpl;

import com.AddressService.entity.Address;
import com.AddressService.repository.AddressRepository;
import com.AddressService.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private KafkaTemplate<String, Object> template;

    @Override
    @KafkaListener(topics = "address", groupId = "address-group")
    public Address saveAddress(Address address) {
        System.out.println(address.getState());
        return repository.save(address);
    }

    @KafkaListener(topics = "userId", groupId = "address-group")
    public void findByUserId(long userId) {
        template.send("addressList", repository.findByUserId(userId));
    }

    @KafkaListener(topics = "findAllAddress", groupId = "address-group")
    public void findAll(long id) {
        System.out.println(id);
        List<Address> all = repository.findAll();
        for (Address a : all) {
            System.out.println(a.getState());
        }
        template.send("allAddressList", all);
    }
}
