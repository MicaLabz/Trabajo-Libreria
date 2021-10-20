package com.Libreria1.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Libreria1.app.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
	
	@Query("SELECT c from Cliente c WHERE c.alta = true ")
	public List<Cliente> buscarActivos();
	
	@Query("SELECT c FROM Cliente c where c.documento = :documento")
	public Optional<Cliente> buscarPorDocumento(@Param("documento") Long documento);

}
