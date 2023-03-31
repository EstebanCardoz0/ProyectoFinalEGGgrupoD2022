package com.QuinchApp.Entidades;

import com.QuinchApp.Enums.PropiedadEnum;
import com.QuinchApp.Enums.ServicioEnum;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "propiedad")
public class Propiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPropiedad;
    private String nombre;
    private String ubicacion;
    private String Descripcion;
    private double valor;
    private int capacidad;
    private boolean disponibilidad;
    @Enumerated(EnumType.STRING)
    private PropiedadEnum tipoDePropiedad;
    @OneToOne
    private Propietario propietario;
    @OneToMany
    private List<Imagen> imagenes;
    @ElementCollection   //solo con ElementCollection puedo utilizar List de Enums
    @Enumerated(EnumType.STRING)
    private List<ServicioEnum> servicios;

    public Propiedad() {
    }

    public Propiedad(Integer idPropiedad, String nombre, String ubicacion, String Descripcion, double valor, int capacidad, boolean disponibilidad, PropiedadEnum tipoDePropiedad, Propietario propietario, List<Imagen> imagenes, List<ServicioEnum> servicios) {
        this.idPropiedad = idPropiedad;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.Descripcion = Descripcion;
        this.valor = valor;
        this.capacidad = capacidad;
        this.disponibilidad = disponibilidad;
        this.tipoDePropiedad = tipoDePropiedad;
        this.propietario = propietario;
        this.imagenes = imagenes != null ? imagenes : new ArrayList<>();
        this.servicios = servicios != null ? servicios : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Propiedad [idPropiedad=" + idPropiedad + ", nombre=" + nombre + ", ubicacion=" + ubicacion
                + ", Descripcion=" + Descripcion + ", valor=" + valor + ", capacidad=" + capacidad
                + ", disponibilidad=" + disponibilidad + ", tipoDePropiedad=" + tipoDePropiedad + "]";
    }

}

