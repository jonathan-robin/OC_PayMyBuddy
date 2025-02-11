//package com.oc.paymybuddy.integration.controllers;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.ui.Model;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.security.web.context.SecurityContextRepository;
//
//import com.oc.paymybuddy.controller.UserController;
//import com.oc.paymybuddy.model.User;
//import com.oc.paymybuddy.service.CustomUserDetailsService;
//import com.oc.paymybuddy.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@ExtendWith(MockitoExtension.class)
//class SignInControllerTest {
//
//	
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//    
//    @Mock 
//    private UserController userController;
//
//    @Mock
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Mock
//    private SecurityContextRepository securityContextRepository;
//
//    Model model;
//
//
//    @BeforeEach
//    void setup() {
//        model = mock(Model.class);
//    }
//
//    @Test
//    void testSignInPage() throws Exception {
//        mockMvc.perform(get("/sign-in"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("sign-in/signIn"))
//                .andExpect(model().attributeExists("signIn"));
//    }
//
//    @Test
//    void testCreateUser_Success() throws Exception {
//        User user = new User();
//        user.setFirstname("John");
//        user.setLastname("Doe");
//        user.setEmail("john.doe@example.com");
//        user.setPassword("password123");
//
//        when(userService.createUser(any(User.class))).thenReturn(user);
//
//        mockMvc.perform(post("/sign-in")
//                .flashAttr("signIn", user))
//                .andExpect(status().isOk())
//                .andExpect(view().name("sign-in/signed"));
//    }
//
//    @Test
//    void testCreateUser_Exception() throws Exception {
//        User user = userService.findAll().get(0);
//        
//        user.setFirstname("John");
//        user.setLastname("Doe");
//        user.setEmail(user.getEmail());
//        user.setPassword("password123");
//
////        when(userService.createUser(any(User.class))).thenReturn(null);
//
//        Exception exception = assertThrows(Exception.class, () -> 
//            userController.addUser(user, model, mock(HttpServletRequest.class), mock(HttpServletResponse.class))
//        );
//
//        assertEquals("Error when creating new user", exception.getMessage());
//    }
//}
