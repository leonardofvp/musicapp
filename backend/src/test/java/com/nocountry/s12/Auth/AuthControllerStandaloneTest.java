package com.nocountry.s12.Auth;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nocountry.s12.Dto.Request.LoginDto;
import com.nocountry.s12.Dto.Request.RegistroDto;
import com.nocountry.s12.Dto.Response.AuthResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AuthControllerStandaloneTest {

  private MockMvc mockMvc;

  @Mock
  private AuthService authService;

  @InjectMocks
  private AuthController authController;

  private JacksonTester<LoginDto> loginDtoJacksonTester;
  private JacksonTester<AuthResponse> authResponseJacksonTester;
  private JacksonTester<RegistroDto> registroDtoJacksonTester;

  @BeforeEach
  void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());

    mockMvc = MockMvcBuilders
        .standaloneSetup(authController)
        .build();
  }

  @Test
  void canAuthenticateAUser() throws Exception {
    LoginDto loginDto = new LoginDto("faviofz", "123456");
    AuthResponse authResponse = new AuthResponse("token");

    //given
    given(authService.login(loginDto)).willReturn(authResponse);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginDtoJacksonTester.write(loginDto).getJson())).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(authResponseJacksonTester.write(authResponse).getJson(),
        response.getContentAsString());
  }

  @Test
  void loginShouldFailWhenUrlIsInvalid() throws Exception {
    LoginDto loginDto = new LoginDto("faviofz", "123456");

    //when
    MockHttpServletResponse response = mockMvc.perform(
        post("/auth//login").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginDtoJacksonTester.write(loginDto).getJson())).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    assertTrue(response.getContentAsString().isEmpty());
  }

  @Test
  void loginShouldFailWhenUsernameIsBlank() throws Exception {
    LoginDto loginDto = new LoginDto(" ", "123456");

    //when
    MockHttpServletResponse response = mockMvc.perform(
        post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginDtoJacksonTester.write(loginDto).getJson())).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertTrue(response.getContentAsString().isEmpty());
  }

  @Test
  void loginShouldFailWhenPasswordIsBlank() throws Exception {
    LoginDto loginDto = new LoginDto("faviofz", " ");

    //when
    MockHttpServletResponse response = mockMvc.perform(
        post("/auth/login").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginDtoJacksonTester.write(loginDto).getJson())).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    assertTrue(response.getContentAsString().isEmpty());
  }
}
  /*@Test
  void canRegisterAUser() throws Exception {
    RegistroDto registroDto = RegistroDto.builder().email("faviofz@gmail.com").password("Faviofz8@")
        .rol("ARTISTA").nombreArtistico("Favio Fernandez").descripcion("A urban artist").build();
    AuthResponse authResponse = new AuthResponse("token");

    //given
    given(authService.registro(registroDto)).willReturn(authResponse);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            post("/auth/registro").accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroDtoJacksonTester.write(registroDto).getJson()))
        .andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(authResponseJacksonTester.write(authResponse).getJson(),
        response.getContentAsString());
  }
}*/