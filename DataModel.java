package p2;

import java.util.*;
import java.io.*;
import org.w3c.dom.Document;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPathFactory;


public class DataModel {

    public static Examen getExamen(){
          Document doc = Sint77P2.getDocument();
          Examen ex = new Examen();
          XPathFactory xpathFactory = XPathFactory.newInstance();
          XPath xpath = xpathFactory.newXPath();
          try {
                ex.setCont((String) xpath.evaluate("/examen/datos/@cont", doc.getDocumentElement() , XPathConstants.STRING));
                ex.setID((String) xpath.evaluate("/examen/datos/@id", doc.getDocumentElement() , XPathConstants.STRING));
                ex.setGenero((String) xpath.evaluate("/examen/datos/text()", doc.getDocumentElement() , XPathConstants.STRING));
                ex.setComentario((String) xpath.evaluate("/examen/text()", doc.getDocumentElement() , XPathConstants.STRING));
          } catch(Exception e) {
                ex.setCont("AH");
          }
          return ex;
    }
    public static ArrayList <String>  getQ1Years(){
          ArrayList <String> listaYears = new ArrayList <String>();
          HashMap <String, Document> years = Sint77P2.getValidosKeys();
          for (String key: years.keySet()) {
 	    if (key.length()==4)
              listaYears.add (key);
          }
          listaYears.sort(Comparator.reverseOrder());
          return listaYears;
    }

    public static ArrayList <Movie> getQ1Movies (String year){
          ArrayList <Movie> listaMovies = new ArrayList <Movie>();
          XPath xpath = XPathFactory.newInstance().newXPath();
          HashMap <String, Document> years = Sint77P2.getValidosKeys();
          NodeList movies = null;
          try {
              movies = (NodeList) xpath.evaluate("/Movies/Movie", years.get(year), XPathConstants.NODESET);
          } catch(Exception e) {
              return listaMovies;
          }
          for (int i = 0; i < movies.getLength(); i++) {
              try {
                  Movie movie = new Movie();
          ArrayList<String> genre = new ArrayList <String>();
                  movie.setTitle((String) xpath.evaluate("Title/text()", movies.item(i), XPathConstants.STRING));
                  movie.setDuration((String) xpath.evaluate("Duration/text()", movies.item(i), XPathConstants.STRING));
                  NodeList nodeList = (NodeList) xpath.evaluate("/Movies/Movie[Title='"+movie.getTitle()+"']/Genre", movies.item(i), XPathConstants.NODESET);
                  for (int j = 0;j < nodeList.getLength(); ++j) {
                    String text = nodeList.item(j).getTextContent();
                	genre.add(text);
                  }
                  movie.setGenre(genre);
		              movie.setSynopsis(((String) xpath.evaluate("text()[normalize-space()]", movies.item(i), XPathConstants.STRING)).trim());
                  movie.setLang((String) xpath.evaluate("@langs", movies.item(i), XPathConstants.STRING));
                  listaMovies.add(movie);
                  // EXAMEN //
                  for (int k=0; k<genre.size(); k++){
                  if (genre.get(k).equals(getExamen().getGenero())){
                      listaMovies.remove (movie);
                  }
                  }
              } catch(Exception e) {
              }
          }
          Collections.sort(listaMovies);
          return listaMovies;
    }

    public static ArrayList <Cast> getQ1Casts (String year, String movie){
          ArrayList <Cast> listaCasts = new ArrayList <Cast> ();
          XPath xpath = XPathFactory.newInstance().newXPath();
          HashMap <String, Document> years = Sint77P2.getValidosKeys();
          NodeList casts = null;
          try {
              casts = (NodeList) xpath.evaluate("/Movies/Movie[Title='"+movie+"']/Cast", years.get(year), XPathConstants.NODESET);
          } catch(Exception e) {
              return listaCasts;
          }
          for (int i = 0; i < casts.getLength(); i++) {
              try {
                  Cast cast = new Cast();
                  cast.setName((String) xpath.evaluate("Name/text()", casts.item(i), XPathConstants.STRING));
                  cast.setRole((String) xpath.evaluate("Role/text()", casts.item(i), XPathConstants.STRING));
                  String phone = (String) xpath.evaluate("Phone/text()", casts.item(i), XPathConstants.STRING);
                  if (phone.length() != 0){
                  cast.setContact((String) xpath.evaluate("Phone/text()", casts.item(i), XPathConstants.STRING));
                  }
                  else {
                  cast.setContact((String) xpath.evaluate("Email/text()", casts.item(i), XPathConstants.STRING));
                }
                  String ID = (String) xpath.evaluate("@id", casts.item(i), XPathConstants.STRING);
                  cast.setID(ID);
                  if (ID.equals(getExamen().getID())){
                    cast.setContact(getExamen().getCont());
                  }
                  listaCasts.add(cast);
              } catch(Exception e) {
              }
          }
          Collections.sort(listaCasts);
          return listaCasts;
    }

}
