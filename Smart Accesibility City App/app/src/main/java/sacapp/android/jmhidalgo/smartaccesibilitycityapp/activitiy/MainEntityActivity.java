package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.CommentService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterAccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterComment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.CommentItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comments;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

/** Main Entity activity
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class MainEntityActivity extends AppCompatActivity {

    private TextView textView;
    private ListView listViewComments;
    private FloatingActionButton fabSettings;
    private FloatingActionButton fabNotifications;
    private RatingBar ratingBar;

    private ArrayList<CommentItem> commentItems;
    private AdapterComment adapterComment;
    private List<Comment> commentsEntity;

    private double acumulateRating;
    private double globalRating;

    /** OnCreate inhered method
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entity);

        // Getting the view components
        textView = (TextView) findViewById(R.id.textViewEntityTittle);
        listViewComments = (ListView) findViewById(R.id.listViewComments);
        fabNotifications = (FloatingActionButton) findViewById(R.id.fabNotification);
        fabSettings = (FloatingActionButton) findViewById(R.id.fabSettings);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        commentItems = new ArrayList<CommentItem>();
        adapterComment = new AdapterComment (MainEntityActivity.this, R.layout.comment_item, commentItems);
        listViewComments.setAdapter(adapterComment);

        globalRating = 0;

        fillEntityData();
        getCommentsByEntityAndFillListView();
    }

    /** Request the comments by entity ID and fill the list view
     */
    private void getCommentsByEntityAndFillListView(){

        if(SACAPPControl.getEntity() != null){
            CommentService commentService = API.getApi().create(CommentService.class);
            Call<Comments> commentsCall = commentService.getComments(SACAPPControl.getEntity().getId());

            commentsCall.enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    int httpCode = response.code();

                    switch(httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(MainEntityActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(MainEntityActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                            break;
                        case API.OK:
                            Comments comments = response.body();
                            commentsEntity = comments.getComments();
                            if(commentsEntity != null){
                                fillListViewComments();
                            }
                            // fillListViewAccessResource();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Comments> call, Throwable t) {
                    Toast.makeText(MainEntityActivity.this, "Error de conexion Comentarios", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /** Method to fill ListView with all the entity comments
     *
     */
    private void fillListViewComments(){

        if(commentsEntity != null){
            acumulateRating = 0;
            for(int i=0; i<commentsEntity.size(); ++i){
                commentItems.add(new CommentItem(commentsEntity.get(i).getUserName(), SACAPPControl.getEntity().getId(),
                        commentsEntity.get(i).getContent(), commentsEntity.get(i).getRating()));
                acumulateRating += commentsEntity.get(i).getRating();
            }
            globalRating = acumulateRating / commentsEntity.size();
            ratingBar.setRating((float)globalRating);
            adapterComment.notifyDataSetChanged();
        }
    }

    private void fillEntityData(){

        if(SACAPPControl.getEntity() != null){
            textView.setText(SACAPPControl.getEntity().getEntityname());
        }
    }

    @Override
    protected void onResume() {

        /*if(SACAPPControl.getEntity() != null){
            commentItems.clear();
            adapterComment.notifyDataSetChanged();
            getCommentsByEntityAndFillListView();
        }*/

        super.onResume();
    }
}
