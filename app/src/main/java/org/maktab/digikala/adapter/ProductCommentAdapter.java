package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemCommentBinding;
import org.maktab.digikala.model.Comment;
import org.maktab.digikala.view.activities.EditCommentActivity;
import org.maktab.digikala.viewmodel.ProductViewModel;

public class ProductCommentAdapter extends RecyclerView.Adapter<ProductCommentAdapter.ProductHolder>{

    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;
    private Context mContext;

    public ProductCommentAdapter(LifecycleOwner owner, ProductViewModel productViewModel, Context context) {
        mOwner = owner;
        mProductViewModel = productViewModel;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getCommentList().size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mProductViewModel.getApplication());
        ItemCommentBinding itemCommentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_comment,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(itemCommentBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Comment comment = mProductViewModel.getCommentList().get(position);
        holder.bindProduct(comment);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private Comment mComment;
        private final ItemCommentBinding mCommentBinding;

        public ProductHolder(ItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());

            mCommentBinding = itemCommentBinding;
            mCommentBinding.setLifecycleOwner(mOwner);
            mCommentBinding.commentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(EditCommentActivity.newIntent(mContext,mComment.getId()));
                }
            });
        }

        public void bindProduct(Comment comment) {
            mComment = comment;
            mCommentBinding.userName.setText(comment.getReviewer());
            mCommentBinding.userEmail.setText(comment.getReviewer_email());
            mCommentBinding.userRate.setText(String.valueOf(comment.getRating()));
            mCommentBinding.userComment.setText(comment.getReview());
        }
    }
}
