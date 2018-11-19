package com.victor.pruebas.data.network.response;

import com.victor.pruebas.data.entity.ResponseEntity;
import com.victor.pruebas.data.entity.RestaurantEntity;

import java.util.List;

public class RestaurantsResponse {
    String total;
    int max;
    String sort;
    int count;
    List<RestaurantEntity> data;
    int offset;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RestaurantEntity> getData() {
        return data;
    }

    public void setData(List<RestaurantEntity> data) {
        this.data = data;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
