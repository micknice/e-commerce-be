package com.ecommerce_be.ecommerce_backend.model.dao;

import com.ecommerce_be.ecommerce_backend.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDAO extends ListCrudRepository<Address, Long> {
    List<Address> findByUser_Id(Long id);


}
