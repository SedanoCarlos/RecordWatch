package com.recordwatch.recordwatch.encriptacion;

import java.security.MessageDigest;

/**
 * Clase que contiene el método que convierte a formato hash una cadena de texto
 */
public class CodificaciónSHA {
    /**
     * Método que encripta en formato hash la cadena de texto introducida
     * @param contraseña string recibido en bytes
     * @param shaN tipo de codificación
     * @return el string en formato hash
     * @throws Exception en caso de error
     */
    public static byte[] encriptarContraseña(byte[] contraseña, String shaN) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(shaN);
        sha.update(contraseña);
        return sha.digest();
    }
}
