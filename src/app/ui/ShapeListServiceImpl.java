package app.ui;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import app.JPanelWrapper;
import app.ShapeListService;
import app.shapes.CanvasTools;

public class ShapeListServiceImpl extends UnicastRemoteObject implements ShapeListService {
    private CanvasTools receivedShape;

    protected ShapeListServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<CanvasTools> getShapeList() throws RemoteException {
        return null;
    }

    @Override
    public void updateShapeList(JPanelWrapper newArea) throws RemoteException {

    }

    @Override
    public void simple() throws RemoteException {

    }

    @Override
    public void sendShape(CanvasTools shape) throws RemoteException {
        receivedShape = shape;
        // Implement code to send the shape over RMI to the client
    }

    @Override
    public CanvasTools receiveShape() throws RemoteException {
        // Implement code to receive the shape over RMI from the client
        return receivedShape;
    }
}
