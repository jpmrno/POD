package ar.edu.itba.pod.server.p4e2;

import ar.edu.itba.pod.p4e2.EventService;
import ar.edu.itba.pod.p4e2.TicketNotification;
import ar.edu.itba.pod.p4e2.TicketService;
import ar.edu.itba.pod.server.p4e2.Event.State;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventTicketService implements EventService, TicketService {

  private static Logger logger = LoggerFactory.getLogger(EventTicketService.class);

  private final Map<Event, List<TicketNotification>> events;

  public EventTicketService() {
    events = new HashMap<>();
  }

  @Override
  public synchronized void addEvent(final String name, final int minCapacity, final int maxCapacity,
      final int vipCapacity) throws RemoteException {
    final Event newEvent = new Event(name, minCapacity, maxCapacity, vipCapacity);
    events.putIfAbsent(newEvent, new LinkedList<>());
  }

  @Override
  public synchronized void cancelEvent(final String name) throws RemoteException {
    final Optional<Event> event = events.keySet().stream()
        .filter(e -> e.getName().equals(name))
        .findFirst();

    if (!event.isPresent()) {
      throw new IllegalArgumentException("Event does not exist");
    }
    event.get().setState(State.CANCELLED);

    final List<TicketNotification> attendants = events.get(event.get());
    attendants.forEach(a -> {
      try {
        a.cancelled(name);
      } catch (final RemoteException exception) {
        logger.error("Could not notify client", exception);
      }
    });
  }

  @Override
  public synchronized void request(final String eventName, final TicketNotification handler)
      throws RemoteException {
    final Optional<Event> eventOptional = events.keySet().stream()
        .filter(e -> e.getName().equals(eventName))
        .findFirst();

    if (!eventOptional.isPresent()) {
      return;
    }

    final Event event = eventOptional.get();
    final List<TicketNotification> attendants = events.get(event);

    if (event.getState().equals(State.TBC)) {
      attendants.add(handler);
      handler.reserved(eventName);

      if (attendants.size() == event.getMinCapacity()) {
        event.setState(State.CONFIRMED);

        final Iterator<TicketNotification> attendantIterator = attendants.iterator();
        for (int i = 0; i < event.getVipCapacity() && attendantIterator.hasNext(); i++) {
          attendantIterator.next().vipConfirmed(eventName, UUID.randomUUID().toString());
        }

        while (attendantIterator.hasNext()) {
          attendantIterator.next().confirmed(eventName, UUID.randomUUID().toString());
        }
      }
    } else if (event.getState().equals(State.CONFIRMED)) {
      handler.reserved(eventName);
      if (attendants.size() < event.getVipCapacity()) {
        handler.vipConfirmed(eventName, UUID.randomUUID().toString());
        attendants.add(handler);
      } else {
        attendants.add(handler);
        handler.confirmed(eventName, UUID.randomUUID().toString());
      }

      if (attendants.size() == event.getMaxCapacity()) {
        event.setState(State.SOLD_OUT);
      }
    } else if (event.getState().equals(State.SOLD_OUT)) {
      handler.soldOut(eventName);
    } else if (event.getState().equals(State.CANCELLED)) {
      handler.cancelled(eventName);
    }
  }
}
