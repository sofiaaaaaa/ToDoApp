package sample.Database;

import com.mysql.cj.protocol.Resultset;
import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DBHandler extends Configs {
    Connection dbConn;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        String connString  = "jdbc:mysql://" + dbHost +":" + dbPort + "/" + dbName+"?characterEncoding=UTF-8&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConn = DriverManager.getConnection(connString, dbUser, dbPassword);
        return dbConn;
    }


    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE + " ("
                + Const.USERS_FIRSTNAME
                + " ," + Const.USERS_LASTNAME
                + " ," + Const.USERS_PASSWORD
                + " ," + Const.USERS_USERNAME
                + " ," + Const.USERS_GENDER
                + " ," + Const.USERS_LOCATION
                + " ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getUserName());
            preparedStatement.setString(5, user.getGender());
            preparedStatement.setString(6, user.getLocation());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getTasksByUser(int userId){
        ResultSet resultTasks = null;

        String query = "SELECT * FROM "+Const.TASKS_TABLE + " WHERE "
                + Const.USERS_ID + "=? ORDER BY DATECREATED DESC" ;
        try {
            PreparedStatement preparedStatement= getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultTasks = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTasks;
    }

    public ResultSet getUser(User user){
        ResultSet resultSet = null;

        if(!user.getUserName().equals("") || !user.getPassword().equals("")){
            String query = "SELECT * FROM "+Const.USERS_TABLE + " WHERE "
                        + Const.USERS_USERNAME + "=?" + " AND "+ Const.USERS_PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Please enter your credential!");
        }
        return resultSet;
    }

    public int getAllTasks(int userId) throws SQLException, ClassNotFoundException {

        String query = "SELECT COUNT(*) FROM "+Const.TASKS_TABLE + " WHERE "
                        + Const.USERS_ID + " = ?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            return resultSet.getInt(1);
        }

        return resultSet.getInt(1);
    }

    public void deletetask(int userId, int taskId){
        String query = "DELETE FROM "+ Const.TASKS_TABLE + " WHERE "
                + Const.USERS_ID + "=?" + " AND "+ Const.TASKS_TASKID + "=?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertTask(Task task){
        String insert = "INSERT INTO " + Const.TASKS_TABLE + " ("
                + Const.USERS_ID
                + " ," + Const.TASKS_DATECREATED
                + " ," + Const.TASKS_DESCRIPTION
                + " ," + Const.TASKS_TASK
                + " ) VALUES ( ?, ?, ?, ? ) ";
        try {
            System.out.println(task.toString());
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDataecreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateTask(Task task) {
        String insert = "UPDATE " + Const.TASKS_TABLE
                + " SET "
                + Const.TASKS_TASK + "=?"
                + " ," + Const.TASKS_DESCRIPTION + "=?"
                + " ," + Const.TASKS_DATECREATED + "=?"
                + " WHERE "  + Const.USERS_ID + "=?"
                + " AND "+ Const.TASKS_TASKID + "=?";

        try {
            System.out.println(task.toString());
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, task.getTask());
            preparedStatement.setString(2, task.getDescription() );
            preparedStatement.setTimestamp(3, task.getDataecreated());
            preparedStatement.setInt(4, task.getUserId());
            preparedStatement.setInt(5, task.getTaskId());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
