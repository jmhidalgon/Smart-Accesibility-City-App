package sacapp.android.jmhidalgo.smartaccesibilitycityapp;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserAccessTest {

    private final String ROL = "ROL_USER";
    private UserService userService = API.getApi().create(UserService.class);

    //@Test
    public void register() throws InterruptedException {
        // User(String id, String name, String surname, String reduceMovility, String email, String pass, String rol, String image)
        final User u = new User("", "Usuario", "de prueba", "reduccion de movilidad", "user@test.com", "test",  ROL, "");

        final CountDownLatch latch = new CountDownLatch(1);

        Call<User> userCall = userService.register(u);
        userCall.enqueue(new Callback<User>() {
            @Override
            public synchronized void onResponse(Call<User> call, Response<User> response) {
                int httpcode = response.code();

                User user = response.body();

                assertEquals(u.getName(), user.getName());
                assertEquals(u.getSurname(), user.getSurname());
                assertEquals(u.getPass(), user.getPass());
                assertEquals(u.getEmail(), user.getEmail());
                assertEquals(u.getReduceMovility(), user.getReduceMovility());
                assertEquals(httpcode, API.OK);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                assertNotNull(null);
            }
        });
        latch.await();
    }

    //@Test
    public void loginAndConsultById() throws InterruptedException {
        // User(String id, String name, String surname, String reduceMovility, String email, String pass, String rol, String image)
        String email = "user@test.com";
        String pass = "test";
        // Retrofit want the variable like this
        final User[] user = new User[1];

        final CountDownLatch latch = new CountDownLatch(1);

        Call<User> userCall = userService.login(email, pass);
        userCall.enqueue(new Callback<User>() {
            @Override
            public synchronized void onResponse(Call<User> call, Response<User> response) {
                int httpcode = response.code();

                user[0] = response.body();

                assertNotNull(user[0]);
                assertEquals(httpcode, API.OK);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                assertNotNull(null);
            }
        });
        latch.await();

        final CountDownLatch latch2 = new CountDownLatch(1);

        Call<Users> usersCall = userService.getUserById(user[0].getId());
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                int httpcode = response.code();

                Users users = response.body();
                User u = users.getUsers().get(0);

                assertEquals(u.getName(), user[0].getName());
                assertEquals(u.getSurname(), user[0].getSurname());
                assertEquals(u.getPass(), user[0].getPass());
                assertEquals(u.getEmail(), user[0].getEmail());
                assertEquals(u.getReduceMovility(), user[0].getReduceMovility());
                assertEquals(httpcode, API.OK);

                latch2.countDown();

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
