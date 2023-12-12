package com.nocountry.s12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.nocountry.s12.Dto.Request.LoginDto;
import com.nocountry.s12.Dto.Request.RegistroDto;
import com.nocountry.s12.Repository.UsuarioRepository;
import com.nocountry.s12.models.Usuario;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AuthControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JacksonTester<LoginDto> loginDtoJacksonTester;

  @Autowired
  private JacksonTester<RegistroDto> registroDtoJacksonTester;

  @Autowired
  private static UsuarioRepository usuarioRepository;

  private static final Usuario usuario = Usuario.builder()
      .username("example@test.com").password("test123=")
      .nombreCompleto("Example Test")
      .apellidoCompleto("Test Example")
      .pais("Pais")
      .provincia("Provincia")
      .localidad("Localidad")
      .zona("Zona")
      .fechaNacimiento(LocalDate.now())
      .alta(true)
      .build();

  @Test
  void canLoginWhenUserExists() throws Exception {
    LoginDto loginDto = new LoginDto("faviofz1@gmail.com", "Faviofz8+");

    //when
    MockHttpServletResponse response = mockMvc.perform(
            post("/auth/login").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJacksonTester.write(loginDto).getJson())).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertFalse(response.getContentAsString().isEmpty());
  }

  @Test
  void canRegisterAUserWithValidCredentials() throws Exception {
    RegistroDto registroDto = RegistroDto.builder()
        .email("example@test.com").password("test123=")
        .build();

    //when
    MockHttpServletResponse response = mockMvc.perform(
            post("/auth/registro").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(registroDtoJacksonTester.write(registroDto).getJson())).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    assertFalse(response.getContentAsString().isEmpty());
  }

  @AfterAll
  static void afterAll() {
    usuarioRepository.delete(usuario);
  }
}
