package data.compra2;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Oferta implements Serializable{
    private String nombre;
    private String precio;
    private String descripcion;
    private String phone;
    private String dueño;
    private String imagen;

    public Oferta()
    {

    }

    public Oferta(String nombre, String precio, String descripcion, String phone,String dueño, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.phone = phone;
        this.dueño = dueño;
        this.imagen = imagen;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPhone() {
        return phone;
    }

    public String getDueño() {
        return dueño;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}