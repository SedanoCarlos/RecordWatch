package com.recordwatch.recordwatch.pojos;

/**
 * En esta clase se gestiona la creación de objetos de tipo Usuario
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class Usuario {
    private String contrasena;

    /**
     * Este método permite crear un objeto Usuario sin datos.
     *
     */
    public Usuario() {
    }

    /**
     * Este método crear un objeto Usuario indicando todos sus datos.
     *
     * @param contrasena String que indica la contraseña del usuario a insertar
     */
    public Usuario(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Este método devuelve la contraseña de un usuario.
     *
     * @return contraseña del usuario
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Este método permite establecer la contraseña de un usuario.
     *
     * @param contrasena String que indica la contraseña del usuario que se establecerá
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo Usuario.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo Usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "contrasena='" + contrasena + '\'' +
                '}';
    }
}
