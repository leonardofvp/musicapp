package com.nocountry.s12.Controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.nocountry.s12.Dto.Request.AlbumRequestDTO;
import com.nocountry.s12.Dto.Response.AlbumResponseDTO;
import com.nocountry.s12.Jwt.JwtService;
import com.nocountry.s12.ServiceImpl.AlbumServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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
@WebMvcTest(AlbumController.class)
@AutoConfigureMockMvc(addFilters = false)
//@WithMockUser
public class AlbumControllerMockMvcTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private AlbumServiceImpl albumService;
  @MockBean
  private JwtService jwtService;
  @Autowired
  private JacksonTester<AlbumResponseDTO> albumResponseDTOJacksonTester;
  @Autowired
  private JacksonTester<List<AlbumResponseDTO>> albumResponseDTOListJacksonTester;
  @Autowired
  private JacksonTester<AlbumRequestDTO> requestDTOJacksonTester;

  @Test
  void canRetrieveAllAlbum() throws Exception {
    List<AlbumResponseDTO> dtoList = List.of(
        new AlbumResponseDTO(1L, "Rock", LocalDate.now(), List.of()),
        new AlbumResponseDTO(2L, "Pop", LocalDate.now(), List.of()),
        new AlbumResponseDTO(3L, "Internacional", LocalDate.now(), List.of()));

    //given
    given(albumService.listar()).willReturn(dtoList);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/album").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(albumResponseDTOListJacksonTester.write(dtoList).getJson(),
        response.getContentAsString());

  }

  @Test
  void canRetrieveAlbumByIdWhenExist() throws Exception {
    AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(1L, "Rock",
        LocalDate.now(), List.of());

    //given
    given(albumService.obtenerPorID(1L)).willReturn(albumResponseDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/album/1").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        albumResponseDTOJacksonTester.write(albumResponseDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canRetrieveAlbumByIdWhenDoesNotExist() throws Exception {
    //given
    given(albumService.obtenerPorID(1L)).willThrow(new EntityNotFoundException(
        "Entidad no encontrada")); //Replace with custom exception

    //when
    MockHttpServletResponse response = mockMvc.perform(
            get("/album/1").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertAll(
        "Should return 404 Not Found status and empty body",
        () -> assertEquals(response.getStatus(), HttpStatus.NOT_FOUND.value()),
        () -> assertTrue(response.getContentAsString().isEmpty())
    );

  }

  @Test
  void canCreateAlbum() throws Exception {
    AlbumRequestDTO albumRequestDTO = new AlbumRequestDTO("Rock",
        LocalDate.now().toString(), List.of(), "ArtistName");

    //when
    MockHttpServletResponse response = mockMvc.perform(
            post("/album").contentType(MediaType.APPLICATION_JSON)
                .content(requestDTOJacksonTester.write(albumRequestDTO).getJson()))
        .andReturn().getResponse();

    //then
    assertEquals(HttpStatus.CREATED.value(), response.getStatus());
  }

  @Test
  void canModifyAlbumById() throws Exception {
    AlbumRequestDTO albumRequestDTO = new AlbumRequestDTO("Pop",
        LocalDate.now().toString(), List.of(), "ArtistName");
    AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(1L, "Pop",
        LocalDate.now(), List.of());

    //given
    given(albumService.modificar(1L, albumRequestDTO)).willReturn(
        albumResponseDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            put("/album/1").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDTOJacksonTester.write(albumRequestDTO).getJson()))
        .andReturn().getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        albumResponseDTOJacksonTester.write(albumResponseDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canSoftDeleteAlbumById() throws Exception {
    AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO(1L, "Pop",
        LocalDate.now(), List.of());

    //given
    given(albumService.baja(1L)).willReturn(albumResponseDTO);

    //when
    MockHttpServletResponse response = mockMvc.perform(
            put("/album/baja/1").accept(MediaType.APPLICATION_JSON)).andReturn()
        .getResponse();

    //then
    assertEquals(HttpStatus.OK.value(), response.getStatus());
    assertEquals(
        albumResponseDTOJacksonTester.write(albumResponseDTO).getJson(),
        response.getContentAsString());
  }

  @Test
  void canDeleteAlbumById() throws Exception {
    //when
    MockHttpServletResponse response = mockMvc.perform(delete("/album/1"))
        .andReturn()
        .getResponse();

    //then
    assertThat(response.getStatus(), anyOf(is(HttpStatus.ACCEPTED.value()),
        is(HttpStatus.NO_CONTENT.value())));
  }
}
