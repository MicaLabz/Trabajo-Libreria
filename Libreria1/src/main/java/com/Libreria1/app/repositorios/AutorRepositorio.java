package com.Libreria1.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Libreria1.app.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
	
	@Query("SELECT c FROM Autor c where c.nombre = :nombre")
	public Optional<Autor> buscarPorNombre1(@Param("nombre") String nombre);
	
	@Query("SELECT c FROM Autor c where c.nombre = :nombre")
	public List<Autor> buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT a from Autor a WHERE a.alta = true ")
	public List<Autor> buscarActivos();
}