package org.maktab.digikala.view.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.maktab.digikala.R;
import org.maktab.digikala.model.Comment;
import org.maktab.digikala.viewmodel.OrderViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteCommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteCommentFragment extends DialogFragment {

    private int mCommentId;
    private Comment mComment;
    public static final String BUNDLE_DELETE_COMMENT = "Bundle_edit_comment";
    private OrderViewModel mCartViewModel;
    private MutableLiveData<Comment> mLiveDataOneComment;

    public DeleteCommentFragment() {
        // Required empty public constructor
    }

    public static DeleteCommentFragment newInstance(int commentId) {
        DeleteCommentFragment fragment = new DeleteCommentFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_DELETE_COMMENT,commentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mCommentId = getArguments().getInt(BUNDLE_DELETE_COMMENT);

        mCartViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mCartViewModel.fetchOneComment(mCommentId);
        mLiveDataOneComment = mCartViewModel.getLiveDataOneComment();
        observer();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_delete_comment, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_comment_title);
        builder.setIcon(R.drawable.ic_error);
        builder.setView(view);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCartViewModel.fetchDeleteComment(mComment.getId());
                Toast.makeText(getContext(),mComment.getReviewer(),Toast.LENGTH_SHORT).show();
                dismiss();

            }
        })
                .setNegativeButton(R.string.no, null);


        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void observer() {
        mLiveDataOneComment.observe(this, new Observer<Comment>() {
            @Override
            public void onChanged(Comment comment) {
                mComment = comment;

            }
        });
    }
}