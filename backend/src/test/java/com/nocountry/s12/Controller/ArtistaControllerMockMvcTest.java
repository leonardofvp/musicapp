package com.nocountry.s12.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.nocountry.s12.Dto.Response.ArtistaDTO;
import com.nocountry.s12.Jwt.JwtService;
import com.nocountry.s12.Service.ArtistaService;
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
@WebMvcTest(ArtistaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ArtistaControllerMockMvcTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ArtistaService artistaService;
  @MockBean
  private JwtService jwtService;
  @Autowired
  private JacksonTester<ArtistaDTO> artistaJacksonTester;
  @Autowired
  private JacksonTester<List<ArtistaDTO>> artistaListJacksonTester;

  @Test
  void canRetrieveAllArtistas() throws Exception {
    List<ArtistaDTO> artistaDTOS = List.of(new ArtistaDTO(), new ArtistaDTO());
    //given
    given(artistaService.listarTodos()).willReturn(artistaDTOS);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        get("/Artista/listarTodos")).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(artistaListJacksonTester.write(artistaDTOS).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveArtistaById() throws Exception {
    ArtistaDTO artistaDTO = new ArtistaDTO();
    artistaDTO.setId(1L);

    //given
    given(artistaService.verArtista(1L)).willReturn(artistaDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/Artista/1").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(artistaJacksonTester.write(artistaDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveArtistaByNombre() throws Exception {
    ArtistaDTO artistaDTO = new ArtistaDTO();
    artistaDTO.setNombreArtistico("example");
    List<ArtistaDTO> artistaDTOS = List.of(artistaDTO);

    //given
    given(artistaService.buscarPorNombre("example")).willReturn(artistaDTOS);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        get("/Artista/buscarNombre?nombre=example").accept(
            MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(artistaListJacksonTester.write(artistaDTOS).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveArtistaByCampo() throws Exception {
    ArtistaDTO artistaDTO = new ArtistaDTO();
    artistaDTO.setCampoArtistico("example");
    List<ArtistaDTO> artistaDTOS = List.of(artistaDTO);

    //given
    given(artistaService.buscarPorCampo("example")).willReturn(artistaDTOS);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        get("/Artista/buscarCampo?campoArtistico=example").accept(
            MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(artistaListJacksonTester.write(artistaDTOS).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveArtistaByGenero() throws Exception {
    ArtistaDTO artistaDTO = new ArtistaDTO();
    artistaDTO.setGeneroMusical("example");
    List<ArtistaDTO> artistaDTOS = List.of(artistaDTO);

    //given
    given(artistaService.buscarPorGenero("example")).willReturn(artistaDTOS);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        get("/Artista/buscarGenero?genero=example").accept(
            MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(artistaListJacksonTester.write(artistaDTOS).getJson(),
        response.getContentAsString());
  }

  @Test
  void canSoftDeleteAnArtist() throws Exception {
    //given
    willDoNothing().given(artistaService).bajaArtista(1L);

    //when
    MockHttpServletResponse response = mockMvc.perform(
        patch("/Artista/baja?id=1")
            .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }
}