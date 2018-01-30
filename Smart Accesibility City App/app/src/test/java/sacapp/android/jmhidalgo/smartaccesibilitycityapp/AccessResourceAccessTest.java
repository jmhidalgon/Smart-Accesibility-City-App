package sacapp.android.jmhidalgo.smartaccesibilitycityapp;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.AccessResourceService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResources;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class AccessResourceAccessTest {

    private final String ROL = "ROL_USER";
    private AccessResourceService accessResourceService = API.getApi().create(AccessResourceService.class);

    //@Test
    public void register() throws InterruptedException {
        // User(String id, String name, String surname, String reduceMovility, String email, String pass, String rol, String image)
        String email = "entity@test.com";
        String pass = "test";
        // Retrofit want the variable like this
        final Entity[] entities = new Entity[1];

        final CountDownLatch latch = new CountDownLatch(1);

        EntityService entityService = API.getApi().create(EntityService.class);
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

        final AccessResource ar = new AccessResource("", "Rampa de acceso", entities[0].getId());

        Call<AccessResource> accessResourceCall = accessResourceService.register(ar);
        accessResourceCall.enqueue(new Callback<AccessResource>() {
            @Override
            public void onResponse(Call<AccessResource> call, Response<AccessResource> response) {
                int httpcode = response.code();

                AccessResource accessResource = response.body();

                Assert.assertEquals(accessResource.getEntityId(), ar.getEntityId());
                Assert.assertEquals(accessResource.getResourceName(), ar.getResourceName());
                Assert.assertEquals(httpcode, API.OK);

                latch2.countDown();
            }

            @Override
            public void onFailure(Call<AccessResource> call, Throwable t) {

            }
        });
    }

    @Test
    public void getAccessResourceByEntityId() throws InterruptedException {

        String email = "entity@test.com";
        String pass = "test";

        final Entity[] entities = new Entity[1];

        final CountDownLatch latch = new CountDownLatch(1);

        EntityService entityService = API.getApi().create(EntityService.class);
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

        Call<AccessResources> accessResourcesCall = accessResourceService.getResources(entities[0].getId());
        accessResourcesCall.enqueue(new Callback<AccessResources>() {
            @Override
            public void onResponse(Call<AccessResources> call, Response<AccessResources> response) {
                int httpcode = response.code();

                AccessResources accessResources = response.body();
                Assert.assertEquals(httpcode, API.OK);

                latch2.countDown();
            }

            @Override
            public void onFailure(Call<AccessResources> call, Throwable t) {

            }
        });
    }
}
