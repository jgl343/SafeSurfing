package com.pepe.app.safesurfing;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by 0011445 on 12/09/2016.
 */


public class WeatherHttpClient implements GetterAemetURI {

    private static String GENOVESES_URL = "http://magicseaweed.com/api/FvipTLqZWFQ37yS900tVYsP2HZ2t44ny/forecast/?spot_id=3540&units=eu&fields=timestamp,wind.speed,wind.direction,condition.weather&callback=jQuery";
    private static String ALMERIA_URL ="http://www.aemet.es/xml/municipios/localidad_04013.xml";
    private static String CARBONERAS_URL = "http://www.aemet.es/xml/municipios/localidad_04032.xml";

    public String getWeatherDataGenoveses() {
        HttpURLConnection con = null ;
        InputStream is = null;
        JSONObject msg = new JSONObject();
        try {
           // Webb webb = Webb.create();
           // JSONObject respuestaJSON = new JSONObject((webb.post(GENOVESES_URL).useCaches(false).body(msg).ensureSuccess().asJsonObject().getBody()).toString());
           // Log.v("repsuestaJSON", respuestaJSON.toString());
            con = (HttpURLConnection) ( new URL(GENOVESES_URL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
    public Document getWeatherDataCarboneras() {
        HttpURLConnection con = null ;
        InputStream is = null;
        Object msg= new Object();

        try {
            URL url = new URL(
                    CARBONERAS_URL);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            /*
            Webb webb = Webb.create();
            Object respuestaJSON=webb.post(GENOVESES_URL).useCaches(false).body(msg).ensureSuccess().asString().getBody();
            con = (HttpURLConnection) ( new URL(CARBONERAS_URL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            File fileXml = new File(buffer.toString());

            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = parser.parse(fileXml);

            Log.v("XMLXMLXMLXML", respuestaJSON.toString());*/
            return doc;
        }
        catch(Throwable t) {
            t.printStackTrace();
        }


        return null;

    }
    public Document getWeatherDataAlmeria() {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            URL url = new URL(
                    ALMERIA_URL);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            return doc;
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        /*finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }*/

        return null;

    }


    @Override
    public String getAemetURI(String provincia, String municipio) {
        return null;
    }
}
