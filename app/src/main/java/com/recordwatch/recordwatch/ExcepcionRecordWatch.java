/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.recordwatch.recordwatch;
/**
 * En esta clase se gestiona la creación de objetos de tipo ExceptionTaller
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class ExcepcionRecordWatch extends Exception{
    private String mensajeUsuario;
    private String mensajeAdministrador;
    private Integer codigoError;
    /**
     * Este método permite crear un objeto ExcepcionTaller sin datos.
     *
     */
    public ExcepcionRecordWatch() {
    }
    /**
     * Este método crear un objeto ExcepcionTaller indicando todos sus datos.
     *
     * @param mensajeUsuario String que indica el mensaje de Usuario a insertar
     * @param mensajeAdministrador String que indica el mensaje de
     * administrador a insertar
     * @param codigoError Entero que indica el codigo de error a insertar

     */
    public ExcepcionRecordWatch(String mensajeUsuario, String mensajeAdministrador, Integer codigoError) {
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeAdministrador = mensajeAdministrador;
        this.codigoError = codigoError;
    }
    /**
     * Este método devuelve el mensaje de usuario.
     *
     * @return mensaje de usuario
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }
    /**
     * Este método permite establecer un mensaje para el usuario.
     *
     * @param mensajeUsuario String que indica el mensaje de usuario que
     * se insertará
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }
    /**
     * Este método devuelve el mensaje de administrador.
     *
     * @return mensaje de administrador
     */
    public String getMensajeAdministrador() {
        return mensajeAdministrador;
    }
    /**
     * Este método permite establecer un mensaje para el administrador.
     *
     * @param mensajeAdministrador String que indica el mensaje de
     * administrador que se insertará
     */
    public void setMensajeAdministrador(String mensajeAdministrador) {
        this.mensajeAdministrador = mensajeAdministrador;
    }
    /**
     * Este método devuelve el mensaje de administrador.
     *
     * @return codigo de error
     */
    public Integer getCodigoError() {
        return codigoError;
    }
    /**
     * Este método permite establecer un codigo de error.
     *
     * @param codigoError Entero que indica el codigo de error que
     * se insertará
     */
    public void setCodigoError(Integer codigoError) {
        this.codigoError = codigoError;
    }

    /**
     * Este método permite mostrar todos los datos de un objeto de tipo
     * ExcepcionTaller.
     *

     */
    @Override
    public String toString() {
        return "ExcepcionRecordWatch{" + "mensajeUsuario=" + mensajeUsuario + ", mensajeAdministrador=" + mensajeAdministrador + ", codigoError=" + codigoError + '}';
    }

}
