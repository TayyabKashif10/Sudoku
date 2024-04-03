import java.awt.*;

public class Constants {

    public final static String DEFAULT_TITLE = "Sudoku";
    public static final Color HIGHLIGHT_COLOR = new Color(220, 175, 159);
    public static final Color FIXED_DIGIT_COLOR = new Color(246,224,181);
    public static final Color USER_DIGIT_COLOR = new Color(151,153,186);

    public static final Font FIXED_DIGIT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

    public static final Font USER_DIGIT_FONT = FIXED_DIGIT_FONT;
    public static final Color SELECTION_COLOR = new Color(170,111,115);
    public static final Color FALSE_VALUE_COLOR = new Color(203, 85, 85);
    public static int UPPER_BAR_HEIGHT;
    public static int BOTTOM_BAR_HEIGHT;

    public static int LEFT_BAR_HEIGHT;
    public static int RIGHT_BAR_HEIGHT;

    public final static int SQUARE_SIDE = 60;
    public final static int BORDER_THICKNESS = 3;

    public final static int BOARD_SIDE = SQUARE_SIDE*9 + BORDER_THICKNESS*10;

    public final static Color SQUARE_COLOR = new Color(82, 68, 75);
    public final static Color BORDER_COLOR = Color.gray;


    public static final Color HOME_SCREEN_BACKGROUND = SQUARE_COLOR;
    public static final Font HOME_SCREEN_TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 100);

    public static final Font MENU_SCREEN_TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

    public static final Color BUTTON_COLOR = USER_DIGIT_COLOR;
    public static final Color ACTIVE_BUTTON_COLOR = FIXED_DIGIT_COLOR;
    public static final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

    public static final int BUTTON_HEIGHT = 100;
    public static final int BUTTON_WIDTH = 200;

    public static final int BUTTON_BOUNDARY_THICKNESS = BORDER_THICKNESS;

}