package com.Libreria1.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Libreria1.app.entidades.Prestamo;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {
	
	@Query("SELECT p from Prestamo p WHERE p.alta = true ")
	public List<Prestamo> buscarActivos();
	
	@Query("SELECT p FROM Prestamo p where p.cliente.id = :id")
	public Prestamo buscarPrestamoPorIdCliente(@Param("id") String id);
	
	@Query("SELECT p FROM Prestamo p where p.libro.id = :id")
	public Prestamo buscarPrestamoPorIdLibro(@Param("id") String id);
	 
	
}
