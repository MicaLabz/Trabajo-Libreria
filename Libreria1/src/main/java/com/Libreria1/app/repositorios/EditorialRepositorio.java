package com.Libreria1.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.Libreria1.app.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
	
	@Query("SELECT c FROM Editorial c")
	public List<Editorial> buscarTodos();
	
}
