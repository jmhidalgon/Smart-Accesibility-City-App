package sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.VisitService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.AccessibilityActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterVisit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.VisitItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visits;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

/** Fragment that has a visits history ListView
 */
public class HistoryFragment extends Fragment {

    private View rootView;

    private List<Visit> visitsUser;

    private ListView listViewVisit;
    private VisitItem visitItem;
    private ArrayList<VisitItem> visitItems;
    private AdapterVisit adapterVisit;

    /** Empty Constructor
     *
     */
    public HistoryFragment() {

    }

    /** onCreateView inherent method
     *
     * @param inflater inflater for the layout
     * @param container container that will contain the frament
     * @param savedInstanceState bundle
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_history, container, false);

        listViewVisit = (ListView) rootView.findViewById(R.id.listViewVisit);
        visitItems = new ArrayList<VisitItem>();
        adapterVisit = new AdapterVisit (getActivity(), R.layout.item_visit, visitItems);
        listViewVisit.setAdapter(adapterVisit);

        listViewVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i < 0 || i >= visitItems.size()){
                    return ;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("¿Desea cancelar su proxima visita?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            visitItems.remove(adapterVisit.getItem(i));
                            adapterVisit.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        getVisitByUser();

        return rootView;
    }

    /** setMenuVisibility inherent method to control when the fragment is visible
     *
     * @param visible boolean, true for set it visible, false in other case
     */
    @Override
    public void setMenuVisibility(final boolean visible) {
        if (visible) {
            visitItems.clear();
            adapterVisit.notifyDataSetChanged();
            getVisitByUser();
        }

        super.setMenuVisibility(visible);
    }

    /** onViewCreated inherent method
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /** Method to request the visits by the login user id
     */
    public void getVisitByUser() {

        if (SACAPPControl.getUser() != null) {
            VisitService visitService = API.getApi().create(VisitService.class);
            final Call<Visits> visitsCall = visitService.getVisitsByUser(SACAPPControl.getUser().getId());

            visitsCall.enqueue(new Callback<Visits>() {
                @Override
                public void onResponse(Call<Visits> call, Response<Visits> response) {
                    int httpCode = response.code();

                    switch (httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(getActivity(), response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(getActivity(), response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                            break;
                        case API.OK:
                            Visits visitsResponse = response.body();
                            visitsUser = visitsResponse.getVisits();
                            if (visitsUser != null) {
                                // fillListViewVisit();
                                for (int i = 0; i < visitsUser.size(); ++i) {
                                    fillListViewVisit(visitsUser.get(i));
                                    fillListViewWithEntityName(visitsUser.get(i), i);
                                }
                            }
                            // fillListViewAccessResource();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Visits> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error de conexion Visitas", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    /** Method to fill the listView visits
     *
     * @param v Visit to include in the ListView
     */
    public void fillListViewVisit(Visit v){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date today = new Date();
        Date visitDate = dateFormat.parse(v.getStringDate(),  new ParsePosition(0));

        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        today = c.getTime();

        boolean outOfDate = false;
        if(today.compareTo(visitDate) > 0){ // today is after visitDate
            outOfDate = true;
        } else if(today.compareTo(visitDate) < 0){ // today is before visitDate
            outOfDate = false;
        } else if(today.compareTo(visitDate) == 0) { // Same
            outOfDate = false;
        }

        visitItems.add(new VisitItem(SACAPPControl.getUser().getName(),
                v.getEntityId(),
                v.getStringDate(),
                outOfDate));
        adapterVisit.notifyDataSetChanged();
    }

    /** Method to request and fill the listView visits with the entity name
     *
     * @param v Visit to include in the ListView
     */
    public void fillListViewWithEntityName(Visit v, final int i){
        final int position = i;

        EntityService entityService = API.getApi().create(EntityService.class);
        Call<Entities> entityCall = entityService.getEntityById(v.getEntityId());

        entityCall.enqueue(new Callback<Entities>() {
            @Override
            public void onResponse(Call<Entities> call, Response<Entities> response) {
                int httpCode = response.code();

                switch(httpCode) {
                    case API.INTERNAL_SERVER_ERROR:
                        Toast.makeText(getContext(), response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                        break;
                    case API.NOT_FOUND:
                        Toast.makeText(getContext(), response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    case API.OK:
                        Entity entity = response.body().getEntities().get(0);
                        //((VisitItem)listViewVisit.getItemAtPosition(position)).setEntityName(entity.getEntityname());
                        visitItems.get(i).setEntityName(entity.getEntityname());
                        adapterVisit.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Entities> call, Throwable t) {

            }
        });
    }
}

