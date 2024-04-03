import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL implements KeyListener {

    Window parentWindow;

    @Override
    public void keyTyped(KeyEvent e) {
        parseKeyClick(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // move selection square, only relevant in the game screen
        if (parentWindow.currentDisplay == Display.GAME_SCREEN){

            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                if (Board.getBoard() != null)
                {
                    Board.getBoard().moveSelection(e.getKeyCode());
                }
            }
            else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                Board.getBoard().updateSquareValue('\b');
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void parseKeyClick(KeyEvent e)
    {
        if (parentWindow.currentDisplay == Display.GAME_SCREEN){
            if (Board.getBoard() != null)
            {
                Board.getBoard().updateSquareValue(e.getKeyChar());
            }
        }

    }
}
