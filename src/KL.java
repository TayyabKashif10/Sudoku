import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

 class KL implements KeyListener {

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

     void parseKeyClick(KeyEvent e)
    {
        if (parentWindow.currentDisplay == Display.GAME_SCREEN){
            if (Board.getBoard() != null)
            {
                Board.getBoard().updateSquareValue(e.getKeyChar());

                // after updating a value, if the board state gets changed to SOLVED, switch screens
                if (Board.getBoard().boardState == BoardState.SOLVED)
                {
                    parentWindow.currentDisplay = Display.WIN_SCREEN;
                }
            }
        }

    }
}
