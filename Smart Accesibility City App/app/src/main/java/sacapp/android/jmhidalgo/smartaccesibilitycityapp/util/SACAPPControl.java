package sacapp.android.jmhidalgo.smartaccesibilitycityapp.util;

import android.content.Context;

import java.util.ArrayList;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;

public class SACAPPControl
{
    public enum AppMode {
        USER_MODE,
        ENTITY_MODE
    }

    private static Context currentContext;

    private static ArrayList<Visit> entityVisits = new ArrayList<Visit>();
    private static ArrayList<Entity> entityToVisit = new ArrayList<Entity>();

    public static String firebaseToken = "";

    private static User user;
    private static Entity entity;


    public static User getUser()
    {
        return user;
    }

    public static void setUser(User userApp)
    {
        user = userApp;
    }

    public static ArrayList<Visit> getEntityVisits() {
        return entityVisits;
    }

    public static void setEntityVisits(ArrayList<Visit> entityVisits) {
        SACAPPControl.entityVisits = entityVisits;
    }

    public static Entity getEntity()
    {
        return entity;
    }

    public static void setEntity(Entity entityApp)
    {
        entity = entityApp;
    }

    public static void addAVisitToEntity(Visit v){ entityVisits.add(v); }

    public static Context getCurrentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {
        SACAPPControl.currentContext = currentContext;
    }

    public static ArrayList<Entity> getEntityToVisit() {
        return entityToVisit;
    }

    public static void setEntityToVisit(ArrayList<Entity> entityToVisit) {
        SACAPPControl.entityToVisit = entityToVisit;
    }

    public static void addEntityToVisit(Entity entity){
        entityToVisit.add(entity);
    }
}
