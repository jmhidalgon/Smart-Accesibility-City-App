package sacapp.android.jmhidalgo.smartaccesibilitycityapp;


import org.junit.Assert;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.CommentService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comments;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class CommentAccessTest {

    private CommentService commentService = API.getApi().create(CommentService.class);

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
        //String id, String userName, String entityId, int rating, String content
        Comment c = new Comment("", "", entities[0].getId(), 4, "Contenido");

        final Call<Comment> commentCall = commentService.register(c);
        commentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                int httpcode = response.code();
                Assert.assertEquals(httpcode, API.OK);

                latch2.countDown();
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

        final CountDownLatch latch3 = new CountDownLatch(1);

        Call<Comments> commentsCall = commentService.getComments(entities[0].getId());
        commentsCall.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                int httpcode = response.code();

                Comments comments = response.body();

                Assert.assertNotNull(comments);
                Assert.assertEquals(httpcode, API.OK);

                latch3.countDown();
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {

            }
        });

    }

}
