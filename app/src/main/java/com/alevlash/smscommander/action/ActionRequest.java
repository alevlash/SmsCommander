package com.alevlash.smscommander.action;

import android.content.Context;

public class ActionRequest {

    private Context _context;
    private String _phoneNumber;

    private ActionRequest(Builder builder) {
        _context = builder._context;
        _phoneNumber = builder._phoneNumber;
    }

    public Context getContext() {
        return _context;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Context _context;
        private String _phoneNumber;

        private Builder() {
        }

        public Builder setContext(Context context) {
            _context = context;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            _phoneNumber = phoneNumber;
            return this;
        }

        public ActionRequest build() {
            return new ActionRequest(this);
        }
    }

}
