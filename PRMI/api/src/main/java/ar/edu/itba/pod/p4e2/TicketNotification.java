package ar.edu.itba.pod.p4e2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TicketNotification extends Remote {

  void vipConfirmed(final String eventName, final String ticket) throws RemoteException;

  void confirmed(final String eventName, final String ticket) throws RemoteException;

  void reserved(final String eventName) throws RemoteException;

  void soldOut(final String eventName) throws RemoteException;

  void cancelled(final String eventName) throws RemoteException;
}
