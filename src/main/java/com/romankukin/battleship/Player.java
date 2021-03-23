package com.romankukin.battleship;

import java.util.HashMap;
import java.util.Map;

public class Player {

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
