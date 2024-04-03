import javax.swing.*;
import java.awt.*;


enum Display
{
    GAME_SCREEN, HOME_SCREEN, WIN_SCREEN, LEVEL_SCREEN;
}

public class Window extends JFrame implements Runnable {

    boolean keepRunning = true;
    Display currentDisplay = Display.HOME_SCREEN;
    KL keyListener = new KL();
    Graphics2D graphicsBoard;
    ML mouseListener = new ML();

    Button homeScreenPlayButton;

    Button[] levelButtons = new Button[3];

    Button[] winScreenButtons = new Button[2];

    Window()
    {
        this.setVisible(true);
        this.setResizable(false);

        Constants.UPPER_BAR_HEIGHT = this.getInsets().top;
        Constants.BOTTOM_BAR_HEIGHT = this.getInsets().bottom;
        Constants.LEFT_BAR_HEIGHT = this.getInsets().left;
        Constants.RIGHT_BAR_HEIGHT = this.getInsets().right;

        this.setSize(Constants.BOARD_SIDE +Constants.LEFT_BAR_HEIGHT+Constants.RIGHT_BAR_HEIGHT,Constants.BOARD_SIDE +Constants.UPPER_BAR_HEIGHT+Constants.BOTTOM_BAR_HEIGHT);

        // center the Screen
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(Constants.DEFAULT_TITLE);

        this.addKeyListener(keyListener);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);

        keyListener.parentWindow = this;
        mouseListener.parentWindow = this;

        graphicsBoard = (Graphics2D) this.getGraphics();

        homeScreenPlayButton = new Button(180,300,"Play",Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);

        levelButtons[0] = new Button(180, 120, "Easy", Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        levelButtons[1] = new Button(180, 120+150, "Luck", Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        levelButtons[2] = new Button(180, 120+300, "Hard",Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);

        winScreenButtons[0] = new Button(180, 120, "Play Again",Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        winScreenButtons[1] = new Button(180, 120, "Exit Game",Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);


    }

    private void update()
    {
        Image backBuffer =  createImage(Constants.BOARD_SIDE,Constants.BOARD_SIDE);
        Graphics2D backBufferContext = (Graphics2D)backBuffer.getGraphics();


        if (currentDisplay == Display.HOME_SCREEN)
        {
            backBufferContext.setColor(Constants.HOME_SCREEN_BACKGROUND);
            backBufferContext.fillRect(0,0,Constants.BOARD_SIDE, Constants.BOARD_SIDE);
            backBufferContext.setColor(Constants.FIXED_DIGIT_COLOR);
            backBufferContext.setFont(Constants.HOME_SCREEN_TITLE_FONT);
            backBufferContext.drawString("SUDOKU",70,150);
            homeScreenPlayButton.draw(backBufferContext);
        }
        else if (currentDisplay == Display.LEVEL_SCREEN)
        {
            backBufferContext.setColor(Constants.HOME_SCREEN_BACKGROUND);
            backBufferContext.fillRect(0,0,Constants.BOARD_SIDE, Constants.BOARD_SIDE);
            backBufferContext.setColor(Constants.FIXED_DIGIT_COLOR);
            backBufferContext.setFont(Constants.MENU_SCREEN_TITLE_FONT);
            backBufferContext.drawString("Select Difficulty",155,80);

            for (Button button : levelButtons)
            {
                button.draw(backBufferContext);
            }
        }
        else if (currentDisplay == Display.GAME_SCREEN)
        {
            Board.getBoard().draw(backBufferContext);
        }
        else if (currentDisplay == Display.WIN_SCREEN)
        {



        }

        graphicsBoard.drawImage(backBuffer, Constants.LEFT_BAR_HEIGHT, Constants.UPPER_BAR_HEIGHT, this);
    }

    @Override
    public void run()
    {

        this.setLocationRelativeTo(null);

       while (keepRunning)
       {
           update();
       }
    }


}
