package com.simplifiedpayment.services;

import com.simplifiedpayment.data.dtos.ReadUserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserTests userTests;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnEmptyArray() {
        when(userService.getAll()).thenReturn(new ArrayList<>());
        var users = this.userService.getAll();
        assertEquals(0, users.size());
    }

    @Test
    public void shouldReturnArrayWithTwoUsers() {
        List<ReadUserDTO> usersMock = new ArrayList<>();

        ReadUserDTO readUserDto1 = new ReadUserDTO(1L, "Geraldo", "geraldo@teste.com", "1234985", "normal", 500.0);
        ReadUserDTO readUserDto2 = new ReadUserDTO(2L, "Luan", "luan@teste.com", "231424", "lojista", 200.0);

        usersMock.add(readUserDto1);
        usersMock.add(readUserDto2);
        when(userService.getAll()).thenReturn(usersMock);
        var users = this.userService.getAll();
        assertEquals(1, users.size());
    }
}
