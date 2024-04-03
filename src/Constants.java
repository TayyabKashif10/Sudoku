import java.awt.*;

 class Constants {

     final static String DEFAULT_TITLE = "Sudoku";
     static final Color HIGHLIGHT_COLOR = new Color(220, 175, 159);
     static final Color FIXED_DIGIT_COLOR = new Color(246,224,181);
     static final Color USER_DIGIT_COLOR = new Color(151,153,186);

     static final Font FIXED_DIGIT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);

     static final Font USER_DIGIT_FONT = FIXED_DIGIT_FONT;
     static final Color SELECTION_COLOR = new Color(170,111,115);
     static final Color FALSE_VALUE_COLOR = new Color(203, 85, 85);
     static int UPPER_BAR_HEIGHT;
     static int BOTTOM_BAR_HEIGHT;

     static int LEFT_BAR_HEIGHT;
     static int RIGHT_BAR_HEIGHT;

     final static int SQUARE_SIDE = 60;
     final static int BORDER_THICKNESS = 3;

     final static int BOARD_SIDE = SQUARE_SIDE*9 + BORDER_THICKNESS*10;

     final static Color SQUARE_COLOR = new Color(82, 68, 75);
     final static Color BORDER_COLOR = Color.gray;


    // home screen background color actually applies to all screen backgrounds
     static final Color SCREEN_BACKGROUND_COLOR = SQUARE_COLOR;
     static final Font HOME_SCREEN_TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 100);

    // used for written text on each screen.
     static final Color TEXT_COLOR = FIXED_DIGIT_COLOR;
     static final Font MENU_SCREEN_TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
     static final Color BUTTON_COLOR = USER_DIGIT_COLOR;
     static final Color ACTIVE_BUTTON_COLOR = FIXED_DIGIT_COLOR;

    // for both level selection and home screen
     static final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 40);
     static final int BUTTON_HEIGHT = 100;
     static final int BUTTON_WIDTH = 200;


    // for win screen
     static final Font WIN_SCREEN_BUTTON_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 60);
     static final int WIN_SCREEN_BUTTON_HEIGHT = BUTTON_HEIGHT;
     static final int WIN_SCREEN_BUTTON_WIDTH = 250;

    // in case the program cant read from the puzzle source files, resort to a default puzzle.
     static final String DEFAULT_PUZZLE = "000000000000200045008070210000006090051802470020100000067040800940001000000000000";



    // for all buttons
     static final int BUTTON_BOUNDARY_THICKNESS = BORDER_THICKNESS;

}
