import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;


interface Engine{
    public void update();
}

public class RenderEngine extends JPanel implements Engine{
    private ArrayList<Displayable> renderList;

    public RenderEngine(){
        this.renderList = new ArrayList<>();
    }

    public void setrenderList(ArrayList<Displayable> newRenderList){
        this.renderList = newRenderList;
    }

    public void addToRenderList(Displayable displayable){
        this.renderList.add(displayable);
    }

    @Override
    public void update(){
        repaint();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        for(Displayable d : renderList){
            d.draw(g);
        }
    }
}
