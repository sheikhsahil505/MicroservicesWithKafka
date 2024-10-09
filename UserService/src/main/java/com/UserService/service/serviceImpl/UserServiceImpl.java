package com.UserService.service.serviceImpl;


import com.AddressService.entity.Address;
import com.UserService.entity.User;
import com.UserService.repository.UserRepository;
import com.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, Object> template;

    @Override
    public User save(User user) {
        User save = userRepository.save(user);
        for (Address address : user.getAddressList()) {
            address.setUserId(user.getId());
            template.send("address", address);
        }
        return save;

    }

    @Override
    public Optional<User> findById(long userId) {
        template.send("userId", userId);
        Optional<User> byId = userRepository.findById(userId);
        byId.get().setAddressList(UserServiceImpl.listAdd());
        return byId;
    }

    @Override
    public List<User> getAll() {
        long id = 10L;
        template.send("findAllAddress", id);
        List<User> all = userRepository.findAll();
        List<Address> addressList1 = UserServiceImpl.listAdd();
        Map<Long, List<Address>> userIdToAddressesMap = addressList1.stream()
                .filter(address -> address.getUserId() != null)  // filter out addresses with null userId
                .collect(Collectors.groupingBy(Address::getUserId));
        for (User user : all) {
            long userId = user.getId();
            List<Address> userAddresses = userIdToAddressesMap.getOrDefault(userId, Collections.emptyList());
            user.setAddressList(userAddresses);
        }
        return all;
    }

    static List<Address> addressList = new ArrayList<>();

    @KafkaListener(topics = "addressList", groupId = "address-group")
    private static List<Address> getAllAddress(List<Address> address) {
        addressList.addAll(address);
        return address;
    }

    @KafkaListener(topics = "allAddressList", groupId = "address-group")
    private static List<Address> finAllAddress(List<Address> address) {
        addressList.addAll(address);
        return address;
    }

    private static List<Address> listAdd() {
        return addressList;
    }
}
