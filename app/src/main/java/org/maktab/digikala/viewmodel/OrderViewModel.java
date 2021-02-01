package org.maktab.digikala.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.digikala.adapter.OrderProductAdapter;
import org.maktab.digikala.databinding.FragmentOrderBinding;
import org.maktab.digikala.model.BillingAddress;
import org.maktab.digikala.model.Customer;
import org.maktab.digikala.model.Order;
import org.maktab.digikala.model.Product;
import org.maktab.digikala.model.ShippingAddress;
import org.maktab.digikala.repository.OrderDBRepository;
import org.maktab.digikala.repository.ProductRepository;
import org.maktab.digikala.view.activities.OrderActivity;
import org.maktab.digikala.view.activities.ProductDetailActivity;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class OrderViewModel extends AndroidViewModel {
    private OrderDBRepository mOrderDBRepository;
    private ProductRepository mProductRepository;
    private OrderProductAdapter mOrderProductAdapter;
    private FragmentOrderBinding mFragmentOrderBinding;
    private LiveData<Product> mProductLiveData;
    private LiveData<Customer> mCustomerLiveData;
    private LiveData<List<Product>> mProductListLiveData;
    private List<Product> mProductList;
    private List<Product> mProductListMostVisited;
    private List<Product> mProductListLatest;
    private List<Product> mProductListHighestScore;
    private List<Product> mSearchProduct;
    private Context mContext;


    public OrderViewModel(@NonNull Application application) {
        super(application);
        mOrderDBRepository = OrderDBRepository.getInstance(application);
        mProductRepository = new ProductRepository();

    }
    public void insertToOrder(Order order){
        mOrderDBRepository.insertOrder(order);
    }

    public void getOrderedProduct(){

        List<Order> carts = mOrderDBRepository.getOrders();
        for (int i = 0; i < carts.size(); i++) {
            mProductRepository.fetchProductItemsAsync(carts.get(i).getProduct_id());
            mProductLiveData = mProductRepository.getProductLiveData();

        }

    }

    public LiveData<Product> getLiveDateProduct(){
        return mProductRepository.getProductLiveData();
    }

    public LiveData<Customer> getLiveDataCustomer() {
        return mProductRepository.getCustomerLiveData();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext,productId));
    }

    public void onClickToGoToOrder() {
        mContext.startActivity(OrderActivity.newIntent(getApplication()));
    }

    public void onClickToBuy(int productId) {
        Order oredr = mOrderDBRepository.getOrder(productId);
        if (oredr == null) {
            insertToOrder(new Order(productId,1));
            Toast.makeText(mContext, "add to cart", Toast.LENGTH_SHORT).show();
        }else {
            int count = oredr.getProduct_count() + 1;
            oredr.setProduct_count(count);
            mOrderDBRepository.updateOrder(oredr);
            Toast.makeText(mContext, "add to cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickToBuyAgain(int productId) {
        onClickToBuy(productId);
        mOrderProductAdapter.notifyDataSetChanged();
        mFragmentOrderBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
    }

    public void onClickToDelete(int productId) {
        if (mOrderDBRepository.getOrder(productId).getProduct_count() == 1) {
            mOrderDBRepository.deleteOrder(mOrderDBRepository.getOrder(productId));
            for (int i = 0; i < mProductList.size(); i++) {
                if (mProductList.get(i).getId() == productId)
                    mProductList.remove(i);
            }
        }
        else {
            Order updateCart = mOrderDBRepository.getOrder(productId);
            int count = updateCart.getProduct_count() - 1;
            updateCart.setProduct_count(count);
            mOrderDBRepository.updateOrder(updateCart);

        }
        if (mProductList.size() == 0){
            mFragmentOrderBinding.recyclerOrder.setVisibility(View.GONE);
            mFragmentOrderBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
        }
        mFragmentOrderBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
        mOrderProductAdapter.notifyDataSetChanged();
    }

    public void setOrderedProductAdapter(OrderProductAdapter orderedProductAdapter) {
        mOrderProductAdapter = orderedProductAdapter;
    }

    public OrderProductAdapter getOrderedProductAdapter() {
        return mOrderProductAdapter;
    }

    public void setFragmentCartBinding(FragmentOrderBinding fragmentCartBinding) {
        mFragmentOrderBinding = fragmentCartBinding;
    }

    public Order getCart(int productId){
        return mOrderDBRepository.getOrder(productId);
    }

    public int getTotalPrice (){
        int totalPrice = 0;
        for (int i = 0; i < mProductList.size(); i++) {
            int price = Integer.parseInt(mProductList.get(i).getPrice());
            int count = getCart(mProductList.get(i).getId()).getProduct_count();
            totalPrice += (price * count);
        }
        return totalPrice;
    }

    public void onClickContinueBuy(){

        Random random = new Random();
        BillingAddress billingAddresses = new BillingAddress("maryam","banitalebi","maktab",
                "poonak","nateghnoori","tehran","north","1473165569",
                "iran","banytalebi.m@gmail.com","9013330520");

        ShippingAddress shippingAddresses = new ShippingAddress("maryam","banitalebi","maktab",
                "poonak","nateghnoori","tehran","north","1473165569",
                "iran");
        Customer customer = new Customer("banytalebi.m@gmail.com","maryam","banitalebi",
                "maryam.banitalebi", billingAddresses,
                shippingAddresses);

        mProductRepository.postCreateCustomerAsync(customer);
    }
}
