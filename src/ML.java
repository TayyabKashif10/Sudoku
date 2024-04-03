import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

 class ML extends MouseAdapter
{
    Window parentWindow;

    /*
    *  NOTE: If mouse is DRAGGED
    *  i.e if its clicked but moved during the click, it wont be registered as a mouse click event, and the squares wont get highlighted.
    *  thats why some clicks can go unregistered while clicking
    * if mouse is moved even slightly during the click, it wont register
    *
    * thats also why this issue never arises in touchpad, you cant drag normally in touchpad
    *
    * i have fixed this by making it so when a mouse is dragged, it calls the mouse click function as well
    *
    * */

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mouseClicked(e);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        parseMouseClick(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        parseMouseMovement(e);
    }

     void parseMouseClick(MouseEvent e)
    {
        int x = e.getX() - Constants.LEFT_BAR_HEIGHT;
        int y = e.getY() - Constants.UPPER_BAR_HEIGHT;

        if (parentWindow.currentDisplay == Display.GAME_SCREEN)
        {
            if (Board.getBoard() != null)
            {
                Board.getBoard().selectSquare(x,y);
            }
        }
        else if (parentWindow.currentDisplay == Display.HOME_SCREEN)
        {
            if (x >= parentWindow.homeScreenPlayButton.x && x <= parentWindow.homeScreenPlayButton.x+Constants.BUTTON_WIDTH &&
                    y >= parentWindow.homeScreenPlayButton.y && y <= parentWindow.homeScreenPlayButton.y+Constants.BUTTON_HEIGHT
            )
            {
                parentWindow.currentDisplay = Display.DIFFICULTY_SCREEN;

                // reset button color.
                parentWindow.homeScreenPlayButton.textColor = Constants.BUTTON_COLOR;
            }
        }
        else if (parentWindow.currentDisplay == Display.DIFFICULTY_SCREEN)
        {
            for (Button button : parentWindow.difficultyButtons)
            {

                if (x >= button.x && x <= button.x+Constants.BUTTON_WIDTH &&
                        y >= button.y && y <= button.y+Constants.BUTTON_HEIGHT
                )
                {
                    Difficulty difficulty;
                    if (Objects.equals(button.text, "Easy"))
                    {
                        difficulty = Difficulty.EASY;
                    } else if (Objects.equals(button.text, "Luck")) {
                        difficulty = Difficulty.RANDOM;

                    }
                    else
                    {
                        assert Objects.equals(button.text, "Hard");
                        difficulty = Difficulty.HARD;
                    }

                    /* instantiate board with selected difficulty
                     note that board is only used in the program AFTER passing through this block.
                    */
                    Board.createBoard(difficulty);

                    parentWindow.currentDisplay = Display.GAME_SCREEN;

                    // reset button color
                    button.textColor = Constants.BUTTON_COLOR;
                }

            }
        }
        else if (parentWindow.currentDisplay == Display.WIN_SCREEN)
        {
            for (Button button : parentWindow.winScreenButtons)
            {

                if (x >= button.x && x <= button.x+Constants.BUTTON_WIDTH &&
                        y >= button.y && y <= button.y+Constants.BUTTON_HEIGHT
                )
                {

                    if (Objects.equals(button.text, "Exit Game"))
                    {
                        parentWindow.keepRunning = false;

                    } else
                    {
                        assert (Objects.equals(button.text, "Play Again"));

                        // destroy previous board to allow creation of new board
                        Board.destroyBoard();
                        parentWindow.currentDisplay = Display.DIFFICULTY_SCREEN;
                    }
                                    }

            }
        }
    }

     void parseMouseMovement(MouseEvent e)
    {
        int x = e.getX() - Constants.LEFT_BAR_HEIGHT;
        int y = e.getY() - Constants.UPPER_BAR_HEIGHT;

        if (parentWindow.currentDisplay == Display.HOME_SCREEN)
        {
            if (x >= parentWindow.homeScreenPlayButton.x && x <= parentWindow.homeScreenPlayButton.x+Constants.BUTTON_WIDTH &&
                    y >= parentWindow.homeScreenPlayButton.y && y <= parentWindow.homeScreenPlayButton.y+Constants.BUTTON_HEIGHT
            )
            {
                parentWindow.homeScreenPlayButton.textColor = Constants.ACTIVE_BUTTON_COLOR;
            }
            else
            {
                parentWindow.homeScreenPlayButton.textColor = Constants.BUTTON_COLOR;
            }
        }
        else if (parentWindow.currentDisplay == Display.DIFFICULTY_SCREEN)
        {

            for (Button button : parentWindow.difficultyButtons)
            {

                if (x >= button.x && x <= button.x+Constants.BUTTON_WIDTH &&
                        y >= button.y && y <= button.y+Constants.BUTTON_HEIGHT
                )
                {
                    button.textColor = Constants.ACTIVE_BUTTON_COLOR;
                }
                else
                {
                    button.textColor = Constants.BUTTON_COLOR;
                }
            }
        }
        else if (parentWindow.currentDisplay == Display.WIN_SCREEN)
        {
            for (Button button : parentWindow.winScreenButtons)
            {

                if (x >= button.x && x <= button.x+Constants.BUTTON_WIDTH &&
                        y >= button.y && y <= button.y+Constants.BUTTON_HEIGHT
                )
                {
                    button.textColor = Constants.ACTIVE_BUTTON_COLOR;
                }
                else
                {
                    button.textColor = Constants.BUTTON_COLOR;
                }
            }

        }

    }







}
