package Model;

public class Data {

    String name ;
    String x ;
    String image ;
    String y;
    String info;
    String maxdistance;


    public Data()
    {

    }

    public Data(String name, String x,String y, String image, String info,String maxdistance) {
        this.name = name;
        this.x = x;
        this.image = image;
        this.y=y;
        this.info=info;
        this.maxdistance = maxdistance;

    }

    public String getName() {
        return name;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getImage() {
        return image;
    }

    public String getInfo() { return info; }

    public String getMaxdistance() {
        return maxdistance;
    }

    public void setName(String name) { this.name = name; }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setMaxdistance(String maxdistance) {
        this.maxdistance = maxdistance;
    }

}
