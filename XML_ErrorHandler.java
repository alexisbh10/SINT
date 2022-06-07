package p2;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.util.List;
import org.xml.sax.helpers.DefaultHandler;

public class XML_ErrorHandler extends DefaultHandler {

       private List<SAXParseException> warnings;
       private List<SAXParseException> errors;
       private List<SAXParseException> fatalerrors;

       public XML_ErrorHandler(List<SAXParseException> warnings, List<SAXParseException> errors,
               List<SAXParseException> fatalerrors) {
           super();
           this.warnings = warnings;
           this.errors = errors;
           this.fatalerrors = fatalerrors;
       }

       public void warning(SAXParseException warning) throws SAXException {
           warnings.add(warning);
           throw(warning);
       }

       public void error(SAXParseException error) throws SAXException {
           errors.add(error);
           throw(error);
       }

       public void fatalError(SAXParseException fatal) throws SAXException {
           fatalerrors.add(fatal);
           throw(fatal);
       }

       public List<SAXParseException> getWarnings() {
           return warnings;
       }

       public List<SAXParseException> getErrors() {
           return errors;
       }

       public List<SAXParseException> getfatalerrors() {
           return fatalerrors;
       }

   }
