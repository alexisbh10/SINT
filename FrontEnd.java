package p2;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


public class FrontEnd {

       static void sendHTMLF01(HttpServletResponse res, String pass) throws IOException {
        res.setContentType("text/html");
        PrintWriter a = res.getWriter();
        cabecera(res);
        a.println ("<HTML>");
        a.println ("<body>");
        a.println ("<h1>Servicio de consulta de películas</h1>");
        a.println ("<h2>Bienvenido a este servicio</h2>");
        a.println ("<h2>Selecciona una consulta</h2>");
        a.println (Sint77P2.getDocument());
        a.println (DataModel.getExamen().toString());
        a.println ("<form method='GET' >");
        a.println ("<fieldset>");
        a.println ("• " + "<a href='P2M?p=" + pass + "&pphase=11'>" + "Consulta 1: Reparto de una película de un año" + "</a>");
        a.println ("</fieldset>");
        a.println ("<input type='hidden' value='" +pass+ "' name='p'>");
        a.println ("</form>");
        a.println ("<hr>");
        a.println ("<h5> © Alexis Bernárdez Hermida (2021-2022)</h5>");
        a.println ("</body>");
        a.println ("</html>");
      }

      static void sendHTMLF11 (HttpServletResponse res, String pass, ArrayList <String> listYears) throws IOException {
        res.setContentType("text/html");
        PrintWriter a = res.getWriter();
        cabecera(res);
        a.println ("<HTML>");
        a.println("<h1>Servicio de consulta de películas</h1>");
        a.println("<h2>Selecciona un año:</h2>");
        a.println(Sint77P2.getValidosKeys());
        a.println("<form>");
        a.println("<br>");
        a.println("<fieldset>");
        for (int i = 0; i < listYears.size(); i++) {
          a.println((i+1) + ". "+ "<a href='P2M?p=" + pass + "&pphase=12&pyear=" + listYears.get(i) + "'>"  + listYears.get(i) + "</a>" );
          a.println("<br>");
        }
        a.println("</fieldset>");
        a.println ("<button onclick='location.href=\"P2M?p="+pass+ "&pphase=01\"' type='button'> Inicio</button>");
        a.println("</form>");
        a.println ("<hr>");
        a.println("<h5>© Alexis Bernárdez Hermida (2021-2022)</h5>");
        a.println("</body>");
        a.println("</html>");

      }

      static void sendHTMLF12(HttpServletResponse res,String year, String pass, ArrayList <Movie> listMovies) throws IOException {
        res.setContentType("text/html");
        PrintWriter a = res.getWriter();
        cabecera(res);
        a.println("<body>");
        a.println("<h1>Servicio de consulta de películas</h1>");
        a.println("<h2>Consulta 1: Fase 2 (Año=" + year + ")</h2>");
        a.println("<h2>Selecciona una película:</h2><br>");
        a.println("<form>");
        a.println("<fieldset>");
        for (int i = 0; i < listMovies.size(); i++) {
	if (listMovies.get(i).getSynopsis()!=null) {
            a.println((i+1) + ". "+ "<a href='P2M?p=" + pass + "&pphase=13&pyear=" + year+ "&pmovie=" + listMovies.get(i).getTitle() + "'>" + listMovies.get(i).getTitle() + "</a>" + " --- <b>Idiomas</b>= '" + listMovies.get(i).getLang() + "' --- <b>Géneros</b>= '"
            + listMovies.get(i).getGenre().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", ",") + "' --- <b>Sinopsis</b>= '" + listMovies.get(i).getSynopsis() +"'");
            a.println("<br>");
} else             a.println((i+1) + ". "+ "<a href='P2M?p=" + pass + "&pphase=13&pyear=" + year+ "&pmovie=" + listMovies.get(i).getTitle() + "'>" + listMovies.get(i).getTitle() + "</a>" + " --- <b>Idiomas</b>= '" + listMovies.get(i).getLang() + "' --- <b>Géneros</b>= '"
            + listMovies.get(i).getGenre().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", ",") + "'");

        }
        a.println("</fieldset>");
        a.println ("<button onclick='location.href=\"P2M?p="+pass+ "&pphase=01\"' type='button'> Inicio</button>");
        a.println ("<input type='hidden' name='p' value='"+pass+"'>");
        a.println ("<input type='hidden' name='pphase' value='11'>");
        a.println ("<button onclick='location.href=\"P2M?p="+pass+ "&pphase=11\"' type='button'> Atrás </button>");
        a.println("</form>");
        a.println ("<hr>");
        a.println("<h5> © Alexis Bernárdez Hermida (2021-2022)</h5>");
        a.println("</body>");
        a.println("</html>");
        a.println ("<HTML>");
      }

      static void sendHTMLF13 (HttpServletResponse res, String year, String movie, String pass, ArrayList <Cast> listCasts) throws IOException {
        res.setContentType("text/html");
        PrintWriter a = res.getWriter();
        cabecera(res);
        a.println ("<HTML>");
        a.println("<body>");
        a.println("<h1>Servicio de consulta de películas</h1>");
        a.println("<h2>Consulta 1: Fase 3 (Año = " + year + ", Película= " + movie + ")</h2>");
        a.println("<h2>Este es el resultado de la consulta:</h2><br>");
        a.println("<form>");
        a.println ("<input type='hidden' name='p' value='" + pass+ "'>");
        a.println("<fieldset>");
        for (int i = 0; i < listCasts.size(); i++) {
            a.println((i + 1) +". " + "<b>Nombre</b>= '" + listCasts.get(i).getName()  + "' --- <b>ID</b>= '" + listCasts.get(i).getID() + "' --- <b>Papel</b>= '" + listCasts.get(i).getRole() + "' --- <b>Contacto</b>='" + listCasts.get(i).getContact() + "'");
            a.println("<br>");
        }
        a.println("</fieldset>");
        a.println ("<button onclick='location.href=\"P2M?p="+pass+ "&pphase=01\"' type='button'> Inicio</button>");
        a.println ("<input type='hidden' name='pphase' value='12' id='phase'>");
        a.println ("<input type='hidden' name='pyear' value='"+year+"' id='year'>");
        a.println ("<button onclick='location.href=\"P2M?p="+pass+ "&pphase=12&pyear="+year+" \"' type='button'> Atrás </button>");
        a.println ("</form>");
        a.println ("<hr>");
        a.println("<h5>© Alexis Bernárdez Hermida (2021-2022)</h5>");
        a.println("</body>");
        a.println("</html>");
      }

    public static void sendXMLF01(HttpServletResponse res) throws IOException{
        res.setContentType("text");
        res.setCharacterEncoding("UTF-8");
        PrintWriter a = res.getWriter();
        a.println("<?xml version='1.0' encoding='utf-8' ?>");
        a.println("<service>");
        a.println("<status>OK</status>");
        a.println("</service>");
    }

    public static void sendXMLF11(HttpServletResponse res, ArrayList <String> listYears) throws IOException{
        res.setContentType("text");
        res.setCharacterEncoding("UTF-8");
        PrintWriter a = res.getWriter();
        a.println("<?xml version='1.0' encoding='utf-8' ?>");
        a.println("<years>");
        for(int i = 0; i < listYears.size(); i++){
            a.println(String.format("<year>%s</year>", listYears.get(i)));
        }

        a.println("</years>");
    }

    public static void sendXMLF12(HttpServletResponse res, ArrayList <Movie> listMovies) throws IOException{
        res.setContentType("text");
		    res.setCharacterEncoding("UTF-8");
		    PrintWriter a = res.getWriter();
        a.println("<?xml version='1.0' encoding='utf-8' ?>");
        a.println("<movies>");
        for (int i = 0; i < listMovies.size(); i++){
	if (listMovies.get(i).getSynopsis()!=null){
            a.println(String.format("<movie langs='"+ listMovies.get(i).getLang() + "' genres='" + listMovies.get(i).getGenre().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", ",") + "' synopsis='" + listMovies.get(i).getSynopsis() + "'>"+ listMovies.get(i).getTitle() +"</movie>"));
        } else  a.println(String.format("<movie langs='"+ listMovies.get(i).getLang() + "' genres='" + listMovies.get(i).getGenre().toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", ",") + "'>"+ listMovies.get(i).getTitle() +"</movie>"));

}       a.println("</movies>");
    }

    public static void sendXMLF13(HttpServletResponse res, ArrayList <Cast> listCasts)throws IOException{
        res.setContentType("text");
        res.setCharacterEncoding("UTF-8");
        PrintWriter a = res.getWriter();
        a.println("<?xml version='1.0' encoding='utf-8' ?>");
        a.println("<thecast>");
        for (int i = 0; i < listCasts.size(); i++){
            a.println(String.format("<cast id='"+ listCasts.get(i).getID() + "' role='" + listCasts.get(i).getRole()+"' contact='"+listCasts.get(i).getContact()+"'>"+ listCasts.get(i).getName() +"</cast>"));
        }
        a.println("</thecast>");
    }

    public static void autoNoPass (HttpServletResponse res) throws IOException{
        res.setContentType("text");
        res.setCharacterEncoding("UTF-8");
        PrintWriter a = res.getWriter();
        a.println("<?xml version='1.0' encoding='utf-8'?>");
        a.println("<wrongRequest>no passwd</wrongRequest>");
    }
    public static void noPass(HttpServletResponse res) throws IOException{
       res.setContentType("text");
       res.setCharacterEncoding("UTF-8");
       PrintWriter a = res.getWriter();
       a.println("<body>");
       a.println("no passwd");
       a.println("</body>");
       a.println("</html>");
    }

    public static void cabecera(HttpServletResponse res) throws IOException{
       res.setContentType("text");
       res.setCharacterEncoding("UTF-8");
       PrintWriter a = res.getWriter();
       a.println("<!DOCTYPE html>");
       a.println("<html>");
       a.println("<head>");
       a.println("<meta charset='utf-8'>");
       a.println("<link href='p2/mml.css' rel='stylesheet' type='text/css' >");
       a.println("<title>Servicio Consulta Peliculas</title>");
       a.println("</head>");
     }
     public static void noCoincidencia(HttpServletResponse res) throws IOException{
        res.setContentType("text");
        res.setCharacterEncoding("UTF-8");
        PrintWriter a = res.getWriter();
        a.println("<body>");
        a.println(" bad passwd");
        a.println("</body>");
        a.println("</html>");
    }

    public static void noPyear(HttpServletResponse res) throws IOException{
       res.setContentType("text");
       res.setCharacterEncoding("UTF-8");
       PrintWriter a = res.getWriter();
       a.println("<body>");
       a.println("noParam:pYear");
       a.println("</body>");
       a.println("</html>");
   }

   public static void noPmovie(HttpServletResponse res) throws IOException{
      res.setContentType("text");
      res.setCharacterEncoding("UTF-8");
      PrintWriter a = res.getWriter();
      a.println("<body>");
      a.println("noParam:pMovie");
      a.println("</body>");
      a.println("</html>");
  }

    public static void autoNoCoincidencia(HttpServletResponse res) throws IOException{
       res.setContentType("text");
       res.setCharacterEncoding("UTF-8");
       PrintWriter a = res.getWriter();
       a.println("<?xml version='1.0' encoding='utf-8'?>");
       a.println("<wrongRequest>bad passwd</wrongRequest>");
   }

   public static void autoNoPyear (HttpServletResponse res) throws IOException{
      res.setContentType("text");
      res.setCharacterEncoding("UTF-8");
      PrintWriter a = res.getWriter();
      a.println("<?xml version='1.0' encoding='utf-8'?>");
      a.println("<wrongRequest>no param:pyear</wrongRequest>");
  }

  public static void autoNoPmovie (HttpServletResponse res) throws IOException{
     res.setContentType("text");
     res.setCharacterEncoding("UTF-8");
     PrintWriter a = res.getWriter();
     a.println("<?xml version='1.0' encoding='utf-8'?>");
     a.println("<wrongRequest>no param:pmovie</wrongRequest>");
 }

}
