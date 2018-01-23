package sacapp.android.jmhidalgo.smartaccesibilitycityapp.util;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

public class SACAPPControl
{
    public enum AppMode {
        USER_MODE,
        ENTITY_MODE
    }

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

    public static Entity getEntity()
    {
        return entity;
    }

    public static void setEntity(Entity entityApp)
    {
        entity = entityApp;
    }
}
