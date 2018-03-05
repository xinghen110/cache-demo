package com.qiquan.service.impl;

import com.qiquan.service.ZSDataService;
import com.qiquan.utils.ZSDataUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("ZSDataService")
public class ZSDataServiceImpl implements ZSDataService {


    private ZSDataUtil zsDataUtil;

    @Autowired
    public ZSDataServiceImpl(ZSDataUtil zsDataUtil) {
        this.zsDataUtil = zsDataUtil;
    }

    @Override
    public void getZSDate(String symbol) {
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        String SINAJS_URL = "http://hq.sinajs.cn/list=";
        Request request = new Request.Builder()
                .get()
                .url(SINAJS_URL +symbol)
                .build();
        //通过client发起请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.print(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    System.out.print(str);
                    zsDataUtil.parseString(str);
                }

            }
        });
    }
}
