package com.bingchong.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class HttpParams {
	private List<NameValuePair> params = new ArrayList<NameValuePair>();

	public List<NameValuePair> getParams() {
		return params;
	}

	public void addParam(String field, Object value) {
		if(value == null)
			return;
		params.add(new BasicNameValuePair(field, value.toString()));
	}
}
