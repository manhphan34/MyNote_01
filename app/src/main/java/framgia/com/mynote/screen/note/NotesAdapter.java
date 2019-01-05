package framgia.com.mynote.screen.note;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import framgia.com.mynote.R;
import framgia.com.mynote.data.model.Note;
import framgia.com.mynote.databinding.ItemNoteBinding;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<Note> mNotes;
    private LayoutInflater mInflater;

    public NotesAdapter(Context context, List<Note> notes) {
        mNotes = notes;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemNoteBinding binding = DataBindingUtil.
                inflate(mInflater, R.layout.item_note, viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBinding.setNote(mNotes.get(i));
    }

    @Override
    public int getItemCount() {
        return mNotes !=null ? mNotes.size() : 0;
    }

    public void addData(List<Note> notes) {
        if (notes == null) {
            return;
        }
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNoteBinding mBinding;

        public ViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
