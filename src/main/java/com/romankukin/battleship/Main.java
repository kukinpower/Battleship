package com.romankukin.battleship;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  static class BadArgumentException extends IllegalArgumentException {

    private final static String MESSAGE = "Error! You passed wrong values! Try again:";

    public BadArgumentException() {
      super(MESSAGE);
    }

    public BadArgumentException(Throwable cause) {
      super(MESSAGE, cause);
    }
  }

  static class WrongShipLocationException extends IllegalArgumentException {

    private final static String MESSAGE = "Error! Wrong ship location! Try again:";

    public WrongShipLocationException() {
      super(MESSAGE);
    }
  }

  static class TooClosePlacingException extends IllegalArgumentException {

    private final static String MESSAGE = "Error! You placed it too close to another one. Try again:";

    public TooClosePlacingException() {
      super(MESSAGE);
    }
  }

  static class BadLengthCoordsException extends IllegalArgumentException {

    private final static String MESSAGE_LEFT = "Error! Wrong length of the ";
    private final static String MESSAGE_RIGHT = "! Try again:";

    public BadLengthCoordsException(String ship) {
      super(MESSAGE_LEFT + ship + MESSAGE_RIGHT);
    }
  }

  private enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int length;

    Ship(String s, int i) {
      name = s;
      length = i;
    }
  }

  private static final Map<Character, Integer> COORDS = Arrays.stream(new Object[][]{
      {'A', 1}, {'B', 2}, {'C', 3}, {'D', 4}, {'E', 5},
      {'F', 6}, {'G', 7}, {'H', 8}, {'I', 9}, {'J', 10}
  }).collect(Collectors.toMap(kv -> (Character) kv[0], kv -> (Integer) kv[1]));

  private final String[][] field = {
      {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
      {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
      {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
  };

  private void checkCoordsValidity(String[] coordsStrings) {
    if (coordsStrings == null || coordsStrings.length != 2
        || coordsStrings[0].length() < 2
        || coordsStrings[1].length() < 2
        || coordsStrings[0].length() > 3
        || coordsStrings[1].length() > 3
        || !Main.COORDS.containsKey(coordsStrings[0].charAt(0))
        || !Main.COORDS.containsKey(coordsStrings[1].charAt(0))) {
      throw new BadArgumentException();
    }
  }

  private boolean isValidCoordinate(int value) {
    return value >= 1 && value <= 10;
  }

  private void checkCoordsRange(String shipType, int shipLength, int x1, int x2, int y1, int y2) {
    if (!(isValidCoordinate(x1) && isValidCoordinate(x2)
        && isValidCoordinate(y1) && isValidCoordinate(y2))) {
      throw new BadArgumentException();
    }
    // the coordinates should only differ on one axis
    if ((x1 == x2) == (y1 == y2)) {
      throw new WrongShipLocationException();
    }
    if ((x1 == x2 && Math.abs(y1 - y2) != shipLength - 1)
        || (y1 == y2 && Math.abs(x1 - x2) != shipLength - 1)) {
      throw new BadLengthCoordsException(shipType);
    }
  }

  private void checkIfCanPlaceShip(int x1, int x2, int y1, int y2) {
    int n = x1 == x2 ? 0 : 1;
    int m = n == 1 ? 0 : 1;

    int x;
    int y;

    for (x = x1, y = y1; x < x2 || y < y2; x += n, y += m) {
      isPlaceOccupiedBySomeShip(x, y);
    }

    isPlaceOccupiedBySomeShip(x, y);
  }

  private void isPlaceOccupiedBySomeShip(int x, int y) {
    if ("O".equals(field[x][y])
        || x + 1 <= 10 && "O".equals(field[x + 1][y])
        || x - 1 >= 1 && "O".equals(field[x - 1][y])
        || y + 1 <= 10 && "O".equals(field[x][y + 1])
        || y - 1 >= 1 && "O".equals(field[x][y - 1])) {
      throw new TooClosePlacingException();
    }
  }

  private void place(int x1, int x2, int y1, int y2) {
    int n = x1 == x2 ? 0 : 1;
    int m = n == 1 ? 0 : 1;

    int x;
    int y;

    for (x = x1, y = y1; x < x2 || y < y2; x += n, y += m) {
      field[x][y] = "O";
    }
    field[x][y] = "O";
  }

  private void printField() {
    for (String[] strings : field) {
      System.out.println(String.join(" ", strings));
    }
  }

  public void play() {
    try (Scanner scanner = new Scanner(System.in)) {

      for (Ship ship : Ship.values()) {
        printField();

        System.out.println(
            System.lineSeparator()
                + "Enter the coordinates of the "
                + ship.name
                + " (" + ship.length
                + " cells):"
                + System.lineSeparator());

        boolean read = false;

        while (!read) {
          String[] coordsStrings = scanner.nextLine().split("\\s+");
          System.out.println();
          try {
            checkCoordsValidity(coordsStrings);

            int x1 = Main.COORDS.get(coordsStrings[0].charAt(0));
            int x2 = Main.COORDS.get(coordsStrings[1].charAt(0));
            int y1 = Integer.parseInt(coordsStrings[0].substring(1));
            int y2 = Integer.parseInt(coordsStrings[1].substring(1));

            // Sort coordinates
            if (x2 < x1) {
              int tmp = x1;
              x1 = x2;
              x2 = tmp;
            }
            if (y2 < y1) {
              int tmp = y1;
              y1 = y2;
              y2 = tmp;
            }

            checkCoordsRange(ship.name, ship.length, x1, x2, y1, y2);
            checkIfCanPlaceShip(x1, x2, y1, y2);
            place(x1, x2, y1, y2);

            read = true;
          } catch (NullPointerException | NumberFormatException e) {
            throw new BadArgumentException(e);
          } catch (BadArgumentException
              | BadLengthCoordsException
              | WrongShipLocationException
              | TooClosePlacingException e) {
            System.out.println(e.getMessage() + System.lineSeparator());
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    printField();
  }

  public static void main(String[] args) {
    Main game = new Main();
    game.play();
  }
}
