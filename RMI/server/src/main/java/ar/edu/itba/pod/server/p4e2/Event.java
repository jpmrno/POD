package ar.edu.itba.pod.server.p4e2;

import java.util.Objects;

public class Event {

  public enum State {
    TBC, CONFIRMED, SOLD_OUT, CANCELLED
  }

  private final String name;
  private final int minCapacity;
  private final int maxCapacity;
  private final int vipCapacity;
  private State state;

  public Event(final String name, final int minCapacity, final int maxCapacity,
      final int vipCapacity) {
    this.name = Objects.requireNonNull(name);
    this.minCapacity = minCapacity;
    this.maxCapacity = maxCapacity;
    this.vipCapacity = vipCapacity;
    this.state = State.TBC;
  }

  public String getName() {
    return name;
  }

  public int getMinCapacity() {
    return minCapacity;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public int getVipCapacity() {
    return vipCapacity;
  }

  public void setState(State state) {
    this.state = Objects.requireNonNull(state);
  }

  public State getState() {
    return state;
  }

  @Override
  public String toString() {
    return "Event{" +
        "name='" + name + '\'' +
        ", minCapacity=" + minCapacity +
        ", maxCapacity=" + maxCapacity +
        ", vipCapacity=" + vipCapacity +
        ", state=" + state +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Event)) {
      return false;
    }

    final Event event = (Event) o;

    return name.equals(event.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
