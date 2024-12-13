package com.hexaware.ControllerTest;

import com.hexaware.Controller.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.DTO.EmployeeDTO;
import com.hexaware.DTO.UserDTO;
import com.hexaware.Entity.Users;
import com.hexaware.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAdminAddUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        when(userService.admin_addUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/admin_addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testAdminEditUserName() throws Exception {
        when(userService.admin_editUserName(eq("oldUser"), eq("newUser"))).thenReturn("Username updated");

        mockMvc.perform(post("/api/admin_editUsername/oldUser/newUser"))
                .andExpect(status().isOk())
                .andExpect(content().string("Username updated"));
    }

    @Test
    void testAdminEditUserRole() throws Exception {
        when(userService.admin_editUserRole(eq("testuser"), eq(Users.Role.ADMIN))).thenReturn("Role updated");

        mockMvc.perform(post("/api/admin_editUserRole/testuser/ADMIN"))
                .andExpect(status().isOk())
                .andExpect(content().string("Role updated"));
    }

    @Test
    void testAdminEditUserPwd() throws Exception {
        when(userService.admin_editUserPwd(eq("testuser"), eq("newPassword"))).thenReturn("Password updated");

        mockMvc.perform(post("/api/admin_editUserPwd/testuser/newPassword"))
                .andExpect(status().isOk())
                .andExpect(content().string("Password updated"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO());

        when(userService.admin_showAll()).thenReturn(userList);

        mockMvc.perform(get("/api/admin_getUserData"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);

        when(userService.admin_getDataById(1)).thenReturn(userDTO);

        mockMvc.perform(get("/api/admin_getUserData/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void testAdminRemoveUser() throws Exception {
        when(userService.admin_removeUser(1)).thenReturn("User removed");

        mockMvc.perform(delete("/api/admin_removeUser/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User removed"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<EmployeeDTO> employeeList = new ArrayList<>();
        employeeList.add(new EmployeeDTO());

        when(userService.getAllEmployees()).thenReturn(employeeList);

        mockMvc.perform(get("/api/admin_getAllEmployees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}