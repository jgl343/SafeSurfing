package com.pepe.app.safesurfing;

import java.util.Date;

/**
 * Created by Pepe y Rocio on 05/07/2017.
 */

public class Posicion {

    private String x="";
    private String y="";
    private int id=0;
    private double wind=0;
    private Date fecha = new Date();

    public Posicion(){

    }
    public Posicion(String x, String y, double wind, int id, Date fecha){
        this.x=x;
        this.y=y;
        this.id=id;
        this.wind=wind;
        this.fecha=fecha;
    }
    public double getWind() {
        return wind;
    }
    public void setWind(double wind){
        this.wind=wind;
    }
    public String getX() {
        return x;
    }
    public void setX(String x){
        this.x=x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y){
        this.y=y;
    }

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
}
