/////////////////////////////////////////////////////////////////////////////
//
// © 2019 andro Japan. All right reserved.
//
/////////////////////////////////////////////////////////////////////////////

package Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.mysql.jdbc.Connection;

import Dao.impl.ProcessImpl;
import connectDatabase.JdbcConnection;

/**
 * [OVERVIEW] XXXXX.
 *
 * @author: tuyenhc
 * @version: 1.0
 * @History
 * [NUMBER]  [VER]     [DATE]          [USER]             [CONTENT]
 * --------------------------------------------------------------------------
 * 001       1.0       2019/12/09      tuyenhc       Create new
*/
public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            
            Registry rgsty = LocateRegistry.createRegistry(8000);
            rgsty.rebind("rmi://localhost/Dao/process",new ProcessImpl() );
            Connection connection = JdbcConnection.getJdbConnection();
            if (connection!= null) {
                System.out.println("thành công");
            }
            else {
                System.out.println("thất bại");
            }
        
    

            System.out.println("Server RMI đang hoạt động...");
    } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
    }

    }

}
