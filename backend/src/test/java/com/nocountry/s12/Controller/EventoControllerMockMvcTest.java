package com.nocountry.s12.Controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.nocountry.s12.Dto.Request.EventoRequestDTO;
import com.nocountry.s12.Dto.Response.EventoResponseDTO;
import com.nocountry.s12.Jwt.JwtService;
import com.nocountry.s12.ServiceImpl.EventoServiceImpl;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureJsonTesters
@WebMvcTest(EventoController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventoControllerMockMvcTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private EventoServiceImpl eventoService;
  @MockBean
  private JwtService jwtService;
  @Autowired
  private JacksonTester<EventoRequestDTO> eventoRequestDTOJacksonTester;
  @Autowired
  private JacksonTester<EventoResponseDTO> eventoResponseDTOJacksonTester;
  @Autowired
  private JacksonTester<List<EventoResponseDTO>> eventoResponseDTOListJacksonTester;

  @Test
  void canRetrieveAllEventos() throws Exception {
    List<EventoResponseDTO> dtoList = List.of(
        new EventoResponseDTO(1L, "Titulo 1", "Lugar 1", "Hora 1", 1.,
            LocalDate.now(), "Descripcion 1"),
        new EventoResponseDTO(2L, "Titulo 2", "Lugar 2", "Hora 2", 2.,
            LocalDate.now(), "Descripcion 2"),
        new EventoResponseDTO(3L, "Titulo 3", "Lugar 3", "Hora 3", 3.,
            LocalDate.now(), "Descripcion 3"));
    //given
    given(eventoService.findAll()).willReturn(dtoList);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/eventos").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(eventoResponseDTOListJacksonTester.write(dtoList).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveEventoById() throws Exception {
    EventoResponseDTO eventoResponseDTO = new EventoResponseDTO(1L, "Titulo 1",
        "Lugar 1", "Hora 1", 1., LocalDate.now(), "Descripcion 1");

    //given
    given(eventoService.findById(1L)).willReturn(eventoResponseDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/eventos/1").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        eventoResponseDTOJacksonTester.write(eventoResponseDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canCreateEvento() throws Exception {
    EventoRequestDTO eventoRequestDTO = new EventoRequestDTO(1L, "Titulo 1",
        "Lugar 1", "Hora 1", 1., LocalDate.now().toString(), "Descripcion 1");
    EventoResponseDTO eventoResponseDTO = new EventoResponseDTO(1L, "Titulo 1",
        "Lugar 1", "Hora 1", 1., LocalDate.now(), "Descripcion 1");
    //when
    MockHttpServletResponse response = mockMvc.perform(
            post("/eventos").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(
                    eventoRequestDTOJacksonTester.write(eventoRequestDTO).getJson()))
        .andReturn().getResponse();

    //then
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    assertEquals(
        eventoResponseDTOJacksonTester.write(eventoResponseDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canUpdateEventoById() throws Exception {
    EventoRequestDTO eventoRequestDTO = new EventoRequestDTO(1L, "Titulo 1",
        "Lugar 1", "Hora 1", 1., LocalDate.now().toString(), "Descripcion 1");
    EventoResponseDTO eventoResponseDTO = new EventoResponseDTO(1L,
        "Titulo " + "Modificado 1", "Lugar 1", "Hora 1", 1., LocalDate.now(),
        "Descripcion 1");

    //given
    given(eventoService.update(1L, eventoRequestDTO)).willReturn(
        eventoResponseDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(put("/eventos/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                eventoRequestDTOJacksonTester.write(eventoRequestDTO).getJson()))
        .andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        eventoResponseDTOJacksonTester.write(eventoResponseDTO).getJson(),
        response.getContentAsString());

  }

  @Test
  void canDeleteEventoById() throws Exception {
    //given
    given(eventoService.delete(1L)).willReturn(true);

    //when
    MockHttpServletResponse response =
        mockMvc.perform(delete("/eventos/1").accept(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse();

    //then
    assertThat(response.getStatus(), anyOf(is(HttpStatus.ACCEPTED.value()),
        is(HttpStatus.NO_CONTENT.value())));
  }
}