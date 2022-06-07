package p2;

public class Cast implements Comparable <Cast>{

    private String name, role, contact, id;

    public Cast(){
    }

    public Cast (String n, String r, String c, String i){
        name=n;
        role=r;
        contact=c;
        id=i;
    }

    public String getName(){
        return name;
    }

    public String getRole(){
        return role;
    }

    public String getContact(){
        return contact;
    }

    public String getID(){
        return id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setRole(String role){
        this.role=role;
    }

    public void setContact (String contact){
        this.contact=contact;
    }

    public void setID (String id){
        this.id=id;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Cast) {
            return  name.equals(((Cast)o).name);
        }
        return false;
    }

    @Override
    public int compareTo(Cast c) {
        if (role.equals(c.role)) {
            return id.compareTo(c.id);
        } else {
            return new String (c.role).compareTo(new String(role));
        }
    }
}
