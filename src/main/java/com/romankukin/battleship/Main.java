package com.romankukin.battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

  static class WrongCoordinatesException extends IllegalArgumentException {

    private final static String MESSAGE = "Error! You entered the wrong coordinates! Try again:";

    public WrongCoordinatesException() {
      super(MESSAGE);
    }

    public WrongCoordinatesException(Throwable cause) {
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

  private enum ShipType {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int length;

    ShipType(String s, int i) {
      name = s;
      length = i;
    }
  }

  enum ShotMessage {
    HIT(System.lineSeparator() + "You hit a ship! Try again:" + System.lineSeparator(), "X"),
    MISS(System.lineSeparator() + "You missed! Try again:" + System.lineSeparator(), "M");

    private final String message;
    private final String sign;

    ShotMessage(String message, String sign) {
      this.message = message;
      this.sign = sign;
    }
  }

  class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Point)) {
        return false;
      }
      Point point = (Point) o;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  class Ship {

    private final ShipType type;
    private final List<Point> list;

    public Ship(ShipType type) {
      this.type = type;
      this.list = new ArrayList<>(type.length);
    }

    public void put(Point p) {
      if (list.size() == type.length) {
        return;//todo exception
      }
      list.add(p);
    }

    public void remove(Point p) {
      list.remove(p);
    }

    public boolean isEmpty() {
      return list.isEmpty();
    }
  }

  Player player1;
  Player player2;

  public Main() {
    player1 = new Player(1);
    player2 = new Player(2);
    player1.setNext(player2);
    player2.setNext(player1);
  }

  private static final Map<Character, Integer> COORDS = Arrays.stream(new Object[][]{
      {'A', 1}, {'B', 2}, {'C', 3}, {'D', 4}, {'E', 5},
      {'F', 6}, {'G', 7}, {'H', 8}, {'I', 9}, {'J', 10}
  }).collect(Collectors.toMap(kv -> (Character) kv[0], kv -> (Integer) kv[1]));

  class Player {

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

    private final String[][] displayField = {
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

    private final Map<Point, Ship> map;
    private int ships;
    private final int number;
    private Player next;

    public Player(int number) {
      this.number = number;
      this.map = new HashMap<>();
      this.ships = 0;
      this.next = null;
    }

    public Player next() {
      return next;
    }

    public void setNext(Player player) {
      this.next = player;
    }

    public int getNumber() {
      return number;
    }

    public String[][] getField() {
      return field;
    }

    public String[][] getDisplayField() {
      return displayField;
    }

    public Map<Point, Ship> getMap() {
      return map;
    }

    public int getShips() {
      return ships;
    }

    public void incShips() {
      ships++;
    }

    public void decShips() {
      ships--;
    }
  }

  private void checkCoords(int x, int y) {
    if (!isValidCoordinate(x) || !isValidCoordinate(y)) {
      throw new WrongCoordinatesException();
    }
  }

  private boolean isValidCoordinate(int value) {
    return value >= 1 && value <= 10;
  }

  private void checkCoordsRange(String shipType, int shipLength, int x1, int x2, int y1, int y2) {
    // the coordinates should only differ on one axis
    if ((x1 == x2) == (y1 == y2)) {
      throw new WrongShipLocationException();
    }
    if ((x1 == x2 && Math.abs(y1 - y2) != shipLength - 1)
        || (y1 == y2 && Math.abs(x1 - x2) != shipLength - 1)) {
      throw new BadLengthCoordsException(shipType);
    }
  }

  private void checkIfCanPlaceShip(Player player, int x1, int x2, int y1, int y2) {
    int n = x1 == x2 ? 0 : 1;
    int m = n == 1 ? 0 : 1;

    int x;
    int y;

    for (x = x1, y = y1; x < x2 || y < y2; x += n, y += m) {
      isPlaceOccupiedBySomeShip(player, x, y);
    }

    isPlaceOccupiedBySomeShip(player, x, y);
  }

  private void isPlaceOccupiedBySomeShip(Player player, int x, int y) {
    if ("O".equals(player.getField()[x][y])
        || x + 1 <= 10 && "O".equals(player.getField()[x + 1][y])
        || x - 1 >= 1 && "O".equals(player.getField()[x - 1][y])
        || y + 1 <= 10 && "O".equals(player.getField()[x][y + 1])
        || y - 1 >= 1 && "O".equals(player.getField()[x][y - 1])) {
      throw new TooClosePlacingException();
    }
  }

  private void printPlayerField(Player player) {
    for (String[] strings : player.next().getDisplayField()) {
      System.out.println(String.join(" ", strings));
    }
    System.out.println("---------------------");
    for (String[] strings : player.getField()) {
      System.out.println(String.join(" ", strings));
    }
  }

  private void printField(Player player) {
    for (String[] strings : player.getField()) {
      System.out.println(String.join(" ", strings));
    }
  }

  private void printDisplayField(Player player) {
    for (String[] strings : player.getDisplayField()) {
      System.out.println(String.join(" ", strings));
    }
  }

  private void putShip(Player player, Ship ship, Point p) {
    ship.put(p);
    player.getMap().put(p, ship);
    player.getField()[p.getX()][p.getY()] = "O";
  }

  private void place(Player player, ShipType shipType, int x1, int x2, int y1, int y2) {
    int n = x1 == x2 ? 0 : 1;
    int m = n == 1 ? 0 : 1;

    int x;
    int y;

    Ship ship = new Ship(shipType);

    for (x = x1, y = y1; x < x2 || y < y2; x += n, y += m) {
      putShip(player, ship, new Point(x, y));
    }
    putShip(player, ship, new Point(x, y));
    player.incShips();
  }

  private boolean placeShip(Player player, ShipType shipType, String[] coordsStrings) {
    int x1 = Main.COORDS.get(coordsStrings[0].charAt(0));
    int x2 = Main.COORDS.get(coordsStrings[1].charAt(0));
    int y1 = Integer.parseInt(coordsStrings[0].substring(1));
    int y2 = Integer.parseInt(coordsStrings[1].substring(1));

    checkCoords(x1, y1);
    checkCoords(x2, y2);

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

    checkCoordsRange(shipType.name, shipType.length, x1, x2, y1, y2);
    checkIfCanPlaceShip(player, x1, x2, y1, y2);
    place(player, shipType, x1, x2, y1, y2);

    return true;
  }

  private void placeShips(Player player, Scanner scanner) {
    for (ShipType ship : ShipType.values()) {
      printField(player);

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
          try {
            read = placeShip(player, ship, coordsStrings);
          } catch (NullPointerException
              | NumberFormatException
              | IndexOutOfBoundsException e) {
            throw new WrongCoordinatesException(e);
          }
        } catch (WrongCoordinatesException
            | BadLengthCoordsException
            | WrongShipLocationException
            | TooClosePlacingException e) {
          System.out.println(e.getMessage() + System.lineSeparator());
        }
      }
    }
    printField(player);
    System.out.println();
  }

  private void applyChanges(Player player, ShotMessage shotMessage, int x, int y) {
    if (!"X".equals(player.getField()[x][y])) {
      player.getField()[x][y] = shotMessage.sign;
      player.getDisplayField()[x][y] = shotMessage.sign;
    }
    printDisplayField(player);

    if (shotMessage.equals(ShotMessage.HIT)) {
      Point p = new Point(x, y);
      if (player.getMap().containsKey(p)) {
        Ship ship = player.getMap().get(p);
        ship.remove(p);
        if (ship.isEmpty()) {
          player.decShips();
          if (player.getShips() == 0) {
            System.out.println(
                System.lineSeparator() + "You sank the last ship. You won. Congratulations!");
          } else {
            System.out.println(System.lineSeparator() + "You sank a ship! Specify a new target:"
                + System.lineSeparator());
          }
          return;
        }

      }
    }
    System.out.println(shotMessage.message);
  }

  private void takeAShot(Player player, int x, int y) {
    if ("O".equals(player.getField()[x][y])) {
      applyChanges(player, ShotMessage.HIT, x, y);
    } else {
      applyChanges(player, ShotMessage.MISS, x, y);
    }
  }

  private boolean shoot(Player player, String coords) {
    int x = Main.COORDS.get(coords.charAt(0));
    int y = Integer.parseInt(coords.substring(1));

    checkCoords(x, y);
    takeAShot(player.next(), x, y);
    return player.next().getShips() == 0;
  }

  private void play(Scanner scanner) {
    System.out.println("The game starts!" + System.lineSeparator());
    Player player = player1;

    boolean isGameFinished = false;

    while (!isGameFinished) {
      printPlayerField(player);
      System.out.println(
          System.lineSeparator() + "Player " + player.getNumber() + ", it's your turn:" + System
              .lineSeparator());
      String coords = scanner.nextLine();
      try {
        try {
          isGameFinished = shoot(player, coords);
          player = player.next();
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
          throw new WrongCoordinatesException(e);
        }
      } catch (WrongCoordinatesException e) {
        System.out.println(e.getMessage() + System.lineSeparator());
      }
    }
  }

  private void playerPlaceShips(Player player, Scanner scanner) {
    System.out.println(
        "Player " + player.getNumber() + ", place your ships on the game field" + System
            .lineSeparator());
    placeShips(player, scanner);
    System.out.println("Press Enter and pass the move to another player");
    scanner.nextLine();
  }

  public void playGame() {
    try (Scanner scanner = new Scanner(System.in)) {
      playerPlaceShips(player1, scanner);
      playerPlaceShips(player2, scanner);

      play(scanner);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void main(String[] args) {
    Main game = new Main();
    game.playGame();
  }
}
