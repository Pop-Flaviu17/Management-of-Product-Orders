package com.example.orders_management.Business_Logic;

public interface Validator<T> {
    /**
     * This represents an interface which has the role to validate
     * different fields of class Client
     * @param t Represents the object of class Client which is
     *          to be validated
     */
    public void validate(T t);
}

