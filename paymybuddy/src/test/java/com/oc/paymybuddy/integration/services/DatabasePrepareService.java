package com.oc.paymybuddy.integration.services;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.oc.paymybuddy.integration.config.DatabaseTestConfig;


public class DatabasePrepareService {

    DatabaseTestConfig dataBaseTestConfig = new DatabaseTestConfig();

    @Test
    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

//            //clear entries;
//            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }
    


}
