package com.pepe.app.safesurfing;

import java.util.Date;

public class Weather {
    private static final Weather ourInstance = new Weather();


    public static Weather getInstance() {
        return ourInstance;
    }

    private Weather() {
    }
    private String usuario ="";
    private String ciudad ="";
    private String x="";
    private String y="";
    private double latitud=36.830428;
    private double longitud=-2.405592;
    private double speed=0;
    private String windDirection="";
    private String direction="";
    private int id=0;
    private double wind=0;
    private Date fecha = new Date();
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad){
        this.ciudad=ciudad;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed){
        this.speed=speed;
    }
    public double getWind() {
        return wind;
    }
    public void setWind(double wind){
        this.wind=wind;
    }
    public String getWindDirection() {
        return windDirection;
    }
    public void setWindDirection(String windDirection){
        this.windDirection=windDirection;
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
    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud){
        this.latitud=latitud;
    }
    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud){
        this.longitud=longitud;
    }
    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction){
        this.direction=direction;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha){
        this.fecha=fecha;
    }
}
