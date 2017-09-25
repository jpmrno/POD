package ar.edu.itba.pod.p4e2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicketService extends Remote {

  void request(final String eventName, final TicketNotification handler) throws RemoteException;
}
