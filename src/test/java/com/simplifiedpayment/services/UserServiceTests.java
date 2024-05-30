package com.simplifiedpayment.services;

import com.simplifiedpayment.data.UserRole;
import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.ReadUserDTO;
import com.simplifiedpayment.data.dtos.UpdateUserDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

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

        assertInstanceOf(ReadUserDTO.class, users.getFirst());
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
    public void shouldReturnOptionalUserByIdAndCheckAllAttributes() {
        String name = "user";
        String email = "email@teste.com";
        String password = "senha123";
        String document = "52763";
        String type = "normal";
        double wallet = 500.0;
        Optional<User> user = Optional.of(new User(name, email, password, document, type, wallet, UserRole.USER));
        when(userRepository.findById(2L)).thenReturn(user);

        User userReturn = this.userService.getOneById(2L);
        assertEquals(name, userReturn.getName());
        assertEquals(email, userReturn.getEmail());
        assertEquals(password, userReturn.getPassword());
        assertEquals(document, userReturn.getDocument());
        assertEquals(type, userReturn.getType());
        assertEquals(wallet, userReturn.getWallet());
    }

    @Test
    public void shouldReturnNullOptionalUserByIdReceivingNull() {
        User userOptional = null;
        Optional<User> user = Optional.ofNullable(userOptional);
        when(userRepository.findById(null)).thenReturn(user);

        User userReturn = this.userService.getOneById(null);
        assertNull(userReturn);
    }

    @Test
    public void shouldCreateAnUserAndVerifyAllAttributes() {
        String name = "user";
        String email = "email@teste.com";
        String password = new BCryptPasswordEncoder().encode("senha123");
        String document = "52763";
        String type = "normal";
        double wallet = 500.0;

        CreateUserDTO createUserDTO = new CreateUserDTO(name, email, password, document, type, wallet, UserRole.USER);
        User user = createUserDTO.toUser();

        when(userRepository.save(user)).thenReturn(user);

        User userReturn = this.userService.create(createUserDTO);

        assertEquals(user.getName(), userReturn.getName());
        assertEquals(user.getEmail(), userReturn.getEmail());
        assertEquals(user.getPassword(), userReturn.getPassword());
        assertEquals(user.getDocument(), userReturn.getDocument());
        assertEquals(user.getType(), userReturn.getType());
        assertEquals(user.getWallet(), userReturn.getWallet());
    }

    @Test
    public void shouldThrowNullPointerOnCreateNullUser() {
        User user = null;
        CreateUserDTO userDTO = null;
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(NullPointerException.class, () -> this.userService.create(userDTO));
    }

    @Test
    public void shouldUpdateUserAndCheckAllAttributes() {
        Long id = 40L;
        String nameDto = "userdto";
        String emailDto = "emaildto@teste.com";
        String documentDto = "527214163";
        String typeDto = "normal";
        double walletDto = 500.0;

        UpdateUserDTO updateUserDto = new UpdateUserDTO(nameDto, emailDto, documentDto, typeDto, walletDto);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "email@teste.com";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        user.setId(13L);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameDto, userUpdated.getName());
        assertEquals(emailDto, userUpdated.getEmail());
        assertEquals(documentDto, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeDto, userUpdated.getType());
        assertEquals(walletDto, userUpdated.getWallet());

    }

    @Test
    public void shouldUpdateUserNameAndCheck() {
        Long id = 40L;
        String nameDto = "userdto";

        UpdateUserDTO updateUserDto = new UpdateUserDTO(nameDto, null, null, null, null);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "password123";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameDto, userUpdated.getName());
        assertEquals(emailUser, userUpdated.getEmail());
        assertEquals(documentUser, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeUser, userUpdated.getType());
        assertEquals(walletUser, userUpdated.getWallet());

    }

    @Test
    public void shouldUpdateUserEmailAndCheck() {
        Long id = 40L;
        String emailDto = "userdto";

        UpdateUserDTO updateUserDto = new UpdateUserDTO(null, emailDto, null, null, null);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "password123";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameUser, userUpdated.getName());
        assertEquals(emailDto, userUpdated.getEmail());
        assertEquals(documentUser, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeUser, userUpdated.getType());
        assertEquals(walletUser, userUpdated.getWallet());

    }

    @Test
    public void shouldUpdateUserDocumentAndCheck() {
        Long id = 40L;
        String documentDto = "9178458141";

        UpdateUserDTO updateUserDto = new UpdateUserDTO(null, null, documentDto, null, null);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "password123";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameUser, userUpdated.getName());
        assertEquals(emailUser, userUpdated.getEmail());
        assertEquals(documentDto, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeUser, userUpdated.getType());
        assertEquals(walletUser, userUpdated.getWallet());

    }

    @Test
    public void shouldUpdateUserTypeAndCheck() {
        Long id = 40L;
        String typeDto = "normal";

        UpdateUserDTO updateUserDto = new UpdateUserDTO(null, null, null, typeDto, null);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "password123";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameUser, userUpdated.getName());
        assertEquals(emailUser, userUpdated.getEmail());
        assertEquals(documentUser, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeDto, userUpdated.getType());
        assertEquals(walletUser, userUpdated.getWallet());

    }

    @Test
    public void shouldUpdateUserWalletAndCheck() {
        Long id = 40L;
        double walletDto = 3.29;

        UpdateUserDTO updateUserDto = new UpdateUserDTO(null, null, null, null, walletDto);

        String nameUser = "user";
        String emailUser = "email@teste.com";
        String passwordUser = "password123";
        String documentUser = "1241241";
        String typeUser = "lojista";
        double walletUser = 250;

        User user = new User(nameUser, emailUser, passwordUser, documentUser, typeUser, walletUser, UserRole.USER);
        Optional<User> userOptional = Optional.of(user);
        when(this.userRepository.findById(id)).thenReturn(userOptional);
        when(this.userRepository.save(user)).thenReturn(user);

        User userUpdated = this.userService.update(id, updateUserDto);

        assertEquals(nameUser, userUpdated.getName());
        assertEquals(emailUser, userUpdated.getEmail());
        assertEquals(documentUser, userUpdated.getDocument());
        assertEquals(passwordUser, userUpdated.getPassword());
        assertEquals(typeUser, userUpdated.getType());
        assertEquals(walletDto, userUpdated.getWallet());

    }
}
