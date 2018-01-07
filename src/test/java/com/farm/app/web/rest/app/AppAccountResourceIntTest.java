package com.farm.app.web.rest.app;

import com.farm.app.FarmServiceApp;
import com.farm.app.domain.Authority;
import com.farm.app.domain.User;
import com.farm.app.repository.AuthorityRepository;
import com.farm.app.repository.UserRepository;
import com.farm.app.security.AuthoritiesConstants;
import com.farm.app.service.UserService;
import com.farm.app.service.dto.UserDTO;
import com.farm.app.web.rest.TestUtil;
import com.farm.app.web.rest.app.AppAccountResource;
import com.farm.app.web.rest.vm.ManagedUserVM;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = FarmServiceApp.class)
public class AppAccountResourceIntTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Mock
    private UserService mockUserService;


    private MockMvc restUserMockMvc;

    private MockMvc restMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        AppAccountResource appAccountResource =
            new AppAccountResource(userRepository, userService);

        AppAccountResource accountUserMockResource =
            new AppAccountResource(userRepository, mockUserService);

        this.restMvc = MockMvcBuilders.standaloneSetup(appAccountResource).build();
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(accountUserMockResource).build();
    }

    @Test
    public void testNonAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/app/authenticate")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    public void testAuthenticatedUser() throws Exception {
        restUserMockMvc.perform(get("/api/app/authenticate")
            .with(request -> {
                request.setRemoteUser("test");
                return request;
            })
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("test"));
    }

    @Test
    public void testGetExistingAccount() throws Exception {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        User user = new User();
        user.setLogin("test");
        user.setPhone("0123456789");
        user.setEmail("john.doe@farm.com");
        user.setAuthorities(authorities);
        when(mockUserService.getUserWithAuthorities()).thenReturn(user);

        restUserMockMvc.perform(get("/api/app/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.login").value("test"))
            .andExpect(jsonPath("$.phone").value("0123456789"))
            .andExpect(jsonPath("$.email").value("john.doe@farm.com"))
            .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
    }

    @Test
    public void testGetUnknownAccount() throws Exception {
        when(mockUserService.getUserWithAuthorities()).thenReturn(null);

        restUserMockMvc.perform(get("/api/app/account")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }


    @Test
    public void testRegisterInvalidLogin() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM(
            null,                   // id
            "funky-log!n",          // login <-- invalid
            "password",             // password
            "aaaaa@local.com",      // email,
            "phone",                // phone
            ZonedDateTime.now(),                   // createdDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restUserMockMvc.perform(
            post("/api/app/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("funky@example.com");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void testRegisterInvalidEmail() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM(
            null,               // id
            "bob",              // login
            "password",             // password
            "invalid",      // email,
            "phone",                // phone
            null,                   // createdDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restUserMockMvc.perform(
            post("/api/app/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByLogin("bob");
        assertThat(user.isPresent()).isFalse();
    }

    @Test
    public void testRegisterInvalidPassword() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM(
            null,               // id
            "bob",              // login
            "123",             // password
            "aaaaa@local.com",      // email,
            "phone",                // phone
            ZonedDateTime.now(),                   // createdDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER)));

        restUserMockMvc.perform(
            post("/api/app/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByLogin("bob");
        assertThat(user.isPresent()).isFalse();
    }



    @Test
    public void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM(
            null,                   // id
            "badguy",               // login
            "password",             // password
            "aaaaa@local.com",      // email,
            "phone",                // phone
            null,                   // createdDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.ADMIN)));

        restMvc.perform(
            post("/api/app/register")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        Optional<User> userDup = userRepository.findOneByLogin("badguy");
        assertThat(userDup.isPresent()).isTrue();
        assertThat(userDup.get().getAuthorities()).hasSize(1)
            .containsExactly(authorityRepository.findOne(AuthoritiesConstants.USER));
    }

    @Test
    public void testSaveInvalidLogin() throws Exception {
        UserDTO invalidUser = new UserDTO(
            null,                   // id
            "funky-log!n",          // login <-- invalid

            "aaaaa@local.com",      // email,
            "phone",                // phone
            null,                   // createdDate
            new HashSet<>(Arrays.asList(AuthoritiesConstants.USER))
        );

        restUserMockMvc.perform(
            post("/api/app/account")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("funky@example.com");
        assertThat(user.isPresent()).isFalse();
    }
}
