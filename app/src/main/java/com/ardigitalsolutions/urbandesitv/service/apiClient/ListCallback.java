package com.ardigitalsolutions.urbandesitv.service.apiClient;

import java.util.List;

public interface ListCallback {
    void callback(List<?> content, String error,String pages);
}
