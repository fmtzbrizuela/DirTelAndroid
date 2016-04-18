package com.example.fmb.dirtel;
/**
 * Created by fmb on 02/04/2016.
 */
public class DirOrg {
    private String Organiza;
    private String Nombre;
    private String Tel1;
    private String Tel2;
    private String Fax1;
    private String Fax2;
    private String website;
    private String Calle;
    private String Colonia;
    private String Localidad;
    private String Estado;
    private String CP;
    private String Pais;
    private String Giro;
    private String accion;


    public String getOrganiza() {return Organiza;}
    public String getNombre() {return Nombre;}
    public String getTel1() {return Tel1;}
    public String getTel2() {return Tel2;}
    public String getFax1() {return Fax1;}
    public String getFax2() {return Fax2;}
    public String getwebsite() {return website;}
    public String getCalle() {return Calle;}
    public String getColonia() {return Colonia;}
    public String getLocalidad() {return Localidad;}
    public String getEstado() {return Estado;}
    public String getCP() {return CP;}
    public String getPais() {return Pais;}
    public String getGiro() {return Giro;}
    public String getAccion() {return accion;}

    public void setOrganiza(String value) { Organiza = value;}
    public void setNombre(String value) { Nombre = value;}
    public void setTel1(String value) { Tel1 = value;}
    public void setTel2(String value) { Tel2 = value;}
    public void setFax1(String value) { Fax1 = value;}
    public void setFax2(String value) { Fax2 = value;}
    public void setwebsite(String value) { website = value;}
    public void setCalle(String value) { Calle = value;}
    public void setColonia(String value) { Colonia = value;}
    public void setLocalidad(String value) { Localidad = value;}
    public void setEstado(String value) { Estado = value;}
    public void setCP(String value) { CP = value;}
    public void setPais(String value) { Pais = value;}
    public void setGiro(String value) { Giro = value;}
    public void setAccion(String value) { accion = value;}

    public void setByIndex(int index, String value){
        switch(index){
            case  0: setOrganiza(value); break;
            case  1: setNombre(value); break;
            case  2: setTel1(value); break;
            case  3: setTel2(value); break;
            case  4: setFax1(value); break;
            case  5: setFax2(value); break;
            case  6: setwebsite(value); break;
            case  7: setCalle(value); break;
            case  8: setColonia(value); break;
            case  9: setLocalidad(value); break;
            case 10: setEstado(value); break;
            case 11: setCP(value); break;
            case 12: setPais(value); break;
            case 13: setGiro(value); break;
            case 14: setAccion(value); break;
        }

    }
}
