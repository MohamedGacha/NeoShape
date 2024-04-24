package app;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import app.shapes.CanvasTools;

public interface ShapeListService extends Remote {
    ArrayList<CanvasTools> getShapeList() throws RemoteException;
    void updateShapeList(JPanelWrapper newArea) throws RemoteException;
    void simple() throws RemoteException; // Add this method to the interface
    void sendShape(CanvasTools shape) throws RemoteException;
    CanvasTools receiveShape() throws RemoteException;
}

