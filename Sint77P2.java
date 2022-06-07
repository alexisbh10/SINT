package p2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.namespace.QName;
import java.io.IOException;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.validation.*;
import javax.xml.transform.dom.*;

public class Sint77P2 extends HttpServlet {
  private final static String PASSWD = "Teleco2022";
  private final static String INIT_DOC = "http://alberto.gil.webs.uvigo.es/SINT/21-22/mml2001.xml";
  private static HashMap<String, Document> docsValidos = new HashMap<String, Document>();
  private static String host = "/home/alexisbh/public_html/webapps";
  private static int port = 0;
  private static Document dc = null;

  public void init(ServletConfig config) {
      ServletContext ctx = config.getServletContext();
      try {
              parseXML(ctx.getResource("/p2/mml.xsd"), ctx.getResource("examen.xml"));
      } catch (Exception e1) {
          e1.printStackTrace();
      }
  }

    public void doGet (HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String pass= req.getParameter("p");
        String phase= req.getParameter("pphase");
	String auto = req.getParameter("auto");
        res.setContentType("text");
		    res.setCharacterEncoding("UTF-8");
        PrintWriter a= res.getWriter();
        req.setCharacterEncoding("UTF-8");

        port = req.getServerPort();

        /*if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){
            auto = true;
        } else{
            auto = false;
        }*/
        if (pass==null){
            if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){
                FrontEnd.autoNoPass(res);
                return;
            } else {
                FrontEnd.cabecera(res);
                FrontEnd.noPass(res);
                return;
            }
        }
        else if (!pass.equals(PASSWD)){
            if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true"))){
                FrontEnd.autoNoCoincidencia(res);
                return;
            } else {
                FrontEnd.cabecera(res);
                FrontEnd.noCoincidencia(res);
                return;
            }
        }

        else {
          if ((phase == null) || (phase.equals("01"))){
              doGetPhase01 (req,res);
          }
          else if (phase.equals("11")){
              doGetPhase11 (req,res);
          }
          else if (phase.equals("12")){
              doGetPhase12 (req,res);
          }
          else if (phase.equals("13")){
              doGetPhase13 (req,res);
          }
        }
    }

    public static void parseXML (URL mmlU, URL exn) throws Exception{
          ArrayList<SAXParseException> errors = new ArrayList<SAXParseException>();
          ArrayList<SAXParseException> warnings = new ArrayList<SAXParseException>();
          ArrayList<SAXParseException> fatalerrors = new ArrayList<SAXParseException>();

          //Lista de documentos no parseados
          LinkedList <URL> docsNoParseados = new LinkedList <URL>();
          //Lista de documentos parseados
          LinkedList <URL> docsParseados = new LinkedList <URL>();
          //Document Builder Factory
          XPath xpath = XPathFactory.newInstance().newXPath();
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilderFactory f2 = DocumentBuilderFactory.newInstance();
          DocumentBuilder docBuilder = null;
          DocumentBuilder docB2 = null;
          SchemaFactory xsdfactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
          Schema schema = null;
          //Comprobación de nuestro .xsd
          try {
              schema = xsdfactory.newSchema(mmlU);
          } catch(Exception e) {
          }
          //Asignamos nuestro .xsd como ejemplo para validar docs
          factory.setSchema(schema);
          //Gestión de excepciones de DocumentBuilder
          try {
              docBuilder = factory.newDocumentBuilder();
              docB2 = f2.newDocumentBuilder();
          } catch(Exception e) {
              e.printStackTrace();
          }
          String filepath = host + "/examen.xml";
          dc = docB2.parse(filepath);
          docBuilder.setErrorHandler(new XML_ErrorHandler(warnings,errors,fatalerrors));
          //Inicializamos nuestra URL
          URL init = null;
          //Le asignamos a init el contenido de xml2001 y gestionamos excepciones
          try {
             init = new URL(INIT_DOC);
          } catch (MalformedURLException e) {
          }
          //Creamos un objeto URLConnection
          URLConnection conexion = null;
          //Añadimos a nuestra lista de documentos a leer la URL init
          docsNoParseados.add(init);
	        String year = null;
          //Creamos un bucle que mientras no se haya vaciado la lista de documentos a leer se ejecuta
          while (!docsNoParseados.isEmpty()){
            //Creamos un objeto documento
            Document documento = null;
            //Lista de nodos con otros MML
            NodeList anotherMML = null;
            //Gestión de excepciones
            try {
                //Abrimos conexion para rellenar
                conexion = docsNoParseados.pop().openConnection();
                //Realizamos la lectura del documento
                documento = docBuilder.parse(conexion.getInputStream(), conexion.getURL().toString());
                //Añadimos la URL donde se encuentra el documento a la lista de documentos leídos
                docsParseados.add(conexion.getURL());
                //Rellenamos la variable String
                year = (String) xpath.evaluate("/Movies/Year/text()", documento, XPathConstants.STRING);
                anotherMML = (NodeList) xpath.evaluate("/Movies/Movie/Cast/MML", documento, XPathConstants.NODESET);
                docsValidos.put (year,documento);
            } catch (Exception e) {
                docsParseados.add(conexion.getURL());
                continue;
            }
          for (int i = 0; i < anotherMML.getLength(); i++) {
                  String mml = null;
                  try {
                      mml = (String) xpath.evaluate("text()", anotherMML.item(i), XPathConstants.STRING);
                  } catch(Exception e) {
                      continue;
                  }

                  URL newMML = null;
                  try {
                      newMML = new URL(conexion.getURL(), mml);
                  } catch(Exception e) {
                      e.printStackTrace();
                      continue;
                  }

                  if (!docsParseados.contains(newMML) && !docsNoParseados.contains(newMML)) {
                      docsNoParseados.add(newMML);
                  }
              }
          }
}


    public void doGetPhase01 (HttpServletRequest req, HttpServletResponse res) throws IOException{
          String pass = req.getParameter("p");
	        String auto = req.getParameter("auto");
          res.setCharacterEncoding("utf-8");
           PrintWriter a= res.getWriter();


          if ((req.getParameter("auto") == null) || (!req.getParameter("auto").equals("true")))  {
              FrontEnd.sendHTMLF01(res, pass);
              return;
          }
          else {
              FrontEnd.sendXMLF01(res);
              return;
          }
    }

    public void doGetPhase11 (HttpServletRequest req, HttpServletResponse res) throws IOException{
          String pass = req.getParameter("p");
          String auto = req.getParameter("auto");
          res.setCharacterEncoding("utf-8");
          PrintWriter a= res.getWriter();

          ArrayList<String> listYears = DataModel.getQ1Years();

          if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true")))  {
              FrontEnd.sendXMLF11(res, listYears);
              return;
          }
          else {
              FrontEnd.sendHTMLF11(res, pass,listYears);
              return;
            }
    }

    public void doGetPhase12 (HttpServletRequest req, HttpServletResponse res) throws IOException{
          String pass = req.getParameter("p");
          String year = req.getParameter("pyear");
	  String auto = req.getParameter("auto");

          res.setCharacterEncoding("utf-8");
          PrintWriter a= res.getWriter();


          ArrayList<Movie> listMovies = DataModel.getQ1Movies(year);

          if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true")))  {
            if (req.getParameter("pyear")== null) {
              FrontEnd.autoNoPyear(res);
              return;
            }
            else {
              FrontEnd.sendXMLF12(res,listMovies);
              return;
            }
          }
          else {
            if (req.getParameter("pyear")== null) {
              FrontEnd.noPyear(res);
              return;
            }
            else {
              FrontEnd.sendHTMLF12(res,year,pass, listMovies);
              return;
            }
          }
    }

    public void doGetPhase13 (HttpServletRequest req, HttpServletResponse res) throws IOException{
          String pass = req.getParameter("p");
          String movie = req.getParameter("pmovie");
          String year = req.getParameter("pyear");
	  String auto = req.getParameter("auto");
          res.setCharacterEncoding("utf-8");
          PrintWriter a= res.getWriter();

          ArrayList<Cast> listCasts = DataModel.getQ1Casts(year,movie);
          if ((req.getParameter("auto") != null) && (req.getParameter("auto").equals("true")))  {
            if (req.getParameter("pyear")== null) {
              FrontEnd.autoNoPyear(res);
              return;
            }
            else if (req.getParameter("pmovie")== null){
              FrontEnd.autoNoPmovie(res);
              return;
            }
            else {
              FrontEnd.sendXMLF13(res, listCasts);
              return;
            }

          }
          else {
            if (req.getParameter("pyear")== null) {
              FrontEnd.noPyear(res);
              return;
            }
            else if (req.getParameter("pmovie")== null){
              FrontEnd.noPmovie(res);
              return;
            }
            else {
              FrontEnd.sendHTMLF13(res,pass,year,movie, listCasts);
              return;
            }
          }
    }


  public static HashMap <String, Document> getValidosKeys(){
    return docsValidos;
  }

  public static Document getDocument(){
    return dc;
  }


}
