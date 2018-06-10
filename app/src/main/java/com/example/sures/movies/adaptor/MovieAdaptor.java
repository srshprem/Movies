package com.example.sures.movies.adaptor;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import  android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.sures.movies.R;
import com.squareup.picasso.Picasso;
import com.example.sures.movies.data.*;
import java.util.ArrayList;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.MovieViewHolder>{
    private Cursor mCursor;
    private final Context mContext;
    final private ItemOnClickListener mItemOnClickListener;

    public MovieAdaptor (Context context, ItemOnClickListener itemOnClickListener) {
        this.mContext = context;
        this.mItemOnClickListener = itemOnClickListener;
    }

    public interface ItemOnClickListener {
        void ItemClicked (int positionClicked);
    }

    public MovieInfo getItem (int index) {
        if (this.mCursor != null) {
            this.mCursor.moveToPosition(index);
            return new MovieInfo(this.mCursor.getString(0), this.mCursor.getString(1), this.mCursor.getString(2), this.mCursor.getString(3), this.mCursor.getString(4));
        }
        return null;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_thumbnails;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindItem(holder.imageView.getContext(),position);
    }

    @Override
    public int getItemCount() {
        if (this.mCursor != null) {
            this.mCursor.getCount();
        }
        return 0;
    }

    public void updateData (Cursor cursor) {
        this.mCursor = cursor;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final ImageView imageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_thumbnail);
            itemView.setOnClickListener(this);
        }

        void bindItem(Context context, int index) {
            mCursor.moveToPosition(index);
            Picasso.with(context).load(mCursor.getString(3)).into(imageView);
        }

        @Override
        public void onClick(View v) {
            int positionClicked = getAdapterPosition();
            mItemOnClickListener.ItemClicked(positionClicked);
        }
    }
}
