package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {
        Admin admin = new Admin(username,password);
        return adminRepository1.save(admin);
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        ServiceProvider serviceProvider = new ServiceProvider();

        Admin admin = adminRepository1.findById(adminId).get();

        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);

        admin.getServiceProviders().add(serviceProvider);

        return adminRepository1.save(admin);



    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception {

        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();
        Country country = new Country();
        CountryName[] countryNames = CountryName.values();



        if(Arrays.stream(countryNames).anyMatch(countryName1 -> countryName1.name().equalsIgnoreCase(countryName) ) ){

            if(countryName.equalsIgnoreCase(CountryName.IND.name()))
            {
                country.setCountryName(CountryName.IND);
                country.setCode(CountryName.IND.toCode());
            }
            else if(countryName.equalsIgnoreCase(CountryName.AUS.name()))
            {
                country.setCountryName(CountryName.AUS);
                country.setCode(CountryName.AUS.toCode());
            }
            else if(countryName.equalsIgnoreCase(CountryName.USA.name()))
            {
                country.setCountryName(CountryName.USA);
                country.setCode(CountryName.USA.toCode());
            }
            else if(countryName.equalsIgnoreCase(CountryName.CHI.name()))
            {
                country.setCountryName(CountryName.CHI);
                country.setCode(CountryName.CHI.toCode());
            }
            else{
                country.setCountryName(CountryName.JPN);
                country.setCode(CountryName.JPN.toCode());
            }

            country.setServiceProvider(serviceProvider);
            serviceProvider.getCountryList().add(country);


            return serviceProviderRepository1.save(serviceProvider);

        }
        else{
            throw new Exception("Country not found");
        }

    }
}
