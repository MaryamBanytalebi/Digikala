package org.maktab.digikala.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.maktab.digikala.R;
import org.maktab.digikala.databinding.ItemBuyBinding;
import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.repository.OrderDBRepository;
import org.maktab.digikala.viewmodel.OrderViewModel;

public class BuyProductAdapter extends RecyclerView.Adapter<BuyProductAdapter.ProductHolder> {

    private final OrderViewModel mOrderViewModel;
    private final LifecycleOwner mOwner;
    private Context mContext;

    public BuyProductAdapter(LifecycleOwner owner, Context context, OrderViewModel orderViewModel) {
        mOwner = owner;
        mOrderViewModel = orderViewModel;
        mOrderViewModel.setContext(context);
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mOrderViewModel.getProductList().size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mOrderViewModel.getApplication());
        ItemBuyBinding itemBuyBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_buy,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(itemBuyBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mOrderViewModel.getProductList().get(position);
        holder.bindProduct(product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        ItemBuyBinding mItemBuyBinding;
        OrderDBRepository mCartDBRepository;

        public ProductHolder(ItemBuyBinding itemBuyBinding) {
            super(itemBuyBinding.getRoot());
            mItemBuyBinding = itemBuyBinding;
            mItemBuyBinding.setOrderViewModel(mOrderViewModel);
            mItemBuyBinding.setLifecycleOwner(mOwner);
            mCartDBRepository = OrderDBRepository.getInstance(mContext);


        }

        public void bindProduct(Product product) {
            Order order = mCartDBRepository
                    .getOrder(product.getId());
            if (order != null) {
                mItemBuyBinding.setProductId(product.getId());
                mItemBuyBinding.textOrderProductName.setText(product.getTitle());

                mItemBuyBinding.numberOfProduct
                        .setText(String.valueOf(order.getProduct_count()));
            }

            mItemBuyBinding.textOrderProductPrice.setText(product.getPrice());
            Glide.with(mItemBuyBinding.getRoot())
                    .load(product.getImages().get(0).getSrc())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mItemBuyBinding.imageOrderProduct);
        }
    }
}
