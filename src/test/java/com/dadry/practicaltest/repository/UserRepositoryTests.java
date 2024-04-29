package com.dadry.practicaltest.repository;

import com.dadry.practicaltest.models.User;
import com.dadry.practicaltest.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave() {
        User newUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("test@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        User savedUser = userRepository.save(newUser);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getBirthDate()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testFindByEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("findbyemail@example.com")
                .birthDate(LocalDate.of(2004, 12, 28))
                .build();
        userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findByEmail("findbyemail@example.com");

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo("findbyemail@example.com");
    }

    @Test
    void testFindById() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("findbyid@example.com")
                .birthDate(LocalDate.of(2004, 12, 28))
                .build();
        User savedUser = userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    void testFindAllByBirthDateBetween() {
        User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("findbyid1@example.com")
                .birthDate(LocalDate.of(1985, 1, 1))
                .build();
        User user2 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("findbyid2@example.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
        User user3 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("findbyid3@example.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        List<User> users = userRepository.findAllByBirthDateBetween(LocalDate.of(1980, 1, 1), LocalDate.of(1995, 12, 31));

        assertThat(users).hasSize(2);
        assertThat(users.stream().map(User::getEmail)).contains("findbyid1@example.com", "findbyid2@example.com");
    }
}
