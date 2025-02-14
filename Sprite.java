import java.awt.Graphics;
import java.awt.image.BufferedImage;

interface Displayable{
    public void draw(Graphics g);
}

public class Sprite implements Displayable{
    private BufferedImage image;
    private double x;
    private double y;
    private double width;
    private double height;

    public Sprite(BufferedImage imageIn, double xIn, double yIn, double widthIn, double heightIn){
        this.image = imageIn;
        this.x = xIn;
        this.y = yIn;
        this.width = widthIn;
        this.height = heightIn;
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(this.image,(int)this.x,(int)this.y,null);
    }

    //getters
    public BufferedImage getImage(){
        return this.image;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    //setters
    public void setX(double newX){
        this.x = newX;
    }
    public void setY(double newY){
        this.y = newY;
    }
}