package com.example.orders_management.Data_Access;

import com.example.orders_management.Connection.ConnectionFactory;
import com.example.orders_management.Model.Client;
import com.example.orders_management.Model.Product;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

/**
 * This class contains the Data Access Object methods
 * @param <T> represents the Class of the object on which are performed
 *           the operations, it can be one of the classes Client, Product or Object
 */
public class AbstractDAO<T>{
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * This method creates the SQL query for any simple
     * selection operation performed
     * @param field represents the String field of data based on which the selection will be done
     * @return It returns a string based on the SQL needed operation
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * This method creates the SQL query that is to be used for an inserting operation
     * into the DataBase
     * @param fields represents a list of the String fields which
     *               are to be inserted into the DataBase
     * @return it returns a String containing the SQL operation commands needed
     */
    private String createInsertQuery(List<String> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO public.");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for (int i = 0; i < fields.size(); i++) {
            if(i == fields.size()-1){
                sb.append(fields.get(i));
            }else{
                sb.append(fields.get(i));
                sb.append(",");
            }
        }
        sb.append(") VALUES (");
        for (int i = 0; i < fields.size(); i++) {
            if(i == fields.size()-1){
                sb.append("?");
            }else{
                sb.append("?");
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
        // example "INSERT INTO client (idclient,name,email,phone,address) VALUES (?,?,?,?,?)";
    }

    /**
     * This method creates the SQL query that is to be used for an deleting operation
     * from the DataBase
     * @param field represents the String field of data based on which the deletion will be done
     * @return it returns a String containing the SQL operation commands needed
     */
    private String createDeleteQuery(String field){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
        // example "DELETE FROM client where idclient = ?"
    }
    private String createUpdateQuery(List<String> fields, String id){
        /*
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        sb.append(field);
        sb.append(" =? WHERE ");
        sb.append(id);
        sb.append(" =?");
        return sb.toString();
        */
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE public.");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for (int i = 0; i < fields.size(); i++) {
            if(i == fields.size()-1){
                sb.append(fields.get(i));
                sb.append("=?");
            }else{
                sb.append(fields.get(i));
                sb.append("=?, ");
            }
        }
        sb.append(" WHERE ");
        sb.append(id);
        sb.append(" =?");
        return sb.toString();
        //example "UPDATE product SET stock =? WHERE id =?";
    }

    /**
     * This method retrieves an desired object from the DataBase.
     * The operation is performed based on its id.
     * @param id represents the id of the object that is searched for
     * @return it returns the object from the DataBase that was searched
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method retrieves all the elements from a DataBase table
     * @return it returns a list of the instances from the DataBase
     */
    public List<T> findAll(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        String query = sb.toString();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "AbstractDAO:findAll method  " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * This method creates a list based on the instances that it receives
     * from the result set.
     * @param resultSet represent the result of a previous SQL operation.
     *                  From its elements is formed the desired list
     * @return it returns a list of various elements based on the result set.
     * It can be of Clients, Products or Orders
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }
    private List<T> createObjectsList(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);

                    for(Method method : createSettersList(instance)){
                        if(method.getName().endsWith(fieldName)){
                            method.invoke(instance, value);
                        }
                    }

                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * This method creates a list of all the existing fields of an object given.
     * @param object represents the object of which fields we want to list.
     *               It can be of class Client, Product or Order
     * @return it returns a list of type String with all the fields of the Object
     */
    private List<String> createFieldsList(T object){
        List<String> fields = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);
                fields.add(field.getName());
                System.out.println("\n" + field.getName());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }

    /**
     * This method creates a list of all the existing values of the
     * fields of an object given.
     * @param object represents the object of which field values we want to list.
     *               It can be of class Client, Product or Order
     * @return it returns a list of type String with all the field values of the Object
     */
    private List<Object> listValuesOfFields(T object){
        List<Object> fieldsValues = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);
                fieldsValues.add(value);
                System.out.println("\n" + value);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldsValues;
    }
    private List<Method> createSettersList(T object){
        List<Method> methods = new ArrayList<>();
        Method[] aux = object.getClass().getDeclaredMethods();
        for(Method met:aux){
            if(met.getName().startsWith("set")){
                methods.add(met);
                System.out.println("Setter: " + met.toString() + "\n");
            }
        }
        return methods;
    }

    public void generateContent(List<T> list, TableView<T> tbv) {
        // Create two columns
        TableColumn<T, String> cl1 = new TableColumn<>("name");
        cl1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<T, String> cl2 = new TableColumn<>("salary");
        cl2.setCellValueFactory(new PropertyValueFactory<>("salary"));
        // Add two columns into TableView
        tbv.getColumns().add(cl1);
        tbv.getColumns().add(cl2);
        // Load objects into table
        for (T emp : list){
            tbv.getItems().add(emp);
        }
    }

    /**
     * This is a method that receives a list of objects
     * and generates the header of the table by extracting through reflection the
     * object properties and then populates the table with the values of the
     * elements from the list
     * @param list represents the given list of various classes.
     *             It can be Client, Product or Order.
     * @return it returns a TableView with tha data from the list
     */
    public void generateTable(List<T> list, TableView<T> tbv){
        ObservableList<T> obsList = FXCollections.observableArrayList();
        obsList.addAll(list);
        Object firstObject = (Object)list.get(0);

        int it = 0;
        for (Field field : firstObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                tbv.getColumns().get(it).setEditable(true);
                tbv.getColumns().get(it).setText(fieldName);
                tbv.getColumns().get(it).setCellValueFactory(new PropertyValueFactory<>(fieldName));
                it++;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        tbv.setItems(obsList);
        tbv.refresh();
    }

    /**
     * This method inserts an object into the DataBase
     * @param objectToInsert represents the object that is to be inserted,
     *                       it can be of class Client, Product or Order
     * @return it returns 1 if executed successfully,
     * and -1 otherwise
     */
    public int insert(T objectToInsert) {
        List<String> fields = createFieldsList(objectToInsert);
        String query = createInsertQuery(fields);
        List<Object> fieldsValues = listValuesOfFields(objectToInsert);

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int ok = -1;

        try {
            insertStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for(int i = 1; i <= fieldsValues.size(); i++){
                insertStatement.setObject(i, fieldsValues.get(i-1));
            }
            insertStatement.executeUpdate();
            ok = 1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:insert method " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return ok;
    }

    public int update(T objectToInsert, String id, int idValue) {
        List<String> fields = createFieldsList(objectToInsert);
        String query = createUpdateQuery(fields, id);
        List<Object> fieldsValues = listValuesOfFields(objectToInsert);

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int ok = -1;

        try {
            updateStatement = dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int nrFields = fieldsValues.size();
            for(int i = 1; i <= nrFields; i++){
                updateStatement.setObject(i, fieldsValues.get(i-1));
            }
            updateStatement.setObject(nrFields + 1, idValue);
            updateStatement.executeUpdate();
            ok = 1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "AbstractDAO:update method " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return ok;
    }

    /**
     * This method deletes an object from the DataBase, it can be of class
     * Client, Product or Order
     * @param id represents the id of the object that is to be deleted
     * @return it returns 1 if executed successfully and 0 otherwise
     */
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id" + type.getSimpleName());

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            return 1;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "AbstractDAO:delete method " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

}
