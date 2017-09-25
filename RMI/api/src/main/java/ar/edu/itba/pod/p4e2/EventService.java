package ar.edu.itba.pod.p4e2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EventService extends Remote {

  void addEvent(final String name, final int minCapacity, final int maxCapacity,
      final int vipCapacity) throws RemoteException;

  void cancelEvent(final String name) throws RemoteException;
}
