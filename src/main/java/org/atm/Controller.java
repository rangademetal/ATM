package org.atm;

import java.sql.*;

public class Controller {
    protected String url;
    protected String name;
    protected String password;

    Controller(String url, String name, String password) {
        this.url = url;
        this.name = name;
        this.password = password;
    }
    Card card = new Card();

    protected boolean isChecked(String x) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM card where pincard =?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.url, this.name, this.password);
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, x);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            card.setPin(rs.getString("pincard"));
            card.setMoney(rs.getInt("money"));
            pst.close();
            con.close();
            return true;
        }
        pst.close();
        con.close();
        return false;
    }
    protected int getMoneyStatus() {
        return card.getMoney();
    }
    protected int withdraw(int money) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE card SET money=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.url, this.name, this.password);
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, card.getMoney() - money);
        card.setMoney(card.getMoney());
        pst.executeUpdate();
        pst.close();
        con.close();
        updateWithdraw(card.getPin());
        return card.getMoney();
    }
    protected int updateWithdraw(String pin) throws SQLException, ClassNotFoundException {
        String sql ="SELECT money from card where pincard=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.url, this.name, this.password);
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, pin);
        ResultSet rs = pst.executeQuery();
        if(rs.next())
            card.setMoney(rs.getInt("money"));
        pst.close();
        con.close();
        return card.getMoney();
    }
    protected String changePIN(String pin) throws  SQLException, ClassNotFoundException {
        String sql = "UPDATE card SET pincard=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(this.url, this.name, this.password);
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, pin);
        pst.executeUpdate();
        pst.close();
        con.close();
        return "Your PIN is changed!";
    }
}