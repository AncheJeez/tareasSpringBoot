package com.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
/**
 * https://spring.io/guides/gs/validating-form-input
 */
@Entity
public class Game {
		//añadios ID
		@Id
		@GeneratedValue(strategy =GenerationType.AUTO)
		private Integer id;
		@NotBlank(message = "El titulo no puede estar vacio")
		private String titulo;
		@Positive(message = "El precio tiene que ser positivo")
		private Double precio;
		
		public Game() {}
		
		public Game(int id, String titulo, Double precio) {
			super();
			this.id = id;
			this.titulo = titulo;
			this.precio = precio;
			
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public Double getPrecio() {
			return precio;
		}

		public void setPrecio(Double precio) {
			this.precio = precio;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Game [id=" + id + ", titulo=" + titulo + ", precio=" + precio + "]";
		}
		
			

}
