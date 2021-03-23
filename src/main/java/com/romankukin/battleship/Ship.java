package com.romankukin.battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {

  private final ShipType type;
  private final List<Point> list;

  public Ship(ShipType type) {
    this.type = type;
    this.list = new ArrayList<>(type.getLength());
  }

  public void put(Point p) {
    if (list.size() == type.getLength()) {
      return;
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
