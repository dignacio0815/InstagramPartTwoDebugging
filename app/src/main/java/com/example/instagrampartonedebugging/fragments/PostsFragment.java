 package com.example.instagrampartonedebugging.fragments;

 import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagrampartonedebugging.PostsAdapter;
import com.example.instagrampartonedebugging.R;
import com.example.instagrampartonedebugging.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

 /**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {

    public static final String TAG = "PostsFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String param1, String param2) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvPosts = view.findViewById(R.id.rvPosts);
        adapter = new PostsAdapter(getContext(), allPosts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

     protected void queryPosts() {
         ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
         query.include(Post.KEY_USER);
         query.setLimit(0);
         query.addDescendingOrder(Post.KEY_CREATED_AT);
         query.findInBackground(new FindCallback<Post>() {
             @Override
             public void done(List<Post> objects, ParseException e) {
                 if (e != null) {
                     Log.e(TAG, "Issue with getting posts...", e);
                     return;
                 }
                 for (Post post: objects) {
                     Log.i(TAG, "Post: " + post.getKeyDescription() + ", username: ");
                 }
                 allPosts.addAll(objects);
             }
         });
     }
}