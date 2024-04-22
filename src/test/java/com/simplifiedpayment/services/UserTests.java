package com.simplifiedpayment.services;

import com.simplifiedpayment.data.UserRole;
import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.ReadUserDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTests {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void shouldReturnCreateUserDTOEmptyArray() {
        List<User> usersMock = new ArrayList<>();
        User user1 = new User();
        usersMock.add(user1);
        when(userRepository.findAll()).thenReturn(usersMock);

        List<ReadUserDTO> users = userService.getAll();

        assertTrue(users.getFirst() instanceof ReadUserDTO);
    }

    @Test
    public void shouldReturnEmptyArray() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        var users = this.userService.getAll();
        assertEquals(0, users.size());
    }

    @Test
    public void shouldReturnAnArrayWithOneUserAndVerifyAttributes() {
        List<User> userMock = new ArrayList<>();
        String name = "Geraldo";
        String email = "geraldo@teste.com";
        String password = "senha123";
        String document = "912875421";
        String type = "lojista";
        double wallet = 89.00;


        User user1 = new User(name, email, password, document, type, wallet, UserRole.ADMIN);
        userMock.add(user1);

        when(userRepository.findAll()).thenReturn(userMock);

        List<ReadUserDTO> users = this.userService.getAll();
        assertEquals(1, users.size());
        assertEquals(name, users.getFirst().name());
        assertEquals(email, users.getFirst().email());
        assertEquals(document, users.getFirst().document());
        assertEquals(type, users.getFirst().type());
        assertEquals(wallet, users.getFirst().wallet());
    }

    @Test
    public void shouldReturnArrayWithTwoUsers() {
        List<User> usersMock = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        usersMock.add(user1);
        usersMock.add(user2);

        when(userRepository.findAll()).thenReturn(usersMock);

        List<ReadUserDTO> users = this.userService.getAll();
        assertEquals(2, users.size());
    }


    @Test
    public void shouldReturnCarameloUser() {
        Optional<User> user = Optional.of(new User("caramelo", "email@teste.com", "senha123", "52763", "normal", 500.0, UserRole.USER));
        when(userRepository.findById(2L)).thenReturn(user);

        User userReturn = this.userService.getOneById(2L);
        assertEquals("caramelo", userReturn.getName());
    }

}
