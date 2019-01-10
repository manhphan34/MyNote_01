package framgia.com.mynote.screen.detail_note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;

public class DetailNoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "EXTRA_NOTE";

    public static Intent getDetailNoteIntent(Context context, Note note) {
        Intent intent = new Intent(context, DetailNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, note);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
    }
}
