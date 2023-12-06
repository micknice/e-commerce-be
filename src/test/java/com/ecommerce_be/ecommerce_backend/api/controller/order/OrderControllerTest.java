//package com.ecommerce_be.ecommerce_backend.api.controller.order;
//
//import com.ecommerce_be.ecommerce_backend.model.WebOrder;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class OrderControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    @WithUserDetails("UserA")
//    public void testUserAAuthenticatedOrderList() throws Exception {
//        testAuthenticatedUserListBelongsToUser("UserA");
//    }
////    @Test
//    @WithUserDetails("UserB")
//    public void testUserBAuthenticatedOrderList() throws Exception {
//        testAuthenticatedUserListBelongsToUser("UserB");
//    }
//
//    private void testAuthenticatedUserListBelongsToUser(String username) throws Exception {
//        mvc.perform(get("/order"))
//                .andExpect(status().is(HttpStatus.OK.value()))
//                .andExpect(result -> {
//                    String json = result.getResponse().getContentAsString();
//                    List<WebOrder> orders = new ObjectMapper().readValue(json, new TypeReference<List<WebOrder>>() {});
//                    for (WebOrder order : orders) {
//                        Assertions.assertEquals(username, order.getUser().getUsername(), "Order list only contains orders belonging to user");
//                    }
//                });
//
//    }
//
//    @Test
//    public void testUnauthenticatedOrderList() throws Exception{
//        mvc.perform(get("/order")).andExpect(status().is(HttpStatus.FORBIDDEN.value()));
//    }
//}
