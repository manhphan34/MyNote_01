package framgia.com.mynote.screen.note;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import framgia.com.mynote.R;
import framgia.com.mynote.databinding.ActivityNoteBinding;


/**
 * Note Screen.
 */
public class NoteActivity extends AppCompatActivity {
    private NoteViewModel mViewModel;
    private ActivityNoteBinding mBinding;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_note);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        mSearchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        return true;
    }
}
