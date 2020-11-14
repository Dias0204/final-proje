package kz.edu.astanait.controllers;

import kz.edu.astanait.JDBC.DB;
import kz.edu.astanait.interfaces.IController;
import kz.edu.astanait.models.User;

import javax.ws.rs.BadRequestException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserController implements IController<User> {
    private static DB db = new DB();

    public boolean checkEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement stmt = db.getConnection().prepareStatement(sql);

        stmt.setString(1, email);
        System.out.println("'" + email + "'");
        ResultSet rset = stmt.executeQuery();
        if (rset.next()) {
            System.out.println("hi");
            return true;
        }
        return false;
    }

    public User checkLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(sql);

        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rset = stmt.executeQuery();

        User user = null;

        if (rset.next()) {
            if (rset.getString("role").equals("club moderator")) {
                String sqlClub = "SELECT * FROM club_moders WHERE user_id = ?";
                PreparedStatement stmtClub = db.getConnection().prepareStatement(sqlClub);
                stmtClub.setInt(1, rset.getInt("user_id"));
                ResultSet rsetClub = stmtClub.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getString("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        clubMod(rsetClub.getInt("club_id")).
                        build();
            } else if (rset.getString("role").equals("event moderator")) {
                String sqlEvent = "SELECT * FROM event_moders WHERE user_id = ?";
                PreparedStatement stmtEvent = db.getConnection().prepareStatement(sqlEvent);
                stmtEvent.setInt(1, rset.getInt("user_id"));
                ResultSet rsetEvent = stmtEvent.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getString("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        eventMod(rsetEvent.getInt("event_id")).
                        build();
            } else if (rset.getString("role").equals("news moderator")) {
                String sqlNews = "SELECT * FROM news_moders WHERE user_id = ?";
                PreparedStatement stmtNews = db.getConnection().prepareStatement(sqlNews);
                stmtNews.setInt(1, rset.getInt("user_id"));
                ResultSet rsetNews = stmtNews.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getString("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        newsMod(rsetNews.getInt("news_id")).
                        build();
            } else {
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getString("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        build();
            }
        }
        return user;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users(user_id,fname, lname, email, password, role, year, major, group)" +
                "VALUES(?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getFname());
            stmt.setString(3, user.getLname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
            stmt.setString(7, user.getYear());
            stmt.setString(8, user.getMajor());
            stmt.setString(9, user.getGroup());

            stmt.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

        String sql = "update users set  fname = ?, lname = ?, email = ?,password = ?, year = ?, major = ?, group = ?  where user_id = ?";
        PreparedStatement stmt = null;
        try {

            stmt = db.getConnection().prepareStatement(sql);

            stmt.setString(1, user.getFname());
            stmt.setString(2, user.getLname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getYear());
            stmt.setString(6, user.getMajor());
            stmt.setString(7, user.getGroup());
            stmt.setInt(8, user.getId());
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try {
            //Statement stmt = db.getConnection().createStatement();
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            //ResultSet rs = stmt.executeQuery(sql);
            pstmt.setInt(1, user.getId());
            pstmt.execute();


        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }

    }

    @Override
    public List getAll() {
        ArrayList<User> userList = new ArrayList();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet rset = statement.executeQuery("SELECT * FROM users where user_id<>1");
            ResultSetMetaData metaData = rset.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            User u;
            while (rset.next()) {
                if (rset.getString("role").equals("club moderator")) {
                    String sqlClub = "SELECT * FROM club_moders WHERE user_id = ?";
                    PreparedStatement stmtClub = db.getConnection().prepareStatement(sqlClub);
                    stmtClub.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetClub = stmtClub.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            clubMod(rsetClub.getInt("club_id")).
                            build();
                    userList.add(u);

                } else if (rset.getString("role").equals("event moderator")) {
                    String sqlEvent = "SELECT * FROM event_moders WHERE user_id = ?";
                    PreparedStatement stmtEvent = db.getConnection().prepareStatement(sqlEvent);
                    stmtEvent.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetEvent = stmtEvent.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            eventMod(rsetEvent.getInt("event_id")).
                            build();
                    userList.add(u);
                } else if (rset.getString("role").equals("news moderator")) {
                    String sqlNews = "SELECT * FROM news_moders WHERE user_id = ?";
                    PreparedStatement stmtNews = db.getConnection().prepareStatement(sqlNews);
                    stmtNews.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetNews = stmtNews.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            newsMod(rsetNews.getInt("news_id")).
                            build();
                    userList.add(u);
                } else {
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            build();
                    userList.add(u);

                }
            }
            rset.close();
            statement.close();

            return userList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User u = null;
        try {
            //Statement stmt = db.getConnection().createStatement();
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            //ResultSet rs = stmt.executeQuery(sql);
            pstmt.setInt(1, id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                if (rset.getString("role").equals("club moderator")) {
                    String sqlClub = "SELECT * FROM club_moders WHERE user_id = ?";
                    PreparedStatement stmtClub = db.getConnection().prepareStatement(sqlClub);
                    stmtClub.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetClub = stmtClub.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            clubMod(rsetClub.getInt("club_id")).
                            build();
                } else if (rset.getString("role").equals("event moderator")) {
                    String sqlEvent = "SELECT * FROM event_moders WHERE user_id = ?";
                    PreparedStatement stmtEvent = db.getConnection().prepareStatement(sqlEvent);
                    stmtEvent.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetEvent = stmtEvent.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            eventMod(rsetEvent.getInt("event_id")).
                            build();
                } else if (rset.getString("role").equals("news moderator")) {
                    String sqlNews = "SELECT * FROM news_moders WHERE user_id = ?";
                    PreparedStatement stmtNews = db.getConnection().prepareStatement(sqlNews);
                    stmtNews.setInt(1, rset.getInt("user_id"));
                    ResultSet rsetNews = stmtNews.executeQuery();
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            newsMod(rsetNews.getInt("news_id")).
                            build();
                } else {
                    u = new User.Builder().setUser(
                            rset.getString("fname"),
                            rset.getString("lname"),
                            rset.getString("email"),
                            rset.getString("password"),
                            rset.getString("role"),
                            rset.getString("year"),
                            rset.getString("major"),
                            rset.getString("group")).
                            withId(rset.getInt("user_id")).
                            build();
                }
            }
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
        return u;
    }
}
