package com.zhuguang.jack.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.zhuguang.jack.invoke.NodeInfo;

public class RmiServerStart {
    public void startRmiServer(String host, String port, String id) {
        try {
            SoaRmiImpl soaRmiImpl = new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.parseInt(port));
            // rmi://127.0.0.1:5689/jacksoa
            Naming.bind("rmi://" + host + ":" + port + "/" + id, soaRmiImpl);
            System.out.println("server: 对象绑定成功");
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public SoaRmi startRmiClient(NodeInfo nodeinfo,String id) {
        String host = nodeinfo.getHost();
        String port = nodeinfo.getPort();
        try {
            return (SoaRmi)Naming.lookup("rmi://" + host + ":" + port + "/" + id);
        }
        catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
