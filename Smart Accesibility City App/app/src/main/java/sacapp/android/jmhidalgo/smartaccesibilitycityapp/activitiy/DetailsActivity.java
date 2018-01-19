package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.AccessResourceService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.CommentService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.VisitService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterAccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterComment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.CommentItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResources;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comments;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

public class DetailsActivity extends AppCompatActivity {

    private Entity entity;
    private List<AccessResource> accessResourcesEntity;
    private List<Comment> commentsEntity;

    private TextView nameTextView;
    private TextView addressTextView;

    private FloatingActionButton googlemapButton;
    private FloatingActionButton emailButton;
    private FloatingActionButton gotoButton;
    private FloatingActionButton commentButton;

    private ListView listViewAccessResource;
    private ArrayList<AccessItem> accesResourceItems;

    private ArrayList<CommentItem> commentItems;
    private ListView listViewComment;

    private AdapterComment adapterComment;
    // private ArrayAdapter<String> adapterAccessResource;
    private AdapterAccessItem adapterAccessResource;

    private RatingBar ratingBar;

    private double globalRating = 0;
    private double acumulateRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();
        if(intent.getParcelableExtra("Entity") != null ) {
            entity = (Entity)intent.getParcelableExtra("Entity");
        }

        listViewAccessResource = (ListView) findViewById(R.id.listViewResource);
        accesResourceItems = new ArrayList();

        // adapterAccessResource = new ArrayAdapter<String>(DetailsActivity.this, android.R.layout.simple_list_item_activated_1, accesResourceItems);
        adapterAccessResource = new AdapterAccessItem(DetailsActivity.this, accesResourceItems);
        listViewAccessResource.setAdapter(adapterAccessResource);

        listViewComment = (ListView) findViewById(R.id.listViewComments);
        commentItems = new ArrayList<CommentItem>();
        adapterComment = new AdapterComment (DetailsActivity.this, R.layout.comment_item, commentItems);
        listViewComment.setAdapter(adapterComment);

        getAccessResources();
        getComments();

        nameTextView = (TextView) findViewById(R.id.tvName);
        addressTextView = (TextView) findViewById(R.id.tvAddress);

        nameTextView.setText(entity.getEntityname());
        addressTextView.setText(entity.getAdress());

        ratingBar = (RatingBar) findViewById(R.id.ratingBarGlobal);

        googlemapButton = (FloatingActionButton) findViewById(R.id.fabGoogleMaps);
        googlemapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriBegin = "geo:12,34";
                String query = entity.getLatitud() + "," + entity.getLongitud() + "(" + entity.getEntityname() + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );

                // Uri gmmIntentUri = Uri.parse("geo:"+entity.getLatitud()+","+entity.getLongitud()+","+entity.getEntityname());

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");

                try {
                    startActivity(mapIntent);
                }catch (ActivityNotFoundException e) {
                    Toast.makeText(DetailsActivity.this, "Error. No se ha podido abrir google maps", Toast.LENGTH_LONG).show();
                }
            }
        });

        emailButton = (FloatingActionButton) findViewById(R.id.fabEmail);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:" +entity.getEmail() ;

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                }catch (ActivityNotFoundException e) {
                    Toast.makeText(DetailsActivity.this, "Error. No se ha podido abrir aplicacion de mensajeria", Toast.LENGTH_LONG).show();
                }

            }
        });

        gotoButton = (FloatingActionButton) findViewById(R.id.fabGoTo);
        gotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                Date date = new Date();

                String dateToday = dateFormat.format(date);

                if(entity != null && SACAPPControl.getUser() != null){
                    visitRegister(new Visit("", SACAPPControl.getUser().getId(), entity.getId(), dateToday));
                }
            }
        });

        commentButton = (FloatingActionButton) findViewById(R.id.fabComment);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = DetailsActivity.this;
                LayoutInflater inf = (LayoutInflater) DetailsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View v = inf.inflate(R.layout.comment_entry, null);

                final EditText inputText = v.findViewById(R.id.editTextCommentEntry);
                final RatingBar inputRating = v.findViewById(R.id.ratingBarEntry);

                final String[] content = new String[1];
                final int[] rating = new int[1];
                final boolean[] success = new boolean[1];
                final Comment[] newComment = new Comment[1];

                new AlertDialog.Builder(DetailsActivity.this)
                        .setTitle("Añadir comentario")
                        .setMessage("Por favor, introduzca sus facilidades para evitar las barreras arquitectonicas")
                        .setView(v)
                        .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                content[0] = (String)inputText.getText().toString();
                                rating[0] = (int)inputRating.getRating();

                                if (!content[0].equals(new String("")) || rating[0] > 0) {

                                    newComment[0] = new Comment("", SACAPPControl.getUser().getName() + " " + SACAPPControl.getUser().getSurname(),
                                            entity.getId(), rating[0], content[0]);

                                    registerComment(newComment[0]);
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();

            }
        });


        if(entity.getRol().equals("ROLE_PUBLIC")){
            emailButton.setVisibility(Button.INVISIBLE);
            gotoButton.setVisibility(Button.INVISIBLE);
        } else {
            emailButton.setVisibility(Button.VISIBLE);
            gotoButton.setVisibility(Button.VISIBLE);
        }
    }

    public void getAccessResources()
    {
        if(entity != null){
            AccessResourceService accessResourceService = API.getApi().create(AccessResourceService.class);
            Call<AccessResources> accessResourcesCall = accessResourceService.getResources(entity.getId());

            accessResourcesCall.enqueue(new Callback<AccessResources>() {
                @Override
                public void onResponse(Call<AccessResources> call, Response<AccessResources> response) {
                    int httpCode = response.code();

                    switch(httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(DetailsActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(DetailsActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                            break;
                        case API.OK:
                            AccessResources accessResources = response.body();
                            accessResourcesEntity = accessResources.getAccessResources();
                            fillListViewAccessResource();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<AccessResources> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Error de conexion Recursos", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void fillListViewAccessResource(){

        if(accessResourcesEntity != null){
            for(int i=0; i<accessResourcesEntity.size(); ++i){
                accesResourceItems.add(new AccessItem(accessResourcesEntity.get(i).getResourceName(), accessResourcesEntity.get(i).getEntityId()));
            }
            adapterAccessResource.notifyDataSetChanged();
        }
    }

    public void getComments()
    {
        if(entity != null){
            CommentService commentService = API.getApi().create(CommentService.class);
            Call<Comments> commentsCall = commentService.getComments(entity.getId());

            commentsCall.enqueue(new Callback<Comments>() {
                @Override
                public void onResponse(Call<Comments> call, Response<Comments> response) {
                    int httpCode = response.code();

                    switch(httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(DetailsActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(DetailsActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(DetailsActivity.this, "Error de conexion Comentarios", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void fillListViewComments(){

        if(commentsEntity != null){
            acumulateRating = 0;
            for(int i=0; i<commentsEntity.size(); ++i){
                commentItems.add(new CommentItem(commentsEntity.get(i).getUserName(), entity.getId(),
                        commentsEntity.get(i).getContent(), commentsEntity.get(i).getRating()));
                acumulateRating += commentsEntity.get(i).getRating();
            }
            globalRating = acumulateRating / commentsEntity.size();
            ratingBar.setRating((float)globalRating);
            adapterComment.notifyDataSetChanged();
        }
    }

    private void registerComment(final Comment comment){

        final Comment newComment = comment;

        CommentService commentService = API.getApi().create(CommentService.class);
        Call<Comment> commentCall = commentService.register(comment);
        final boolean[] success = new boolean[1];
        final String[] message = new String[1];


        commentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (API.OK == response.code()) {
                    newComment.setEntityId(entity.getId());
                    commentItems.add(new CommentItem(newComment.getUserName(), newComment.getEntityId(),
                            newComment.getContent(), newComment.getRating()));
                    adapterComment.notifyDataSetChanged();
                    Toast.makeText(DetailsActivity.this, "Comentario registrado", Toast.LENGTH_LONG).show();

                    acumulateRating += newComment.getRating();
                    globalRating = acumulateRating / commentItems.size();
                    ratingBar.setRating((float) globalRating);

                } else if (API.METHOD_NOT_ALLOWED== response.code()){
                    Toast.makeText(DetailsActivity.this, "Useted ya ha comentado", Toast.LENGTH_LONG).show();
                }else if(API.INTERNAL_SERVER_ERROR == response.code()){
                    Toast.makeText(DetailsActivity.this, "Error: " + response.message(), Toast.LENGTH_LONG).show();
                } else if(API.BAD_REQUEST == response.code()){
                    Toast.makeText(DetailsActivity.this, "No ha podido registrarse el comentario: " + response.message(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DetailsActivity.this, "Error desconocido: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t){
                Toast.makeText(DetailsActivity.this, "Error al registrar comentario ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void visitRegister(Visit visit){

        VisitService visitService = API.getApi().create(VisitService.class);
        Call<Visit> visitServiceCall = visitService.register(visit);

        visitServiceCall.enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                int httpCode = response.code();

                switch(httpCode) {
                    case API.INTERNAL_SERVER_ERROR:
                        Toast.makeText(DetailsActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                        break;
                    case API.NOT_FOUND:
                        Toast.makeText(DetailsActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    case API.METHOD_NOT_ALLOWED:
                        Toast.makeText(DetailsActivity.this, response.message() + ": El usuario ya ha marcado para visitar dicha empresa hoy", Toast.LENGTH_LONG).show();
                        break;
                    case API.OK:
                        Toast.makeText(DetailsActivity.this, "Solicitud de visita realizada con éxito", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Error al registrar visita", Toast.LENGTH_LONG).show();
            }
        });
    }
}




























