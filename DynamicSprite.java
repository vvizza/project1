import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class DynamicSprite extends SolidSprite{
    private boolean isWalking;
    private double speed;
    private int spriteSheetNumberOfColumn;
    private int timeBetweenFrame;
    private Direction direction;

    private static volatile DynamicSprite instance = null;
    
    private DynamicSprite(BufferedImage ImageIn, double xIn, double yIn, double widthIn, double heightIn){
        super(ImageIn, xIn, yIn, widthIn, heightIn);
        this.isWalking = true;
        this.speed = 5;
        this.spriteSheetNumberOfColumn = 10;
        this.timeBetweenFrame = 200;
        this.direction = Direction.NORTH;
    }

    public final static DynamicSprite getInstance(BufferedImage ImageIn, double xIn, double yIn, double widthIn, double heightIn) {
        if (DynamicSprite.instance == null) {
            synchronized(DynamicSprite.class) {
                if (DynamicSprite.instance == null) {
                    DynamicSprite.instance = new DynamicSprite(ImageIn, xIn, yIn, widthIn, heightIn);
                }
            }
        }
        return DynamicSprite.instance;
    }

    public void setDirection(Direction directionIn){
        this.direction = directionIn;
    }

    @Override
    public void draw(Graphics g){
        int index = (int) (System.currentTimeMillis() / this.timeBetweenFrame) % this.spriteSheetNumberOfColumn;
        int attitude = this.direction.getFrameLineNumber();

        int frameWidth = (int) this.getWidth();
        int frameHeight = (int) this.getHeight();

        BufferedImage frame = this.getImage().getSubimage(index * frameWidth, attitude * frameHeight, frameWidth, frameHeight);
        g.drawImage(frame,(int)this.getX(),(int) this.getY(),null);
    }

    private void move(){
        double oldX = this.getX();
        double oldY = this.getY();
        
        switch (direction){
            case NORTH -> {
                this.setY(oldY - speed);
            }
            case SOUTH -> {
                this.setY(oldY + speed);
            }
            case EAST -> {
                this.setX(oldX + speed);
            }
            case WEST -> {
                this.setX(oldX - speed);
            }
        }
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        double tempX = this.getX();
        double tempY = this.getY();
        switch (direction) {
            case NORTH -> {
                tempY = this.getY() - speed;
            }
            case SOUTH -> {
                tempY = this.getY() + speed;
            }
            case EAST -> {
                tempX = this.getX() + speed;
            }
            case WEST -> {
                tempX = this.getX() - speed;
            }
        }

        Rectangle2D hitBox = new Rectangle2D.Double(tempX, tempY, this.getWidth(), this.getHeight());

        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite solid && sprite != this) {
                if (hitBox.intersects(solid.getX(), solid.getY(), solid.getWidth(), solid.getHeight())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite>environment){
        if(isMovingPossible(environment)){
            move();
        }
    }
}