package sacapp.android.jmhidalgo.smartaccesibilitycityapp;


import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.TokenService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class EntityAccessTest {

    private final String ROL = "ROL_ENTITY";
    private EntityService entityService = API.getApi().create(EntityService.class);

    //@Test
    public void register() throws InterruptedException {
        // (String id, String entityname, String email, String pass, String rol, String image, String adress, double longitud, double latitud, String website)
        final Entity e = new Entity("", "Entity", "entity@test.com", "test", ROL, "", "adress",  0, 0, "www.entity.com");

        final CountDownLatch latch = new CountDownLatch(1);

        Call<Entity> entityCall = entityService.register(e);
        entityCall.enqueue(new Callback<Entity>() {
            @Override
            public synchronized void onResponse(Call<Entity> call, Response<Entity> response) {
                int httpcode = response.code();

                Entity entity = response.body();

                assertEquals(e.getEntityname(), entity.getEntityname());
                assertEquals(e.getAdress(), entity.getAdress());
                assertEquals(e.getPass(), entity.getPass());
                assertEquals(e.getEmail(), entity.getEmail());
                assertEquals(e.getWebsite(), entity.getWebsite());
                assertEquals(httpcode, API.OK);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                assertNotNull(null);
            }
        });
        latch.await();
    }


    @Test
    public void loginAndConsultById() throws InterruptedException {
        // User(String id, String name, String surname, String reduceMovility, String email, String pass, String rol, String image)
        String email = "entity@test.com";
        String pass = "test";
        // Retrofit want the variable like this
        final Entity[] entities = new Entity[1];

        final CountDownLatch latch = new CountDownLatch(1);

        Call<Entity> entityCall = entityService.login(email, pass);
        entityCall.enqueue(new Callback<Entity>() {
            @Override
            public synchronized void onResponse(Call<Entity> call, Response<Entity> response) {
                int httpcode = response.code();

                entities[0] = response.body();

                Assert.assertNotNull(entities[0]);
                Assert.assertEquals(httpcode, API.OK);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {
                Assert.assertNotNull(null);
            }
        });
        latch.await();

        final CountDownLatch latch2 = new CountDownLatch(1);

        Call<Entities> entitiesCall = entityService.getEntityById(entities[0].getId());
        entitiesCall.enqueue(new Callback<Entities>() {
            @Override
            public void onResponse(Call<Entities> call, Response<Entities> response) {
                int httpcode = response.code();

                Entities ents = response.body();
                Entity e = ents.getEntities().get(0);

                Assert.assertEquals(e.getEntityname(), entities[0].getEntityname());
                Assert.assertEquals(e.getAdress(), entities[0].getAdress());
                Assert.assertEquals(e.getEmail(), entities[0].getEmail());
                Assert.assertEquals(e.getWebsite(), entities[0].getWebsite());
                Assert.assertEquals(httpcode, API.OK);

                latch2.countDown();
            }

            @Override
            public void onFailure(Call<Entities> call, Throwable t) {

            }
        });
    }

    @Test
    public void getEntities() throws InterruptedException {
        String email = "user@test.com";
        String pass = "test";
        final String[] userToken = {""};

        final CountDownLatch latch = new CountDownLatch(1);

        TokenService tokenService = API.getApi().create(TokenService.class);
        Call<Token> tokenCall = tokenService.login(email, pass, true);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public synchronized void onResponse(Call<Token> call, Response<Token> response) {
                int httpcode = response.code();

                Token t = response.body();
                Assert.assertEquals(httpcode, API.OK);
                userToken[0] = t.getStringToken();
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Assert.assertNotNull(null);
            }
        });
        latch.await();

        final CountDownLatch latch2 = new CountDownLatch(1);

        Call<Entities> entitiesCall = entityService.getEntities(userToken[0]);
        entitiesCall.enqueue(new Callback<Entities>() {
            @Override
            public void onResponse(Call<Entities> call, Response<Entities> response) {
                int httpcode = response.code();

                Entities ents = response.body();
                Assert.assertEquals(httpcode, API.OK);

                latch2.countDown();
            }

            @Override
            public void onFailure(Call<Entities> call, Throwable t) {

            }
        });
    }
}
