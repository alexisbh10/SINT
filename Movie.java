


package p2;

import java.util.ArrayList;

public class Movie implements Comparable<Movie>{
    private String title, synopsis, lang;
    private ArrayList <String> genre;
    private String duration;

    public Movie(){
    }

    public Movie (String t, String d, String s, ArrayList <String> g, String l){
        title=t;
        duration=d;
        synopsis=s;
        genre=g;;
        lang=l;
    }

    public String getTitle(){
        return title;
    }
    public String getDuration(){
        return duration;
    }
    public String getSynopsis(){
        return synopsis;
    }

    public ArrayList <String> getGenre(){
        return genre;
    }

    public String getLang(){
        return lang;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setSynopsis(String synopsis){
        this.synopsis=synopsis;
    }

    public void setGenre(ArrayList <String> genre){
        this.genre=genre;
    }

    public void setLang(String lang){
        this.lang=lang;
    }

    public void setDuration (String d){
        this.duration=d;
    }

    public boolean equals(Object another) {
         if (another == null) return false;
         if (another instanceof Movie) {
             return  title.equals(((Movie) another).title);
         }
         return false;
     }


 @Override
     public int compareTo(Movie m) {
          if (m.genre.size()<(genre.size())){
              return -1;
          }
          else if (m.genre.size()>(genre.size())){
              return 1;
          }
          else {
             return title.compareTo(m.title);
          }
     }
}

