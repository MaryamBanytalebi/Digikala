package org.maktab.digikala.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


    @Entity(tableName = "cart")
    public class Order {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "order_id")
        private long primaryId;

        @ColumnInfo(name = "user_id")
        private long userId;

        @ColumnInfo(name = "product_id")
        private int product_id;

        public long getPrimaryId() {
            return primaryId;
        }

        public void setPrimaryId(long primaryId) {
            this.primaryId = primaryId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public Order(int product_id) {
            this.product_id = product_id;
        }
    }

