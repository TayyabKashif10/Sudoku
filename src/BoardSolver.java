
/*Solver Algorithm Credits: https://norvig.com/sudopy.shtml*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BoardSolver {

    static String rowLabels = "ABCDEFGHI";
    static String columnLabels = "123456789";

    static ArrayList<String> labels = new ArrayList<>(Arrays.asList("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9",
            "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9",
            "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9",
            "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9",
            "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
            "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9",
            "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9",
            "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9"));

    static ArrayList<ArrayList<String>> rows = new ArrayList<>(9);
    static ArrayList<ArrayList<String>> columns = new ArrayList<>(9);
    static ArrayList<ArrayList<String>> blocks = new ArrayList<>(9);

    static Map<String, ArrayList<String>> peerMap = new HashMap<>(81);

    static Map<String, ArrayList<ArrayList<String>>> unitMap = new HashMap<>(81);

    static {
        buildRows();
        buildColumns();
        buildBlocks();
        buildUnitMap();
        buildPeerMap();
    }


    private static void buildRows()
    {
        ArrayList<String> tempRow;
        for (int i = 0 ; i < rowLabels.length() ; i++)
        {
            tempRow = new ArrayList<>(9);
            for (int j = 0 ; j < columnLabels.length(); j++)
            {
                tempRow.add("" + rowLabels.charAt(i) + columnLabels.charAt(j));
            }
            rows.add(tempRow);
        }
    }

    private static void buildColumns()
    {
        ArrayList<String> tempColumn;
        for (int i = 0 ; i < columnLabels.length() ; i++)
        {
            tempColumn = new ArrayList<>(9);
            for (int j = 0 ; j < rowLabels.length(); j++)
            {
                tempColumn.add("" + rowLabels.charAt(j) + columnLabels.charAt(i));
            }
            columns.add(tempColumn);
        }
    }

    private static void buildBlocks()
    {
        blocks.add(new ArrayList<>(Arrays.asList("A1","A2","A3","B1","B2","B3","C1","C2","C3")));
        blocks.add(new ArrayList<>(Arrays.asList("A4","A5","A6","B4","B5","B6","C4","C5","C6")));
        blocks.add(new ArrayList<>(Arrays.asList("A7","A8","A9","B7","B8","B9","C7","C8","C9")));

        blocks.add(new ArrayList<>(Arrays.asList("D1","D2","D3","E1","E2","E3","F1","F2","F3")));
        blocks.add(new ArrayList<>(Arrays.asList("D4","D5","D6","E4","E5","E6","F4","F5","F6")));
        blocks.add(new ArrayList<>(Arrays.asList("D7","D8","D9","E7","E8","E9","F7","F8","F9")));

        blocks.add(new ArrayList<>(Arrays.asList("G1","G2","G3","H1","H2","H3","I1","I2","I3")));
        blocks.add(new ArrayList<>(Arrays.asList("G4","G5","G6","H4","H5","H6","I4","I5","I6")));
        blocks.add(new ArrayList<>(Arrays.asList("G7","G8","G9","H7","H8","H9","I7","I8","I9")));
    }

    private static void buildUnitMap()
    {
        /*
         *  (x,y) represents the position of square on the 9 by 9 sudoku board where x and y in [0,8] [matrix coordinates]
         *  each square belongs to a block, the top left corner square of the block containing (x,y) is represented as (cx, cy)
         *  each block in the 9 by 9 board is assigned an index from 0 to 8 (from left to right and top to bottom counting)
         *  this index is called n, which also represents the index of the block in the 'blocks' array,
         *  n can be calculated from (cx,cy), which is the top left corner square of each block with the formula
         *  n=cx+cy/3
         * */

        int x, y;
        int cx, cy;
        int n;
        for (String label : labels)
        {

            x = label.charAt(0)-'A'; y = label.charAt(1) -  '1';
            cx = x-x%3; cy = y-y%3;

            // cx and cy can only be multiples of 3 i.e [0,3,6]
            assert (cx%3 ==0) && (cy%3 == 0);

            n = cx + (cy/3);

            unitMap.put(label, new ArrayList<>(Arrays.asList(rows.get(x), columns.get(y),blocks.get(n))));

        }
    }

    private static void buildPeerMap() {

        ArrayList<String> peers;
        for (String label: labels)
        {
            peers = new ArrayList<>(20);

            // add peers from same row
            for (String rowElement : unitMap.get(label).get(0))
            {
                if (!rowElement.equals(label))
                {
                    peers.add(rowElement);
                }
            }

            // add peers from same column
            for (String columnElement : unitMap.get(label).get(1))
            {
                if (!columnElement.equals(label))
                {
                    peers.add(columnElement);
                }
            }

            // add peers from same block
            for (String blockElement : unitMap.get(label).get(2))
            {
                if (!blockElement.equals(label) && !peers.contains(blockElement))
                {
                    peers.add(blockElement);
                }
            }

            peerMap.put(label, peers);

        }

    }

    // returns solved grid representation of board, if it cant be solved return null
    public static String solve(String grid)
    {
        String solution;
        if (grid.length() != 81) {throw new RuntimeException("The given sudoku is invalid.");}

        solution = search(parseGrid(grid));

        return solution;
    }

    private static Map<String, String> cloneBoardState(Map<String, String> map)
    {
        Map<String, String> newMap = new HashMap<>(81);
        for (String squareLabel : labels)
        {
            newMap.put(squareLabel,map.get(squareLabel));
        }

        return newMap;
    }

    static private String search(Map<String, String> values)
    {
        // using depth first search and constraint propagation, try all possible values until a valid solution is found
        boolean isSolved = true;

        if (values == null)
        {
            // an invalid board state was given
            return null;
        }

        for (String squareLabel : labels)
        {
            if (values.get(squareLabel).length() != 1)
            {
                isSolved = false;
                break;
            }
        }

        if (!isSolved)
        {
            // assign to the square with least number of possible digits, the first possible digit.

            int minPossibleDigits = 10; // the number of possible digits for any square will always be lower than 10
            String squareWithMinPossibleDigits = null;
            for (String squareLabel : labels)
            {
                if (values.get(squareLabel).length() < minPossibleDigits && values.get(squareLabel).length() > 1)
                {
                    minPossibleDigits = values.get(squareLabel).length();
                    squareWithMinPossibleDigits = squareLabel;
                }
            }

            String digitsToTry = values.get(squareWithMinPossibleDigits);
            for (int i = 0 ; i < digitsToTry.length(); i++)
            {
                String currentTry = search(assign(cloneBoardState(values), squareWithMinPossibleDigits, digitsToTry.charAt(i)));
                if (currentTry != null){return currentTry;}
            }

            // the program hits this statement when all possibilities have been exhausted
            // meaning board is unsolveable
            return null;
        }
        else
        {
            // Board has been solved, return grid representation of solved board
            StringBuilder solvedGrid = new StringBuilder(81);

            for (String squareLabel : labels)
            {
                assert values.get(squareLabel).length() == 1;

                solvedGrid.append(values.get(squareLabel).charAt(0));
            }
            return solvedGrid.toString();
        }
    }
    private static Map<String, String> parseGrid(String grid)
    {
        // a map that assigns to each square its current possible values.
        Map<String, String> values = new HashMap<>(81);

        for (String squareLabel : labels)
        {
            values.put(squareLabel, "123456789");
        }

        // assign values to each square as specified by grid.

        for (int i = 0 ; i < grid.length(); i++)
        {
            //Contradiction: if a square cant be assigned the value specified in the grid, board state must be invalid.
            if (grid.charAt(i) != '0' && (assign(values, labels.get(i), grid.charAt(i))==null))
            {
                return null;
            }
        }

        return values;
    }

    private static  Map<String, String> assign(Map<String, String> values, String squareLabel, char digit)
    {
        String otherDigits = values.get(squareLabel).replace(String.valueOf(digit), "");

        for (int i = 0 ; i < otherDigits.length() ; i++)
        {

            //Contradiction: if all other digits are not able to be eliminated, board state must be invalid.
            if (eliminate(values, squareLabel, otherDigits.charAt(i)) == null)
            {
                return null;
            }
        }

        return values;
    }


    private static Map<String, String> eliminate(Map<String, String> values, String squareLabel, char digit)
    {
        // already eliminated
        if (!values.get(squareLabel).contains(""+ digit))
        {
            return values;
        }

        // remove digit from possible digits
        values.replace(squareLabel, values.get(squareLabel).replace(String.valueOf(digit), ""));

        //Constraint Propagation.


        if (values.get(squareLabel).isEmpty())
        {
            // Contradiction: a square has no possible value.
            return null;
        }
        else if (values.get(squareLabel).length() == 1)
        {
            char digit2 = values.get(squareLabel).charAt(0);

            // if the square has one possible digit, remove that digit from the possible digit lists of all its peers
            // effectively "assigning" the digit to this square.
            for (String peer : peerMap.get(squareLabel))
            {
                //Contradiction: if the digit cannot be removed from all peers, or thus, cannot be assigned to the current square, board state must be invalid
                if (eliminate(values, peer, digit2)==null)
                {
                    return null;
                }
            }
        }

        for (ArrayList<String> unit : unitMap.get(squareLabel))
        {
            // all possible places for the digit in each unit i.e all squares in the unit with digit in its possible digit list
            ArrayList<String> possibleDigitPlaces = new ArrayList<>(9);

            for (String square : unit)
            {
                if (values.get(square).contains(""+digit))
                {
                    possibleDigitPlaces.add(square);
                }
            }

            if (possibleDigitPlaces.toArray().length == 0)
            {
                // contradiction, no square in a unit has place for the digit.
                return null;
            }
            else if (possibleDigitPlaces.toArray().length == 1)
            {
                // assign the digit to the only square which has the digit in its possible digit list per unit
                // if it fails, its a contradiction and an invalid board state.
                if (assign(values, possibleDigitPlaces.get(0), digit)==null)
                {
                    return null;
                }

            }
        }

        return values;
    }

}
