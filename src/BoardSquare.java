import java.awt.*;
import java.util.ArrayList;

public class BoardSquare {

    // flag is set when square is selected
    private  boolean selected = false;

    // flag is set when a peer of this square is selected
    private  boolean highlighted = false;

    // flag is set when square contains a false value and board is complete.
    private boolean inFalseUnit = false;
    private final int positionX;
    private final int positionY;

    // labels of peers, not the peers themselves, because we have a dictionary mapping labels to each square.
    ArrayList<String> peerList = new ArrayList<>(20);

    private final String label;
    private char value;

    private final boolean fixed;

    BoardSquare(int x, int y, String label, char value)
    {
        this.positionX = x;
        this.positionY = y;
        this.label = label;
        this.value = value;
        this.fixed = (value != '0');

        peerList = BoardSolver.peerMap.get(label);

    }

    public boolean isFixed() {
        return fixed;
    }
    public void setSelected(boolean bool)
    {
        this.selected = bool;
    }

    public void setHighlighted(boolean bool)
    {
        this.highlighted = bool;
    }
    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }


    public int getPositionX() {
        return positionX;
    }



    public int getPositionY() {
        return positionY;
    }


    public String getLabel() {
        return label;
    }

    public boolean isEmpty()
    {
        return value == '0';
    }

    public void setInFalseUnit(boolean inFalseUnit) {
        this.inFalseUnit = inFalseUnit;
    }

    public void draw(Graphics2D gContext)
    {

        if (inFalseUnit){gContext.setColor(Constants.FALSE_VALUE_COLOR);}
        else if (selected){gContext.setColor(Constants.SELECTION_COLOR);}
        else if(highlighted){gContext.setColor(Constants.HIGHLIGHT_COLOR);}
        else {gContext.setColor(Constants.SQUARE_COLOR);}
        gContext.fillRect(positionX,positionY, Constants.SQUARE_SIDE,Constants.SQUARE_SIDE );

        if (isFixed())
        {
            gContext.setColor(Constants.FIXED_DIGIT_COLOR);
            gContext.setFont(Constants.FIXED_DIGIT_FONT);
            gContext.drawString(String.valueOf(value), positionX+20, positionY+45);
        }
        else if (isEmpty()) {
        }
        else
        {
            gContext.setColor(Constants.USER_DIGIT_COLOR);
            gContext.setFont(Constants.USER_DIGIT_FONT);
            gContext.drawString(String.valueOf(value), positionX+20, positionY+45);
        }

    }


}
