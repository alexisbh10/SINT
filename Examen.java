package p2;

public class Examen {

    String cont, id, comentario, genero;

    public Examen (String c, String i, String cm, String g){
        cont=c;
        id=i;
        comentario=cm;
        genero=g;
    }

    public Examen (){
    }

    public String getCont(){
      return cont;
    }

    public String getID(){
      return id;
    }

    public String getComentario(){
      return comentario;
    }

    public String getGenero(){
      return genero;
    }

    public void setCont(String c){
        this.cont=c;
    }

    public void setID(String i){
        this.id=i;
    }

    public void setComentario(String cm){
        this.comentario=cm;
    }

    public void setGenero(String g){
        this.genero=g;
    }

    public String toString(){
        return "Examen= " + cont + ", " + id + "," + comentario + ", " + genero;
    }

}
