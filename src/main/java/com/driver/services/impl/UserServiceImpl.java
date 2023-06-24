package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        User user = new User(username,password);

        Country country = new Country();
        CountryName countryName2 = null;

        for(CountryName countryName1: CountryName.values()){
            if(countryName1.name().equalsIgnoreCase(countryName)){
                user.setOriginalIp(countryName1.toCode()+"."+userRepository3.save(user).getId());
                country.setCountryName(countryName1);
                country.setCode(countryName1.toCode());
                countryName2 = countryName1;

                country.setUser(user);
                user.setOriginalCountry(country);
                user.setConnected(false);
                userRepository3.save(user);
            }
        }
        if(countryName2 == null)
            throw new Exception("Country not found");


         return user;




    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUsers().add(user);

        serviceProviderRepository3.save(serviceProvider);

        return user;



    }
}
