import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Button {

    String text;

    int x, y;

    int buttonWidth, buttonHeight;

    Color textColor;
    Button(int x, int y, String text, int width, int height)
    {
        this.x = x;
        this.y =y;
        this.text = text;

        this.buttonWidth = width;
        this.buttonHeight = height;


        // default color of each button
        this.textColor = Constants.BUTTON_COLOR;
    }
    
    public void draw(Graphics2D gContext)
    {
        int textWidth, textHeight;

        gContext.setColor(textColor);
        gContext.setFont(Constants.BUTTON_FONT);

        textWidth = (int)Math.ceil(gContext.getFontMetrics().getStringBounds(text, gContext).getWidth());
        textHeight = (int)Math.ceil(gContext.getFontMetrics().getStringBounds(text, gContext).getHeight());

        if (textWidth > buttonWidth || textHeight > buttonHeight)
        {



        }

        // upper boundary
        gContext.fillRect(x, y,Constants.BUTTON_WIDTH, Constants.BUTTON_BOUNDARY_THICKNESS );
        // left boundary
        gContext.fillRect(x, y,Constants.BUTTON_BOUNDARY_THICKNESS,Constants.BUTTON_HEIGHT );
        //lower boundary
        gContext.fillRect(x, y+(Constants.BUTTON_HEIGHT - Constants.BUTTON_BOUNDARY_THICKNESS),Constants.BUTTON_WIDTH, Constants.BUTTON_BOUNDARY_THICKNESS );
        //right boundary
        gContext.fillRect(x+(Constants.BUTTON_WIDTH- Constants.BUTTON_BOUNDARY_THICKNESS), y,Constants.BUTTON_BOUNDARY_THICKNESS,Constants.BUTTON_HEIGHT );

        gContext.drawString(text, x + 60, y+ 60);



    }

}
