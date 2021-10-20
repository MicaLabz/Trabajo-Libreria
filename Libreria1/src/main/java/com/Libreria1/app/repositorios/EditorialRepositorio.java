package com.Libreria1.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Libreria1.app.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
	
	@Query("SELECT c FROM Editorial c where c.nombre = :nombre")
	public Optional<Editorial> buscarPorNombre1(@Param("nombre") String nombre);
	
	@Query("SELECT c FROM Editorial c where c.nombre = :nombre")
	public List<Editorial> buscarPorNombre(@Param("nombre") String nombre);
	
	@Query("SELECT a from Editorial a WHERE a.alta = true ")
	public List<Editorial> buscarActivos();
}
