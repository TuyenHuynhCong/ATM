/////////////////////////////////////////////////////////////////////////////
//
// © 2019 andro Japan. All right reserved.
//
/////////////////////////////////////////////////////////////////////////////

package Client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Dao.impl.Process;
import Entity.User;
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
public class ATMTonDucThang {

    private static Scanner sc;

    /**
     * @param args
     */
    public static void main(String[] args) {

        sc = new Scanner(System.in);
        try {
            Registry rgsty = LocateRegistry.getRegistry(8000);
            //            ProcessImpl impl = (ProcessImpl) rgsty.lookup("rmi://localhost/Dao/process");
            Process process = (Process) rgsty.lookup("rmi://localhost/Dao/process");
            Login();

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    public static void Login() {
        System.out.println("----------------Đăng Nhập------------------");
        System.out.println("nhập mã số thẻ: ");
        Integer mst = sc.nextInt();
        System.out.println("nhập password: ");
        Integer password = sc.nextInt();

        Connection connection = JdbcConnection.getJdbConnection();
        String sqlString = "SELECT * from user WHERE id=? and password=?";
        PreparedStatement sql;
        try {
            sql = connection.prepareStatement(sqlString);
            sql.setInt(1, mst);
            sql.setInt(2, password);

            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                String checkLogin = rs.getString(6);
                Integer money = rs.getInt(4);
                String nameUser = rs.getString(2);
                //                Integer password = rs.getInt(6);
                if (checkLogin.equals("1")) {
                    System.out.println("welcome admin " + nameUser);
                    
                    menuAdmin();
                    while (true) {
                        
                        Integer check = sc.nextInt();
                        switch (check) {
                        case 1:
                            viewAdmin(mst);
                            break;
                        case 2:
                            viewAllUser();
                            break;
                        case 3:
                            ChuyenTienAdmin(mst, money);
                            break;
                        case 4:
                            RutTienAdmin(mst, money);
                            break;
                        case 5:
                            DoiMatKhauAdmin(mst, password);
                        case 6:
                            System.out.println("hẹn gặp lại");
                            Login();
                            break;
                        
                        default:
                            throw new IllegalArgumentException("Nhập không hợp lệ: " + check);
                        }
                    }
                } else {
                    System.out.println("welcome to "+nameUser);
                    menu();
                    while (true) {
                        
                        Integer check = sc.nextInt();
                        switch (check) {
                        case 1:
                            view(mst);
                            break;
                        case 2:
                            ChuyenTien(mst, money);
                            break;
                        case 3:
                            RutTien(mst, money);
                            break;
                        case 4:
                            DoiMatKhau(mst, password);
                            break;
                        case 5:
                            System.out.println("hẹn gặp lại");
                            break;
                        default:
                            throw new IllegalArgumentException("Nhập không hợp lệ " + check);
                        }
                    }
                }

            } else {
                System.out.println("login thất bại");
                Login();
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void view(Integer mst) {
        Connection connection = JdbcConnection.getJdbConnection();
        String sqlString = "SELECT * from user WHERE id=? ";
        PreparedStatement sql;
        try {
            sql = connection.prepareStatement(sqlString);
            sql.setInt(1, mst);

            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setAge(rs.getInt(3));
                user.setAmount(rs.getInt(4));
                user.setPassword(rs.getInt(5));
                user.setRole(rs.getString(6));
                System.out.println(user.toString());
            }

        } catch (SQLException e) {
            System.out.println("mã số thẻ không đúng !!!");
            
        }
        menu();
        

    }
    public static void viewAdmin(Integer mst) {
        Connection connection = JdbcConnection.getJdbConnection();
        String sqlString = "SELECT * from user WHERE id=? ";
        PreparedStatement sql;
        try {
            sql = connection.prepareStatement(sqlString);
            sql.setInt(1, mst);

            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setFullName(rs.getString(2));
                user.setAge(rs.getInt(3));
                user.setAmount(rs.getInt(4));
                user.setPassword(rs.getInt(5));
                user.setRole(rs.getString(6));
                System.out.println(user.toString());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        menuAdmin();

    }

    public static void ChuyenTien(Integer mst, Integer money) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("nhập số tài khoản bạn muốn chuyển :");
        Integer mstChuyen = sc.nextInt();
        boolean checkTrue = true;
        if (checkMST(mstChuyen)==checkTrue) {
            System.out.println("mã số thẻ không hợp lệ !!!");
            ChuyenTien(mstChuyen, money);
        }
        System.out.println("nhập số tiền bạn muốn chuyển");

        Integer moneyChuyen = sc.nextInt();
        if (moneyChuyen > money) {
            System.out.println("Tài  khoản của bạn không đủ tiền để chuyển !!!");
            ChuyenTien(mst, money);
        } 
        else if (money <(moneyChuyen+50000)) {
            System.out.println("số tiền trong thẻ sau khi chuyển phải lớn hơn 50.000vnd");
            ChuyenTien(mst, money);
        }else {
            //update user nhận
            String sql = "UPDATE user SET amount= (amount + ?) WHERE id=?";
            //update user chuyển
            String sql1 = "UPDATE user SET amount=(amount - ?) WHERE id=?";
            try {
                // update user nhận
                PreparedStatement pStatement = connection.prepareStatement(sql);
                pStatement.setInt(1, moneyChuyen);
                pStatement.setInt(2, mstChuyen);
                int status = pStatement.executeUpdate();
                if (status > 0) {
                    System.out.println("bạn đã chuyển tiền thành công !!!");
                }
                //update user chuyển
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, moneyChuyen);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("Số tiền trong thẻ của bạn còn là :" + (money - moneyChuyen));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    
    public static void ChuyenTienAdmin(Integer mst, Integer money) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("nhập số tài khoản bạn muốn chuyển :");
        Integer mstChuyen = sc.nextInt();

        System.out.println("nhập số tiền bạn muốn chuyển");

        Integer moneyChuyen = sc.nextInt();
        if (moneyChuyen > money) {
            System.out.println("Tài  khoản của bạn không đủ tiền để chuyển !!!");
            ChuyenTienAdmin(mst, money);
        } 
        else if (money <(moneyChuyen+50000)) {
            System.out.println("số tiền tiền trong thẻ phải lớn hơn 50.000vnd");
            ChuyenTienAdmin(mst, money);
        }else {
            //update user nhận
            String sql = "UPDATE user SET amount= (amount + ?) WHERE id=?";
            //update user chuyển
            String sql1 = "UPDATE user SET amount=(amount - ?) WHERE id=?";
            try {
                // update user nhận
                PreparedStatement pStatement = connection.prepareStatement(sql);
                pStatement.setInt(1, moneyChuyen);
                pStatement.setInt(2, mstChuyen);
                int status = pStatement.executeUpdate();
                if (status > 0) {
                    System.out.println("bạn đã chuyển tiền thành công !!!");
                }
                //update user chuyển
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, moneyChuyen);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("Số tiền trong thẻ của bạn còn là :" + (money - moneyChuyen));
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public static void RutTien(Integer mst, Integer money) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("Nhập vào số tiền bạn muốn rút");
        Integer moneyRut = sc.nextInt();
        if (money < moneyRut) {
            System.out.println("số tiền trong thẻ không đủ để rút");
            RutTien(mst,money);
        }
        else if (money <50000+moneyRut) {
            System.out.println("số tiền trong thẻ phải lớn hơn 50000vnđ!!");
            RutTien(mst,money);
        }else {
            //update user chuyển
            String sql1 = "UPDATE user SET amount=(amount - ?) WHERE id=?";
            try {
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, moneyRut);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("bạn đã Rút tiền thành công, số tiền bạn còn trong thẻ  là :" + (money - moneyRut));
                    menu();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    public static void RutTienAdmin(Integer mst, Integer money) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("Nhập vào số tiền bạn muốn rút");
        Integer moneyRut = sc.nextInt();
        
        if (money < moneyRut) {
            System.out.println("số tiền trong thẻ không đủ để rút");
            RutTienAdmin(mst, money);
        } 
        else if (money <50000+moneyRut) {
            System.out.println("số tiền trong thẻ phải lớn hơn 50000vnđ!!");
            RutTienAdmin(mst, money);
        }else {
            //update user chuyển
            String sql1 = "UPDATE user SET amount=(amount - ?) WHERE id=?";
            try {
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, moneyRut);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("bạn đã Rút tiền thành công, số tiền bạn còn trong thẻ  là :" + (money - moneyRut));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static void DoiMatKhau(Integer mst, Integer password) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("Nhập vào mật khẩu hiện tại :");

        Integer newpass = sc.nextInt();
        if (!newpass.equals(password)) {
            System.out.println("mật  khẩu hiện tại không đúng !!!");
            DoiMatKhau(mst, password);
        }

        else {
            System.out.println("nhập mật khẩu mới :");
            Integer newpass1 = sc.nextInt();
            //update user chuyển
            String sql1 = "UPDATE user SET password=? WHERE id=?";
            try {
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, newpass1);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("bạn đã đổi mật khẩu thành công,mật khẩu hiện tại của bạn là :" + newpass1);
                    menu();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    public static void DoiMatKhauAdmin(Integer mst, Integer password) {
        Connection connection = JdbcConnection.getJdbConnection();

        System.out.println("Nhập vào mật khẩu hiện tại :");

        Integer newpass = sc.nextInt();
        if (!newpass.equals(password)) {
            System.out.println("mật  khẩu hiện tại không đúng !!!");
            DoiMatKhauAdmin(mst, password);
        }

        else {
            System.out.println("nhập mật khẩu mới :");
            Integer newpass1 = sc.nextInt();
            //update user chuyển
            String sql1 = "UPDATE user SET password=? WHERE id=?";
            try {
                PreparedStatement pst1 = connection.prepareStatement(sql1);
                pst1.setInt(1, newpass1);
                pst1.setInt(2, mst);
                int status1 = pst1.executeUpdate();
                if (status1 > 0) {
                    System.out.println("bạn đã đổi mật khẩu thành công,mật khẩu hiện tại của bạn là :" + newpass1);
                    menuAdmin();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    public static  Boolean checkMST(Integer mstCheck) {
        
        Connection connection = JdbcConnection.getJdbConnection();
        String sqlString = "SELECT * from user WHERE id=? ";
        PreparedStatement sql;
        try {
            sql = connection.prepareStatement(sqlString);
            sql.setInt(1, mstCheck);

            ResultSet rs = sql.executeQuery();
            

        } catch (SQLException e) {
            System.out.println("mã số thẻ không đúng !!!");
            return false;
        }
        return true;
    }

    public static void menu() {
        System.out.println("------------------menu----------------");
        System.out.println("1.thông tin cá nhân");
        System.out.println("2.chuyển khoản");
        System.out.println("3.rút tiền");
        System.out.println("4.đổi mật khẩu");
        System.out.println("5.thoát");
        System.out.println("---------------------------------------");

    }

    public static void menuAdmin() {
        System.out.println("------------------menu----------------");
        System.out.println("1.thông tin cá nhân ");
        System.out.println("2.xem thông tin tất cả Người dùng");
        System.out.println("3.chuyển khoản");
        System.out.println("4.rút tiền");
        System.out.println("5.đổi mật khẩu");
        System.out.println("6.thoát");
        System.out.println("---------------------------------------");

    }

    public static void viewAllUser() {
        Connection connection = JdbcConnection.getJdbConnection();
        String sqlString = "SELECT * from user ";
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement(sqlString);
            ResultSet rs = pst.executeQuery(sqlString);

       

            while (rs.next()) {

                Integer idInteger = rs.getInt(1);
                String fullname = rs.getString(2);
                Integer age = rs.getInt(3);
                Integer amount = rs.getInt(4);
                Integer password = rs.getInt(5);
                String role = rs.getString(6);

                User user = new User();
                user.setId(idInteger);
                user.setFullName(fullname);
                user.setAge(age);
                user.setAmount(amount);
                user.setPassword(password);
                user.setRole(role);
                System.out.println(user);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        menuAdmin();
    }
   

}
