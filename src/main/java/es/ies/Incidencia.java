
package es.ies;

public class Incidencia {
    private String nombre;
    private String descripcion;
    private String estado;

    public Incidencia(String nom, String descr, String est) {
        setNombre(nom);
        setDescripcion(descr);
        setEstado(est);
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nom) {
        this.nombre = nom;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descr) {
        this.descripcion = descr;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String est) {
        this.estado = est;
    }
}
