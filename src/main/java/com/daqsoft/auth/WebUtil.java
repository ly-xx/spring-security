package com.daqsoft.auth;

import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lxx
 */

public class WebUtil {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String X_REQUESTED_WITH = "X-Requested-With";
    private static final String CONTENT_TYPE = "Content-type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private WebUtil() {
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static boolean isAjax(SavedRequest request) {
        return request.getHeaderValues("X-Requested-With").contains("XMLHttpRequest");
    }

    public static boolean isContentTypeJson(SavedRequest request) {
        return request.getHeaderValues("Content-type").contains("application/json");
    }
}
