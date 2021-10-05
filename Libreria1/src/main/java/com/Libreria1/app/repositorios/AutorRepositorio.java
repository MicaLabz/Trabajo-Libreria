package com.Libreria1.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.Libreria1.app.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
	
	@Query("SELECT c FROM Autor c")
	public List<Autor> buscarAutores();
	
	

}
