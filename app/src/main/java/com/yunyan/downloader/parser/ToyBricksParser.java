package com.yunyan.downloader.parser;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yunyan.download1.entity.ToyBricks;

import org.json.JSONException;

import java.io.IOException;

public class ToyBricksParser extends BaseParser<ToyBricks> {

	@Override
	public ToyBricks parseJSON(String paramString) throws JSONException,IOException {
        if (!TextUtils.isEmpty(checkResponse(paramString))){
            Gson gson=new Gson();
            return gson.fromJson(checkResponse(paramString),new TypeToken<ToyBricks>() {}.getType());
        }
		return null;
	}

	@Override
	public String checkResponse(String paramString) throws JSONException {
            if (paramString != null && !paramString.equals("error")) {
                //Logger.d("卡萌数据处理成json：",result);
                return paramString;
            } else {
                return null;
            }

	}

}
