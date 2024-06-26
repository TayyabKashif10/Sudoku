import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

enum BoardState
{
    INCOMPLETE, INCORRECT, SOLVED
}

enum Difficulty
{
    EASY, RANDOM, HARD
}

 class Board {

    BoardState boardState = BoardState.INCOMPLETE;

    String solution;

    //only one instance of Board is allowed at a time.
    // created to allow access to the Board across all classes.
    private static Board currentBoard;
    private BoardSquare selected;

    // the first square that is non fixed on the board, used by the moveSelection function.
    BoardSquare firstNonFixed;
    Map<String, BoardSquare> squareMap = new HashMap<>(81);


    static FileParser easyFile;

     static {
         try {
             easyFile = new FileParser("puzzles/easy.txt");
         } catch (FileNotFoundException e) {
             JOptionPane.showMessageDialog(new JFrame(), "puzzles/easy.txt not found\nDefault sudoku will be used for Easy Mode.", "Dialog",
                     JOptionPane.ERROR_MESSAGE);
         }
     }

     static FileParser randomFile;

     static {
         try {
             randomFile = new FileParser("puzzles/random.txt");
         } catch (FileNotFoundException e) {
             JOptionPane.showMessageDialog(new JFrame(), "puzzles/random.txt not found\nDefault sudoku will be used for Random Mode.", "Dialog",
                     JOptionPane.ERROR_MESSAGE);
         }
     }

     static FileParser hardFile;

     static {
         try {
             hardFile = new FileParser("puzzles/hard.txt");
         } catch (FileNotFoundException e) {
             JOptionPane.showMessageDialog(new JFrame(), "puzzles/hard.txt not found\nDefault sudoku will be used for Hard Mode.", "Dialog",
                     JOptionPane.ERROR_MESSAGE);
         }
     }

     private Board(Difficulty difficulty)
    {
        String grid = generateBoard(difficulty);
        solution = BoardSolver.solve(grid);

        int posX, posY;
        BoardSquare newSquare;

        // where (i,j) represents the index of the square on the 9 by 9 2D array
        // listIndex represents the index of the square in a 81 element list.
        int listIndex;
        char value;

        for (int i = 0 ; i < 9 ; i++)
        {
            for (int j =0 ; j < 9 ; j++)
            {
                listIndex = 9*i+j;
                value = grid.charAt(listIndex);

                //each square has i+1 borders and i squares behind it and above it.
                posX = Constants.SQUARE_SIDE*j + Constants.BORDER_THICKNESS*(j+1);
                posY = Constants.SQUARE_SIDE*i + Constants.BORDER_THICKNESS*(i+1);

                newSquare = new BoardSquare(posX, posY, BoardSolver.labels.get(listIndex), value);

                squareMap.put(BoardSolver.labels.get(listIndex),newSquare);

            }
        }
    }


     static String generateBoard(Difficulty difficulty)
    {
        String boardGrid = null;

        if (difficulty == Difficulty.EASY)
        {
            if (easyFile != null) boardGrid = easyFile.getRandomLine(81);
        }
        else if (difficulty == Difficulty.RANDOM)
        {
            if (randomFile != null) boardGrid =  randomFile.getRandomLine(81);
        }
        else
        {
            assert difficulty == Difficulty.HARD;

            if (hardFile != null) boardGrid =  hardFile.getRandomLine(81);
        }

        return Objects.requireNonNullElse(boardGrid, Constants.DEFAULT_PUZZLE);
    }

     void identifyFalseUnits()
    {
        // clear selection square and reset colors.
        setCurrentSelectedSquare(null);

        String currentGrid = toString();

        String falseSquare;


        for (int i = 0 ; i < BoardSolver.labels.toArray().length ; i++)
        {

            if (currentGrid.charAt(i) != solution.charAt(i))
            {
                falseSquare = BoardSolver.labels.get(i);

                for (ArrayList<String> unit : BoardSolver.unitMap.get(falseSquare))
                {
                     markFalseUnit(unit);
                }
            }
        }

    }

    // set the inFalseUnit flag of squares inside a false unit to be true.
     void markFalseUnit(ArrayList<String> unit)
    {
        for (String squareLabel : unit)
        {
            squareMap.get(squareLabel).setInFalseUnit(true);
        }

    }

     void updateState()
    {
        if (filledSquares() != 81)
        {
            boardState = BoardState.INCOMPLETE;
        }
        else
        {
            if (toString().equals(solution))
            {
                boardState = BoardState.SOLVED;

            }
            else
            {
                boardState = BoardState.INCORRECT;
                identifyFalseUnits();
            }
        }


    }

     int filledSquares()
    {
        int filledSquares = 0;
        for (String squareLabel : BoardSolver.labels)
        {
            if (!squareMap.get(squareLabel).isEmpty())
            {
                filledSquares++;
            }
        }

        return filledSquares;
    }

    @Override
     public String toString() {

        StringBuilder stripRep = new StringBuilder(81);

        for (String label : BoardSolver.labels)
        {
           stripRep.append(squareMap.get(label).getValue());
        }

        return stripRep.toString();
    }

     static Board createBoard(Difficulty difficulty)
    {
        if (currentBoard == null)
        {
            currentBoard = new Board(difficulty);
            return currentBoard;
        }
        else
        {
            throw new RuntimeException("An instance of Board already exists.");
        }
    }

     static void destroyBoard()
    {
        currentBoard = null;

    }

     static Board getBoard()
    {
        return currentBoard;
    }


     void setCurrentSelectedSquare(BoardSquare newSelection)
    {
        selected = newSelection;
        highlightSquares();
    }


    // select square from a mouse click
    // the first square whose bottom right corner is below and to the right of the cursor gets selected
     void selectSquare(int x, int y)
    {
        BoardSquare square;
        for (String label : BoardSolver.labels)
        {
            square = squareMap.get(label);
            if (square.getPositionX()+Constants.SQUARE_SIDE > x && square.getPositionY()+Constants.SQUARE_SIDE > y)
            {
                if (square.isFixed()) {return;}
                else {
                    setCurrentSelectedSquare(square);

                    return;
                    }

            }

        }
    }

     void highlightSquares()
    {
        resetSquareColors();

        if (selected == null){return;}

        selected.setSelected(true);

        for (String peer : selected.peerList)
        {
            squareMap.get(peer).setHighlighted(true);
        }

    }

     void resetSquareColors()
    {
        for (String label : BoardSolver.labels)
        {
            squareMap.get(label).setHighlighted(false);
            squareMap.get(label).setSelected(false);
            squareMap.get(label).setInFalseUnit(false);
        }
    }

     void updateSquareValue(char keyChar)
    {
        if (selected == null) {return;}
        if (keyChar == '1' ||
                keyChar == '2'||
                keyChar == '3'||
                keyChar == '4'||
                keyChar == '5'||
                keyChar == '6'||
                keyChar == '7'||
                keyChar == '8'||
                keyChar == '9')
        {
            selected.setValue(keyChar);
        }
        // mark as empty if backspace is clicked.
        else if (keyChar == '\b')
        {
            selected.setValue('0');
        }

        // update game state whenever game changing change is made: i.e a value is changed.
        updateState();
    }

     void moveSelection(int keyCode) {
        // if no selection exists, select first non-fixed square.
        if (selected == null) {
            if (firstNonFixed == null) {

                // this will only run ONCE per board, after which the non fixed square will be saved and used directly.
                for (String square : BoardSolver.labels) {
                    if (!squareMap.get(square).isFixed()) {
                        firstNonFixed = squareMap.get(square);
                        break;
                    }
                }
            }
            setCurrentSelectedSquare(firstNonFixed);
            return;
        }

        // MATRIX coordinates of square. [from element (0,0) to (8,8)]
        int x = selected.getLabel().charAt(0) - 'A', y = selected.getLabel().charAt(1) - '1';

        String newSelection;

        // keep moving the selection square in specified direction until the landed square is not fixed (and thus is selectable)

        do {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:

                    y--;

                    if (y == -1) {
                        y = 8;
                    }

                    break;
                case KeyEvent.VK_RIGHT:

                    y++;

                    if (y == 9) {
                        y = 0;
                    }

                    break;
                case KeyEvent.VK_UP:
                    x--;

                    if (x == -1) {
                        x = 8;
                    }

                    break;
                case KeyEvent.VK_DOWN:

                    x++;

                    if (x == 9) {
                        x = 0;
                    }

                    break;
            }

            newSelection = "" + (char) ('A' + x) + (char) ('1' + y);

        } while (squareMap.get(newSelection).isFixed());

        setCurrentSelectedSquare(squareMap.get(newSelection));

    }
     void draw(Graphics2D gContext)
    {
        //draw borders
        for (int i = 0 ; i < 10 ; i++)
        {
            gContext.setColor(Constants.BORDER_COLOR);
            gContext.fillRect(i*Constants.BORDER_THICKNESS+i*Constants.SQUARE_SIDE,0,Constants.BORDER_THICKNESS,Constants.BOARD_SIDE);
            gContext.fillRect(0,i*Constants.BORDER_THICKNESS+i*Constants.SQUARE_SIDE,Constants.BOARD_SIDE,Constants.BORDER_THICKNESS);
        }

        //draw squares
        for (String label: BoardSolver.labels)
        {
            squareMap.get(label).draw(gContext);
        }

    }





}
