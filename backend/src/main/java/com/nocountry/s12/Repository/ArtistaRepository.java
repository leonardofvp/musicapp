package com.nocountry.s12.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nocountry.s12.models.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
	Optional<Artista> findByUsername(String username);
	
	List<Artista> findByAltaTrue();
	
    @Query("SELECT a FROM Artista a WHERE a.nombreArtistico LIKE %:nombre% and a.alta = true")
    public List<Artista> buscarPorNombre(@Param("nombre") String nombre);

	List<Artista> findByCampoArtistico(String campo);

	List<Artista> findByGeneroMusical(String genero);
		

}

