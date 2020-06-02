package com.recordwatch.recordwatch.googledrive;

import com.google.api.client.util.DateTime;

/**
 * En esta clase se gestiona la creación de objetos de tipo GoogleDriveFileHolder
 * y la inserción de sus datos.
 *
 * @author Carlos
 */
public class GoogleDriveFileHolder {

    private String id;
    private String name;
    private DateTime modifiedTime;
    private long size;


    /**
     * Este método devuelve el id.
     *
     * @return id del objeto
     */
    public String getId() {
        return id;
    }

    /**
     * Este método permite establecer el id de un fichero.
     *
     * @param id String que indica el id del fichero que se establecerá
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Este método devuelve el nombre del fichero.
     *
     * @return nombre del objeto
     */
    public String getName() {
        return name;
    }

    /**
     * Este método permite establecer el nombre de un fichero.
     *
     * @param name String que indica el nombre del fichero que se establecerá
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Este método permite establecer la fecha de modificacion de un fichero.
     *
     * @param modifiedTime Entero que indica la fecha de modificacion que se establecerá
     */
    public void setModifiedTime(DateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Este método devuelve el tamaño del archivo.
     *
     * @return tamaño del objeto
     */
    public long getSize() {
        return size;
    }

    /**
     * Este método permite establecer el tamaño de un fichero.
     *
     * @param size Entero que indica el tamaño del fichero que se establecerá
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Este método permite mostrar todos los datos de
     * un objeto de tipo GoogleDriveFileHolder.
     *
     * @return Cadena de texto con los todos y cada uno de los datos de un
     * objeto de tipo GoogleDriveFileHolder

     */
    @Override
    public String toString() {
        return "GoogleDriveFileHolder{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", modifiedTime=" + modifiedTime +
                ", size=" + size +
                '}';
    }
}
