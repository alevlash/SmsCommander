package com.alevlash.smscommander.action;

import android.content.Context;

import com.alevlash.smscommander.connection.ConnectionService;

import java.util.Map;

public class ActionRequest {

    private Context _context;
    private String _phoneNumber;
    private ConnectionService _connectionService;
    private Map<String, String> _parametersMap;

    private ActionRequest(Builder builder) {
        _context = builder._context;
        _phoneNumber = builder._phoneNumber;
        _connectionService = builder._connectionService;
        _parametersMap = builder._parametersMap;
    }

    public Context getContext() {
        return _context;
    }

    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public ConnectionService getConnectionService() {
        return _connectionService;
    }

    public Map<String, String> getParametersMap() {
        return _parametersMap;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Context _context;
        private String _phoneNumber;
        private ConnectionService _connectionService;
        private Map<String, String> _parametersMap;

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

        public Builder setConnectionService(ConnectionService connectionService) {
            _connectionService = connectionService;
            return this;
        }

        public Builder setParameterMap(Map<String, String> parameterMap) {
            _parametersMap = parameterMap;
            return this;
        }

        public ActionRequest build() {
            return new ActionRequest(this);
        }
    }

}
