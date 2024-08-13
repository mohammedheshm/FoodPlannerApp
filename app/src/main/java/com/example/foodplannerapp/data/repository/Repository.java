package com.example.foodplannerapp.data.repository;

import android.content.Context;
import android.util.Log;

import com.example.foodplannerapp.data.fireasestore.BackupManager;
import com.example.foodplannerapp.data.model.User;

import com.example.foodplannerapp.data.network.ApiCalls;
import com.example.foodplannerapp.data.network.Network;
import com.example.foodplannerapp.data.room.RoomDatabase;
import com.example.foodplannerapp.data.sharedpref.SharedManager;

//Handle  Network , RoomDatabase , And SharedPref Manager functions
public class Repository {
    private static final String TAG = "Repository";

    public static final int DELETE_FAV = 1;
    public static final int DELETE_PLAN = 2;
    public static final int DELETE_PLAN_AND_FAV = 3;
    private final ApiCalls apiCalls;
    private final RoomDatabase roomDatabase;
    private final BackupManager backupManager;
    private final SharedManager sharedManager;
    public static Repository repository = null;

    public static Repository getInstance(Context context) {
        if (repository == null)
            repository = new Repository(context);
        return repository;
    }

    private Repository(Context context) {
        apiCalls = Network.apiCalls;
        roomDatabase = RoomDatabase.getInstance(context);
        sharedManager = SharedManager.getInstance(context);
        backupManager = BackupManager.getInstance(sharedManager);
    }

    //SharedPreference
    public boolean isUser() {return sharedManager.isUser();}
    public void saveUser(User user) {sharedManager.saveUser(user);}
    public User getUser() {return sharedManager.getUser();}
    public String[] getList(String type) {return sharedManager.getList(type);}
    public boolean isFirstEntrance() {return sharedManager.isFirstEntrance();}
    public void saveEntrance() {sharedManager.saveEntrance();}

}
