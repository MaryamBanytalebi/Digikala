package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemOrderBinding;
import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.OrderDBRepository;
import org.maktab.digikala.viewmodel.OrderViewModel;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ProductHolder> {

    private final LifecycleOwner mOwner;
    private OrderViewModel mOrderViewModel;
    private ItemOrderBinding mItemOrderBinding;
    private Context mContext;

    public OrderProductAdapter(LifecycleOwner owner, Context context, OrderViewModel orderViewModel) {
        mOwner = owner;
        mOrderViewModel = orderViewModel;
        mOrderViewModel.setContext(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemOrderBinding itemOrderBinding =
                DataBindingUtil.inflate(LayoutInflater.from(mOrderViewModel.getApplication()),
                        R.layout.item_order,
                        parent,
                        false);
        return new ProductHolder(itemOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.bindOrderProductItem(mOrderViewModel.getProductList().get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderViewModel.getProductList().size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{

        ItemOrderBinding mItemOrderBinding;
        OrderDBRepository mOrderDBRepository;

        public ProductHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            mItemOrderBinding = binding;
            mItemOrderBinding.setOrderViewModel(mOrderViewModel);
            mItemOrderBinding.setLifecycleOwner(mOwner);
            mOrderDBRepository = OrderDBRepository.getInstance(mContext);
        }

        private void bindOrderProductItem(Product product){

            Order order = mOrderDBRepository
                    .getOrder(product.getId());
            if (order != null) {
            mItemOrderBinding.setProductId(product.getId());
            mItemOrderBinding.textOrderProductName.setText(product.getTitle());
            mItemOrderBinding.numberOfProduct
                        .setText(String.valueOf(order.getProduct_count()));
            }
            mItemOrderBinding.textOrderProductPrice.setText(product.getPrice());
            Picasso.get()
                    .load(product.getImages().get(0).getSrc())
                    .into(mItemOrderBinding.imageOrderProduct);
        }
    }
}
