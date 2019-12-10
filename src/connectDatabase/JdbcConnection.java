/////////////////////////////////////////////////////////////////////////////
//
// © 2019 andro Japan. All right reserved.
//
/////////////////////////////////////////////////////////////////////////////

package connectDatabase;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

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
public class JdbcConnection {
    
    public static  Connection getJdbConnection() {
        final String url ="jdbc:mysql://localhost:3306/atm?useSSL=false";
        final String user = "root";
        final String password = "1234";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return (Connection) DriverManager.getConnection(url,user,password);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
//    public static void main(String[] args) {
//        Connection connection = getJdbConnection();
//        if (connection!= null) {
//            System.out.println("thành công");
//        }
//        else {
//            System.out.println("thất bại");
//        }
//    }

}
