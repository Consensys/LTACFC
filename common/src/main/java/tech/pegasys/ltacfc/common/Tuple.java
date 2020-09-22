package tech.pegasys.ltacfc.common;

public class Tuple<K, V, U> {

  private K first;
  private V second;
  private U third;

  public Tuple(K first, V second){
    this.first = first;
    this.second = second;
    this.third = null;
  }
  public Tuple(K first, V second, U third){
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public K getFirst() {
    return first;
  }

  public V getSecond() {
    return second;
  }

  public U getThird() {
    return third;
  }
}
