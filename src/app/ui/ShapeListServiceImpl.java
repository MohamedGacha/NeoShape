package app.ui;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;

public class ShapeListServiceImpl extends UnicastRemoteObject implements ShapeListService {
    protected ShapeListServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<CanvasTools> getShapeList() throws RemoteException {
        return null;
    }

    @Override
    public void updateShapeList(JPanelWrapper drawingArea) throws RemoteException {
        System.out.println("drawingArea");
    }

    @Override
    public void simple() throws RemoteException {
        System.out.println("simple");
    }
}
