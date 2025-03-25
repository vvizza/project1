import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main extends JFrame{
    JFrame displayZoneFrame;
    RenderEngine renderEngine;
    PhysicEngine physicEngine;
    GameEngine gameEngine;
    PlayGround playGround;

    public Main() throws Exception{
        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = DynamicSprite.getInstance(ImageIO.read(new File("assets/img/heroTileSheetLowRes.png")),250,350,48,50);

        renderEngine = new RenderEngine();
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero);
        playGround = new PlayGround("assets/level/level1.txt");

        Timer renderTimer = new Timer(50,(time)-> renderEngine.update());
        Timer gameTimer = new Timer(50,(time)-> gameEngine.update());
        Timer physicTimer = new Timer(50,(time)-> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);
        displayZoneFrame.addKeyListener(gameEngine);

        //display grass
        ArrayList<Displayable> backgroundSprites = playGround.getSpriteList();
        for(Displayable s : backgroundSprites){
            renderEngine.addToRenderList(s);
        }

        //display solid sprites (rocks + trees)
        ArrayList<Sprite> solidSprites = playGround.getSolidSpriteList();
        for(Sprite s : solidSprites){
            renderEngine.addToRenderList(s);
        }
        
        physicEngine.setEnvironment(solidSprites);
        renderEngine.addToRenderList(hero);
        physicEngine.addToMovingSpriteList(hero);
    }

    public static void main(String[] args) throws Exception{
        Main main = new Main();
    }
}
