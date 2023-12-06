//package com.ecommerce_be.ecommerce_backend.api.security;
//
//import com.ecommerce_be.ecommerce_backend.model.LocalUser;
//import com.ecommerce_be.ecommerce_backend.model.dao.LocalUserDAO;
//import com.ecommerce_be.ecommerce_backend.service.JWTService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class JWTRequestFilterTest {
//
//    @Autowired
//    MockMvc mvc;
//    @Autowired
//    private JWTService jwtService;
//    @Autowired
//    private LocalUserDAO localUserDAO;
//    private static final String AUTHENTICATED_PATH = "/auth/me";
//
//    @Test
//    public void testUnauthenticatedRequest() throws Exception {
//        mvc.perform(get(AUTHENTICATED_PATH)).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//    }
//
//    @Test
//    public void testBadToken() throws Exception {
//        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "BadTokenInvalid"))
//                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer BadTokenInvalid"))
//                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//    }
//
//    @Test
//    public void testUnverifiedUser() throws Exception {
//        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserB").get();
//        String token = jwtService.generateJWT(user);
//        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer " + token))
//                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//    }
//
//    @Test
//    @WithUserDetails("UserA")
//    public void testSuccess() throws Exception {
//        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
//        String token = jwtService.generateJWT(user);
//        mvc.perform(get(AUTHENTICATED_PATH).header("Authorization", "Bearer " + token ))
//                .andExpect(status().is(HttpStatus.OK.value()));
//    }
//
//
//}
