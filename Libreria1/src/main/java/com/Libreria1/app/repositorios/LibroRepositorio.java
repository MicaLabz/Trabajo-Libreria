package com.Libreria1.app.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Libreria1.app.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>  {
     
	 @Query("SELECT c FROM Libro c where c.autor.id = :id")
	 public Libro buscarLibroPorIdAutor(@Param("id") String id);
	 
	 @Query("SELECT c FROM Libro c where c.editorial.id = :id")
	 public Libro buscarLibroPorIdEditorial(@Param("id") String id);
	 
	 @Query("SELECT a from Libro a WHERE a.alta = true ")
     public List<Libro> buscarActivos();
	 
	 @Query("SELECT c FROM Libro c where c.titulo = :titulo")
	 public Optional<Libro> buscarLibroPorTitulo(@Param("titulo") String titulo);
}
