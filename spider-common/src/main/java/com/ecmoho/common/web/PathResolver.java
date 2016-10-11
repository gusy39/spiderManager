package com.ecmoho.common.web;

/**
 *
 *
 * @author liufang
 */
public interface PathResolver {

    String getPath(String uri);

    String getPath(String uri, String prefix);
}
