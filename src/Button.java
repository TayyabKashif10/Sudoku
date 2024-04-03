import java.awt.*;
import java.awt.font.TextLayout;

 class Button {

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
    
     void draw(Graphics2D gContext)
    {
        /* FOR PERSONAL UNDERSTANDING

        * Some context to understand everything that is happening in this code to centralize the text
        * the drawString() method takes coordinates x,y to draw an image.
        * (x,y) represent the coordinates of the BASELINE of your text.
        * textHeight represents the distance from the ascent line to the descent line.
        * so the difference between the ascent line and baseline is less than the textHeight.
        * so first we calculate the distance from the top of the button to the required ascent line as topPadding.
        * when we do x+topPadding, it gives the location of where we want to place the ascent line of our text.
        * we then add textHeight to this, which gives us the location of the descent line.
        * we use this location to draw the string, so its placing the baseline AT the required descent line.
        * thats why our strings ascent line does not perfectly align with the required y-position for it.
        *

        * help: https://docs.oracle.com/javase/8/docs/api/java/awt/FontMetrics.html  (see class notes)
        * help: https://stackoverflow.com/questions/27631736/meaning-of-top-ascent-baseline-descent-bottom-and-leading-in-androids-font
        *
        * */

        int textWidth, textHeight;

        // construct a text layout object (which gives us the visual bounds/dimensions (approximate, see documentation) of the text)
        TextLayout textLayout =  new TextLayout(text, Constants.BUTTON_FONT, gContext.getFontRenderContext());

        gContext.setColor(textColor);

        // upper boundary
        gContext.fillRect(x, y,buttonWidth,  Constants.BUTTON_BOUNDARY_THICKNESS );
        // left boundary
        gContext.fillRect(x, y,Constants.BUTTON_BOUNDARY_THICKNESS,buttonHeight );
        //lower boundary
        gContext.fillRect(x, y+(buttonHeight - Constants.BUTTON_BOUNDARY_THICKNESS),buttonWidth, Constants.BUTTON_BOUNDARY_THICKNESS );
        //right boundary
        gContext.fillRect(x+(buttonWidth- Constants.BUTTON_BOUNDARY_THICKNESS), y,Constants.BUTTON_BOUNDARY_THICKNESS,buttonHeight );

        // drawing text centered inside the button.
        gContext.setFont(Constants.BUTTON_FONT);

        textWidth = (int)textLayout.getBounds().getWidth();
        textHeight = (int)textLayout.getBounds().getHeight();

        if (textWidth > buttonWidth-2*Constants.BUTTON_BOUNDARY_THICKNESS || textHeight > buttonHeight - 2*Constants.BUTTON_BOUNDARY_THICKNESS)
        {
            throw  new RuntimeException("Text cannot fit inside Button");
        }

        int textPaddingLeft = (buttonWidth-2*Constants.BUTTON_BOUNDARY_THICKNESS - textWidth)/2;
        int textPaddingTop = (buttonHeight-2*Constants.BUTTON_BOUNDARY_THICKNESS - textHeight)/2;

        // the vertical position of the text that the graphics will draw is the position of the baseline, not the ascent line
        gContext.drawString(text, x + Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingLeft, y+ Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingTop + textHeight );


        /* To help visualize the boundaries of the text, uncomment and render the text to see
        gContext.setColor(Color.BLUE);
        gContext.drawLine(x,y+ Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingTop+textHeight, x+200, y+ Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingTop+textHeight);
        gContext.drawLine(x,y+ Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingTop, x+200, y+ Constants.BUTTON_BOUNDARY_THICKNESS + textPaddingTop);
        */
    }


}
