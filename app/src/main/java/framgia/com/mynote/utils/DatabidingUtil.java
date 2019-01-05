package framgia.com.mynote.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DatabidingUtil {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

    @BindingAdapter("date")
    public static void setTime(TextView textView, long time) {
        textView.setText(DateTimeUtil.convertLongToDate(time));
    }
}
