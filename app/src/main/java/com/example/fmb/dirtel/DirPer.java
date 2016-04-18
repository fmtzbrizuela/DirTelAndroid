package com.example.fmb.dirtel;

import java.io.Serializable;

/**
 * Created by fmb on 02/04/2016.
 */
public class DirPer implements Serializable{
    private String idPer;
    private String Organiza;
    private String NomAP;
    private String NomAM;
    private String Nom1;
    private String Nom2;
    private String TelDirecto;
    private String FaxDirecto;
    private String Extension;
    private String TelCasa;
    private String TelCel;
    private String eMail;
    private String Puesto;
    private String Comentarios;
    private String accion;

    public String getidPer() {return idPer;}
    public String getOrganiza() {return Organiza;}
    public String getNomAP() {return NomAP;}
    public String getNomAM() {return NomAM;}
    public String getNom1() {return Nom1;}
    public String getNom2() {return Nom2;}
    public String getTelDirecto() {return TelDirecto;}
    public String getFaxDirecto() {return FaxDirecto;}
    public String getExtension() {return Extension;}
    public String getTelCasa() {return TelCasa;}
    public String getTelCel() {return TelCel;}
    public String geteMail() {return eMail;}
    public String getPuesto() {return Puesto;}
    public String getComentarios() {return Comentarios;}
    public String getAccion() {return accion;}

    public void setidPer(String value) { idPer = value;}
    public void setOrganiza(String value) { Organiza = value;}
    public void setNomAP(String value) { NomAP = value;}
    public void setNomAM(String value) { NomAM = value;}
    public void setNom1(String value) { Nom1 = value;}
    public void setNom2(String value) { Nom2 = value;}
    public void setTelDirecto(String value) { TelDirecto = value;}
    public void setFaxDirecto(String value) { FaxDirecto = value;}
    public void setExtension(String value) { Extension = value;}
    public void setTelCasa(String value) { TelCasa = value;}
    public void setTelCel(String value) { TelCel = value;}
    public void seteMail(String value) { eMail = value;}
    public void setPuesto(String value) { Puesto = value;}
    public void setComentarios(String value) { Comentarios = value;}
    public void setAccion(String value) { accion = value;}


    public void setByIndex(int index, String value){
        switch(index){
            case 0: setidPer(value); break;
            case 1: setOrganiza(value); break;
            case 2: setNomAP(value); break;
            case 3: setNomAM(value); break;
            case 4: setNom1(value); break;
            case 5: setNom2(value); break;
            case 6: setTelDirecto(value); break;
            case 7: setFaxDirecto(value); break;
            case 8: setExtension(value); break;
            case 9: setTelCasa(value); break;
            case 10: setTelCel(value); break;
            case 11: seteMail(value); break;
            case 12: setPuesto(value); break;
            case 13: setComentarios(value); break;
            case 14: setAccion(value); break;
        }
    }
}
