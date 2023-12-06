//package com.ecommerce_be.ecommerce_backend.api.security;
//
//import com.ecommerce_be.ecommerce_backend.model.LocalUser;
//import com.ecommerce_be.ecommerce_backend.model.dao.LocalUserDAO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@Primary
//public class JunitUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    LocalUserDAO localUserDAO;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(username);
//        if (opUser.isPresent())
//            return opUser.get();
//        return null;
//    }
//}
