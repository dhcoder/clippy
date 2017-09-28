package clippy.com.clippy;

import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClippyActionsActivity extends AppCompatActivity {

    private ListView myAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clippy_actions);

        myAppList = (ListView) findViewById(R.id.clippy_actions_list);

        List<String> nameList = new ArrayList<>();
        final List<String> packageList = new ArrayList<>();
        PackageManager pm = this.getPackageManager();


//        for (ApplicationInfo info : pm.getInstalledApplications(0)) {
//            nameList.add(info.packageName + " " + pm.getApplicationLabel(info));
//        }

        nameList.add("YouTube");
        nameList.add("Calendar");
        nameList.add("Phone");
        nameList.add("Search");
        nameList.add("Camera");


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);
        myAppList.setAdapter(adapter);

        myAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                switch (position) {
                    case 0:startYoutube();break;
                    case 1:startCalendar();break;
                    case 2:startPhone();break;
                    case 3:startGoogle();break;
                    case 4:startCamera();break;
                }

            }
        });
    }

    private void startGoogle() {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "hackathon"); // query contains search string
        startActivity(intent);
    }

    private void startYoutube() {
        Uri dummyIntentUri = Uri.parse("https://www.youtube.com/");
        Intent intent = new Intent(Intent.ACTION_VIEW, dummyIntentUri);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }

    private void startCalendar() {
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, Calendar.getInstance().getTimeInMillis());
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        startActivity(intent);
    }

    private void startPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
        startActivity(intent);
    }

    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }
}
