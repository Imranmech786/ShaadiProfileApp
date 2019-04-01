package com.imran.shaaditestapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.imran.shaaditestapp.listener.OnItemClickListener;
import com.imran.shaaditestapp.R;
import com.imran.shaaditestapp.model.Result;

import org.apache.commons.lang.StringUtils;

import java.util.List;


public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.ViewHolder> {


    private Context mContext;
    private List<Result> mList;
    private OnItemClickListener mItemClickListener;

    public UserProfileAdapter(Context context, List<Result> resultList, OnItemClickListener itemClickListener) {
        mContext = context;
        mList = resultList;
        mItemClickListener = itemClickListener;
    }

    public List<Result> getmList() {
        return mList;
    }

    @NonNull
    @Override
    public UserProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserProfileAdapter.ViewHolder holder, final int pos) {

        Result result = mList.get(pos);
        String firstName = StringUtils.capitalize(result.getName().getFirst());
        String lastName = StringUtils.capitalize(result.getName().getLast());
        String fullName = firstName + " " + lastName;
        holder.name.setText(fullName);
        String age = String.valueOf(result.getDob().getAge());
        String city = StringUtils.capitalize(result.getLocation().getCity());
        String state = StringUtils.capitalize(result.getLocation().getState());
        String description = age + " , " + city + " , " + state;
        holder.description.setText(description);
        String imageUrl = result.getPicture().getLarge();
        final ImageRequest request;
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                    .build();
        } else {
            request = ImageRequestBuilder.newBuilderWithResourceId(R.color.skeleton_color)
                    .build();
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.profileImage.getController())
                .build();
        holder.profileImage.setController(controller);

        holder.declineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, holder.getAdapterPosition(), request);
                setAnimation(holder.itemView);
            }
        });

    }

    private void setAnimation(View viewToAnimate) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_left_to_right);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return mList != null && !mList.isEmpty() ? mList.size() : 0;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private SimpleDraweeView profileImage;
        private TextView name, description;
        private LinearLayout declineLayout;


        private ViewHolder(View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            declineLayout = itemView.findViewById(R.id.decline_layout);
            description = itemView.findViewById(R.id.description);

        }
    }
}
