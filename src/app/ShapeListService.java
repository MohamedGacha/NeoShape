package app;

import app.shapes.CanvasTools;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ShapeListService extends Remote {
    ArrayList<CanvasTools> getShapeList() throws RemoteException;
    void updateShapeList(JPanelWrapper DrawingArea) throws RemoteException;
    void simple() throws RemoteException;
}
