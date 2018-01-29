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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class UserAccessTest {

    private final String ROL = "ROL_USER";
    private UserService userService = API.getApi().create(UserService.class);


    @Test
    public void login() throws InterruptedException {
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
                latch.countDown();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                assertNotNull(null);
            }
        });
        latch.await();
    }
}
