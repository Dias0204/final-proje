package kz.edu.astanait.controllers;

import kz.edu.astanait.JDBC.DB;
import kz.edu.astanait.interfaces.IController;
import kz.edu.astanait.models.User;

import javax.ws.rs.BadRequestException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserController implements IController<User> {
    private static DB db = new DB();

    public User checkLogin(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = db.getConnection().prepareStatement(sql);

        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rset = stmt.executeQuery();

        User user = null;

        if (rset.next()) {
            if (rset.getString("role").equals("club moderator")){
                String sqlClub = "SELECT * FROM club_moders WHERE id = ?";
                PreparedStatement stmtClub = db.getConnection().prepareStatement(sqlClub);
                stmtClub.setInt(1,rset.getInt("user_id"));
                ResultSet rsetClub = stmtClub.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getDate("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        clubMod(rsetClub.getInt("club_id")).
                        build();
            } else if (rset.getString("role").equals("event moderator")){
                String sqlEvent = "SELECT * FROM event_moders WHERE id = ?";
                PreparedStatement stmtEvent = db.getConnection().prepareStatement(sqlEvent);
                stmtEvent.setInt(1,rset.getInt("user_id"));
                ResultSet rsetEvent = stmtEvent.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getDate("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        eventMod(rsetEvent.getInt("event_id")).
                        build();
            } else if (rset.getString("role").equals("news moderator")){
                String sqlNews = "SELECT * FROM news_moders WHERE id = ?";
                PreparedStatement stmtNews = db.getConnection().prepareStatement(sqlNews);
                stmtNews.setInt(1,rset.getInt("user_id"));
                ResultSet rsetNews = stmtNews.executeQuery();
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getDate("year"),
                        rset.getString("major"),
                        rset.getString("group")).
                        withId(rset.getInt("user_id")).
                        newsMod(rsetNews.getInt("news_id")).
                        build();
            }else {
                user = new User.Builder().setUser(
                        rset.getString("fname"),
                        rset.getString("lname"),
                        rset.getString("email"),
                        rset.getString("password"),
                        rset.getString("role"),
                        rset.getDate("year"),
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
        String sql = "INSERT INTO users(fname, lname, email, password, role)" +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = db.getConnection().prepareStatement(sql);

            stmt.setString(1, user.getFname());
            stmt.setString(2, user.getLname());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(4, user.getRole());

            stmt.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

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
        return null;
    }

    @Override
    public User getById(int id) {
        return null;
    }
}
