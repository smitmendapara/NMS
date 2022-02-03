package com.motadata.kernel.dao;

import com.motadata.kernel.util.CommonConstant;

import com.motadata.kernel.util.Logger;

import java.sql.*;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

public class DataAccess
{
    private static String DATABASE_URL = CommonConstant.DATABASE_URL;

    private static String DATABASE_USERNAME = CommonConstant.DATABASE_USERNAME;

    private static String DATABASE_PASSWORD = CommonConstant.DATABASE_PASSWORD;

    private static String selectDiscover = "SELECT * FROM TB_DISCOVER WHERE IP = ?";

    private static String insertDataDump = "INSERT INTO TB_DATADUMP(ID, IP, PACKET, MEMORY, DEVICE, CURRENTTIME) VALUES(?, ?, ?, ?, ?, ?)";

    private static String updateMonitor = "UPDATE TB_MONITOR SET RESPONSE = ?, STATUS = ?, CURRENTTIME = ? WHERE IP = ?";

    private static String updateResult = "UPDATE TB_RESULT SET RESPONSE = ?, STATUS = ?, CURRENTTIME = ? WHERE IP = ?";

    private static Connection connection = null;

    private static final DataAccess _dao = new DataAccess();

    private static final Logger _logger = new Logger();

    public Connection getConnection()
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return connection;
    }

    public boolean configureDB(HashMap<Short, Object> properties)
    {
        boolean status = true;

        String columns = "*";

        String update_columns = "";

        String condition = "";

        if (properties.containsKey(CommonConstant_DAO.COLUMN_NAME))
        {
           columns = String.valueOf(properties.remove(CommonConstant_DAO.COLUMN_NAME));
        }

        if (properties.containsKey(CommonConstant_DAO.UPDATE_COLUMN_NAME))
        {
            update_columns = String.valueOf(properties.remove(CommonConstant_DAO.UPDATE_COLUMN_NAME));
        }

        if (properties.containsKey(CommonConstant_DAO.DATABASE_CONDITION))
        {
            condition = String.valueOf(properties.remove(CommonConstant_DAO.DATABASE_CONDITION));
        }

        short db_operation = Short.parseShort(String.valueOf(properties.get(CommonConstant_DAO.DATABASE_OPERATION)));

        connection = (Connection) properties.get(CommonConstant_DAO.DATABASE_CONNECTION);

        String tableName = String.valueOf(properties.get(CommonConstant_DAO.TABLE_NAME));

        HashMap<String, ArrayList<String>> tableData;

        try
        {
            switch (db_operation)
            {
                case CommonConstant_DAO.SELECT : properties.put(CommonConstant_DAO.RESULT_SET, executeSELECT(connection, tableName, condition, columns));

                                                 break;

                case CommonConstant_DAO.INSERT : tableData = (HashMap<String, ArrayList<String>>) properties.get(CommonConstant_DAO.TABLE_DATA);

                                                 if (connection != null)
                                                 {
                                                     for (String table : tableData.keySet())
                                                     {
                                                         for (String tableRow : tableData.get(table))
                                                         {
                                                             properties.put(CommonConstant_DAO.COLUMN_AUTO_ID, executeINSERT(connection, table, tableRow));
                                                         }
                                                     }
                                                 }

                                                 break;

                case CommonConstant_DAO.UPDATE : properties.put(CommonConstant_DAO.QUERY_STATUS, executeUPDATE(connection, tableName, condition, update_columns));

                                                 break;

                case CommonConstant_DAO.DELETE : executeDELETE(connection, tableName, condition);

                                                 break;
            }

        }
        catch (Exception exception)
        {
            exception.printStackTrace();

            status = false;
        }

        return status;
    }

    private void executeDELETE(Connection connection, String tableName, String condition)
    {
        String query;

        Statement statement = null;

        try
        {
            if (!connection.isClosed())
            {
                statement = connection.createStatement();

                if (statement != null)
                {
                    query = "DELETE FROM " + tableName + " " + condition;

                    statement.executeUpdate(query);

                    statement.close();
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("delete query invalid", exception);
        }
        finally
        {
            try
            {
                if (statement != null && !statement.isClosed())
                {
                    statement.close();
                }
            }
            catch (Exception ignored)
            {

            }
        }
    }

    private Object executeUPDATE(Connection connection, String tableName, String condition, String update_columns)
    {
        boolean result = false;

        Statement statement = null;

        String query;

        try
        {
            if (!connection.isClosed())
            {
                statement = connection.createStatement();

                if (statement != null)
                {
                    query = "UPDATE " + tableName + " SET " + update_columns + " " + condition;

                    statement.executeUpdate(query);

                    result = true;

                    statement.close();
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("update query invalid", exception);
        }
        finally
        {
            try
            {
                if (statement != null && !statement.isClosed())
                {
                    statement.close();
                }
            }
            catch (Exception ignored)
            {

            }
        }

        return result;
    }

    private Object executeINSERT(Connection connection, String tableName, String tableRow)
    {
        String query;

        Statement statement = null;

        ResultSet resultSet;

        int generatedId = 0;

        int affectedRows;

        try
        {
            if (!connection.isClosed())
            {
                statement = connection.createStatement();

                if (statement != null)
                {

                    query = "INSERT INTO " + tableName + " VALUES (" + tableRow + ")";

                    affectedRows = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

                    if (affectedRows != 0)
                    {
                        resultSet = statement.getGeneratedKeys();

                        if (resultSet.next())
                        {
                            generatedId = resultSet.getInt(1);
                        }
                    }

                    statement.close();
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("insert query invalid!", exception);
        }
        finally
        {
            try
            {
                if (statement != null && !statement.isClosed())
                {
                    statement.close();
                }
            }
            catch (Exception ignored)
            {

            }
        }

        return generatedId;
    }

    private Object executeSELECT(Connection connection, String tableName, String condition, String columns)
    {
        List<HashMap<String, Object>> table = null;

        HashMap<String, Object> row;

        Statement statement = null;

        ResultSetMetaData metaData;

        try
        {
            if (!connection.isClosed())
            {
                statement = connection.createStatement();

                if (statement != null)
                {
                    ResultSet resultSet = statement.executeQuery("SELECT " + columns + " FROM " + tableName + " " + condition);

                    while (resultSet.next())
                    {
                        if (table == null)
                        {
                            table = new ArrayList<>();
                        }

                        row = new HashMap<>();

                        metaData = resultSet.getMetaData();

                        if (metaData != null)
                        {
                            for (int index = 1; index < metaData.getColumnCount() + 1; index++)
                            {
                                if (resultSet.getObject(index) != null && resultSet.getObject(index).toString().trim().length() > 0 && !resultSet.getObject(index).toString().equalsIgnoreCase(CommonConstant.NULL))
                                {
                                    row.put(metaData.getColumnName(index), resultSet.getObject(index));
                                }
                            }

                            table.add(row);
                        }

                    }

                    statement.close();

                    connection.close();
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("select query invalid...", exception);
        }
        finally
        {
            try
            {
                statement.close();
            }
            catch (Exception ignored)
            {

            }
        }

        return table;
    }

    public static ResultSet getReDiscoveryData(String ip)
    {
        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try
        {
            connection = _dao.getConnection();

            preparedStatement = connection.prepareStatement(selectDiscover);

            preparedStatement.setString(1, ip);

            resultSet = preparedStatement.executeQuery();

            return resultSet;
        }
        catch (Exception exception)
        {
            _logger.error("not find re discover table data!!", exception);
        }

        return resultSet;
    }

    private static void updateData(PreparedStatement preparedStatement, String response, String ipStatus, String timestamp, String ip)
    {
        try
        {
            preparedStatement.setString(1, response);

            preparedStatement.setString(2, ipStatus);

            preparedStatement.setString(3, timestamp.substring(0, 16));

            preparedStatement.setString(4, ip);
        }
        catch (Exception exception)
        {
            _logger.warn("data not updated!");
        }
    }

    public static boolean enterReMonitorData(String name, String ip, String discoveryUsername, String discoveryPassword, String deviceType, String response, String ipStatus, String timestamp)
    {
        boolean result = true;

        PreparedStatement preparedStatement = null;

        try
        {
            Class.forName("org.h2.Driver");

            connection = _dao.getConnection();

            preparedStatement = connection.prepareStatement(updateMonitor);

            updateData(preparedStatement, response, ipStatus, timestamp, ip);

            if (preparedStatement.execute())
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("monitor data not updated properly!", exception);

            result = false;
        }

        return result;
    }

    public static boolean enterReResultTableData(String name, String ip, String discoveryUsername, String discoveryPassword, String deviceType, String response, String ipStatus, String timestamp)
    {
        boolean result = true;

        PreparedStatement preparedStatement = null;

        try
        {
            connection = _dao.getConnection();

            preparedStatement = connection.prepareStatement(updateResult);

            updateData(preparedStatement, response, ipStatus, timestamp, ip);

            if (preparedStatement.execute())
            {
                return true;
            }

        }
        catch (Exception exception)
        {
            _logger.error("not updated data into result table!", exception);

            result = false;
        }

        return result;
    }

    public static boolean enterDataDump(int id, String ip, String packet, Double memory, String deviceType, String time)
    {
        boolean status = true;

        PreparedStatement preparedStatement = null;

        try
        {
            connection = _dao.getConnection();

            preparedStatement = connection.prepareStatement(insertDataDump);

            preparedStatement.setInt(1, id);

            preparedStatement.setString(2, ip);

            preparedStatement.setString(3, packet);

            preparedStatement.setDouble(4, memory);

            preparedStatement.setString(5, deviceType);

            preparedStatement.setString(6, time.substring(0, 16));

            if (preparedStatement.execute())
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("not inserted data into tb_dataDump!", exception);

            status = false;
        }

        return status;
    }
}
