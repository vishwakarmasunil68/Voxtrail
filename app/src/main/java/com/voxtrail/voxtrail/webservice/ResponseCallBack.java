package com.voxtrail.voxtrail.webservice;


import com.voxtrail.voxtrail.pojo.ResponsePOJO;

/**
 * Created by sunil on 29-12-2016.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}
