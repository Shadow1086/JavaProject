package Project01.ledgerSystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Project01.ledgerSystem.models.Users;
import Project01.ledgerSystem.util.DBUtil;

public class UsersDaoImpl implements UsersDao {
    /**
     * 用户注册
     * 
     * @param User user : 从界面获取用户对象
     */
    @Override
    public int signUp(Users user) {
        int id = -1;
        String sql = """
                INSERT INTO users_info
                (user_name,user_password)
                VALUES (?,?);
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.execute();
            ResultSet rs =  ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return id;
        }
        return id;
    }

    /**
     * 用户的登录
     * 
     * @param name  用户名
     * @param password 密码
     */
    @Override
    public Users logIn(String name, String password) {
        String sql = """
                SELECT * FROM users_info
                WHERE user_name = ? AND user_password = ?
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try(ResultSet rs= ps.executeQuery()) {
                if(rs.next()){
                    int idNew = rs.getInt("user_id");
                    double balanceNew = rs.getDouble("user_balance");
                    String nameNew = rs.getString("user_name");
                    String passwordNew = rs.getString("user_password");
                    Users user = new Users(idNew,nameNew,passwordNew,balanceNew);
                    return user;
                }else{
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取/存 款
     */
    @Override
    public double withdrawMoney(Users users, double money, int funcNum) {
        String sql = """
                INSERT INTO account_details (user_id,user_detail,user_detail_balance)
                VALUES (?,?,?)
                """;
        double result = -1;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            double newBalance = -1;
            ps.setInt(1, users.getId());
            if (funcNum > 0) {
                ps.setString(2, "收入");
                ps.setDouble(3, money);
                newBalance = getBalance(users.getId()) + money;
            } else if (funcNum < 0) {
                ps.setString(2, "支出");
                ps.setDouble(3, money);
                newBalance = getBalance(users.getId()) - money;
            } else {
                return result;
            }
            resetBalance(users.getId(), newBalance);
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 获取用户详细的收支记录
     */
    @Override
    public StringBuilder[] getInfo(Users users) {
        StringBuilder[] result = new StringBuilder[15];
        String sql = """
                SELECT us.user_name , ad.user_detail , ad.user_detail_balance , ad.update_time
                FROM account_details ad
                LEFT JOIN users_info us ON ad.user_id = us.user_id
                WHERE us.user_id = ?
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, users.getId());
            try (ResultSet rs = ps.executeQuery()) {
                int i = 0;
                while (rs.next()&& i<=15) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(rs.getString("user_name"));
                    sb.append(rs.getString("user_detail"));
                    sb.append(rs.getString("user_detail_balance"));
                    sb.append(rs.getString("update_time"));
                    result[i] = sb;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * 更新用户余额
     */
    @Override
    public boolean resetBalance(int user_id, double money) {
        String sql = """
                UPDATE users_info
                SET user_balance = ?
                WHERE user_id = ?
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, money);
            ps.setInt(2, user_id);
        } catch (SQLException e) {
            e.printStackTrace();
            // throw new SQLException(e);
            return false;
        }
        return true;
    }

    /**
     * 获取用户的余额,返回-1为错误
     */
    @Override
    public double getBalance(int user_id) {
        double balance = -1;
        String sql = """
                SELECT user_balance
                FROM users_info
                WHERE user_id = ?
                """;
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("user_balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return balance;
    }
}
