package org.maktab.digikala.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import org.maktab.digikala.model.MapAddress;
import org.maktab.digikala.room.OrderDataBase;
import org.maktab.digikala.room.OrderDataBaseDao;

import java.util.List;

public class AddressDBRepository implements IAddressRepository {

    private static AddressDBRepository sInstance;

    private Context mContext;
    private MutableLiveData<List<MapAddress>> mListMutableLiveData;
    private OrderDataBaseDao mOrderDao;

    public static AddressDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AddressDBRepository(context);

        return sInstance;
    }

    private AddressDBRepository(Context context) {
        mContext = context.getApplicationContext();
        OrderDataBase orderDatabase = Room.databaseBuilder(mContext,
                OrderDataBase.class,
                "cart.db")
                .allowMainThreadQueries()
                .build();

        mOrderDao = orderDatabase.getOrderDatabaseDao();
        mListMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MapAddress>> getListMutableLiveData() {
        return mListMutableLiveData;
    }

    public void setListMutableLiveData(MutableLiveData<List<MapAddress>> listMutableLiveData) {
        mListMutableLiveData = listMutableLiveData;
    }

    @Override
    public void updateAddress(MapAddress mapAddress) {
        mOrderDao.updateAddress(mapAddress);
    }

    @Override
    public void insertAddress(MapAddress mapAddress) {
        mOrderDao.insertAddress(mapAddress);
    }

    @Override
    public void insertAddresses(List<MapAddress> mapAddresses) {
        mOrderDao.insertAddresses(mapAddresses);
    }

    @Override
    public void deleteAddress(MapAddress mapAddress) {
        mOrderDao.deleteAddress(mapAddress);
    }

    @Override
    public List<MapAddress> getMapAddresses() {
        return mOrderDao.getAddresses();
    }

    @Override
    public MapAddress getAddress() {
        return mOrderDao.getAddress();
    }

    @Override
    public MapAddress getAddressWithId(long addressId) {
        return mOrderDao.getAddressWithId(addressId);
    }
}
