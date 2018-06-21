package com.example.abel.medib2;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abel.medib2.MatchFragment.OnListFragmentInteractionListener;
import com.example.abel.medib2.contents.MatchContent.Match;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link //League} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMatchRecyclerViewAdapter extends RecyclerView.Adapter<MyMatchRecyclerViewAdapter.ViewHolder> {

    private final List<Match> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMatchRecyclerViewAdapter(List<Match> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTeamName1.setText(mValues.get(position).mTeamName1);
        holder.mTeamName2.setText(mValues.get(position).mTeamName2);
        holder.mVsView.setText("VS");
        holder.mTeamOdd2.setText( mValues.get(position).mTeamOdd2);
        holder.mTeamOdd1.setText(mValues.get(position).mTeamOdd1);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Log.d("TAGG","Clicked");
                    mListener.onMatchSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTeamName1;
        public final TextView mTeamName2;
        public final TextView mTeamOdd1;
        public final TextView mTeamOdd2;
        public final TextView mVsView;

        public Match mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamName1 = (TextView) view.findViewById(R.id.team_name_1);
            mTeamOdd1 = (TextView) view.findViewById(R.id.team_odd_1);
            mVsView = (TextView) view.findViewById(R.id.vs);
            mTeamName2 = (TextView) view.findViewById(R.id.team_name_2);
            mTeamOdd2 = (TextView) view.findViewById(R.id.team_odd_2);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeamOdd2.getText() + "'";
        }
    }
}
