package com.Libreria1.app.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.Libreria1.app.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>  {
     
	 @Query("SELECT c FROM Libro c")
	 public List<Libro> buscarTodos();
	 
}
