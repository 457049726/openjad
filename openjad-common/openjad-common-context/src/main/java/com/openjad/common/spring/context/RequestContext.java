package com.openjad.common.spring.context;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * 请求上下文
 * 用于会话跟踪
 * 
 * @author hechuan
 *
 */
public class RequestContext {

	private static final ThreadLocal<RequestContext> LOCAL = new ThreadLocal<RequestContext>() {
		@Override
		protected RequestContext initialValue() {
			return new RequestContext();
		}
	};
	
	private final Map<String, String> attachments = new HashMap<String, String>();
    private final Map<String, Object> values = new HashMap<String, Object>();
    
    private InetSocketAddress localAddress;
    private InetSocketAddress remoteAddress;
    
    private Object request;
    private Object response;
	
    private static String USER_NO="_userNo";
    private static String ACCESS_TOKEN="_accessToken";
	
	 /**
     * get context.
     *
     * @return context
     */
    public static RequestContext getContext() {
        return LOCAL.get();
    }

    /**
     * remove context.
     *
     * @see com.alibaba.dubbo.rpc.filter.ContextFilter
     */
    public static void removeContext() {
        LOCAL.remove();
    }
	

	/**
	 * 获取当前用户标识
	 * 
	 * @return 当前用户标识
	 */
	public static String getCurrentUser() {
		return LOCAL.get().getUserNo();
	}

	public static void setCurrentUser(String user) {
		LOCAL.get().setUserNo(user);
	}

	
	public Object getRequest() {
        return request;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRequest(Class<T> clazz) {
        return (request != null && clazz.isAssignableFrom(request.getClass())) ? (T) request : null;
    }
    
    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResponse() {
        return response;
    }

    @SuppressWarnings("unchecked")
    public <T> T getResponse(Class<T> clazz) {
        return (response != null && clazz.isAssignableFrom(response.getClass())) ? (T) response : null;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

	public String getUserNo() {
		return values.get(USER_NO)==null?null:values.get(USER_NO).toString();
	}

	public RequestContext setUserNo(String userNo) {
		values.put(USER_NO, userNo);
		return this;
	}

	public String getAccessToken() {
		return values.get(ACCESS_TOKEN)==null?null:values.get(ACCESS_TOKEN).toString();
	}

	public RequestContext setAccessToken(String accessToken) {
		values.put(ACCESS_TOKEN, accessToken);
		return this;
	}
	
	


    /**
     * set local address.
     *
     * @param host
     * @param port
     * @return context
     */
    public RequestContext setLocalAddress(String host, int port) {
        if (port < 0) {
            port = 0;
        }
        this.localAddress = InetSocketAddress.createUnresolved(host, port);
        return this;
    }

    /**
     * get local address.
     *
     * @return local address
     */
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    /**
     * set local address.
     *
     * @param address
     * @return context
     */
    public RequestContext setLocalAddress(InetSocketAddress address) {
        this.localAddress = address;
        return this;
    }

    public String getLocalAddressString() {
        return getLocalHost() + ":" + getLocalPort();
    }

    /**
     * get local host name.
     *
     * @return local host name
     */
    public String getLocalHostName() {
        String host = localAddress == null ? null : localAddress.getHostName();
        if (host == null || host.length() == 0) {
            return getLocalHost();
        }
        return host;
    }

    /**
     * set remote address.
     *
     * @param host
     * @param port
     * @return context
     */
    public RequestContext setRemoteAddress(String host, int port) {
        if (port < 0) {
            port = 0;
        }
        this.remoteAddress = InetSocketAddress.createUnresolved(host, port);
        return this;
    }

    /**
     * get remote address.
     *
     * @return remote address
     */
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    /**
     * set remote address.
     *
     * @param address
     * @return context
     */
    public RequestContext setRemoteAddress(InetSocketAddress address) {
        this.remoteAddress = address;
        return this;
    }

    /**
     * get remote address string.
     *
     * @return remote address string.
     */
    public String getRemoteAddressString() {
        return getRemoteHost() + ":" + getRemotePort();
    }

    /**
     * get remote host name.
     *
     * @return remote host name
     */
    public String getRemoteHostName() {
        return remoteAddress == null ? null : remoteAddress.getHostName();
    }

    /**
     * get local host.
     *
     * @return local host
     */
    public String getLocalHost() {
//    	TODO	获取当前服务器
    	return null;
//        String host = localAddress == null ? null :
//                localAddress.getAddress() == null ? localAddress.getHostName()
//                        : NetUtils.filterLocalHost(localAddress.getAddress().getHostAddress());
//        if (host == null || host.length() == 0) {
//            return NetUtils.getLocalHost();
//        }
//        return host;
    }

    /**
     * get local port.
     *
     * @return port
     */
    public int getLocalPort() {
        return localAddress == null ? 0 : localAddress.getPort();
    }

    /**
     * get remote host.
     *
     * @return remote host
     */
    public String getRemoteHost() {
//    	TODO	
    	return null;
//        return remoteAddress == null ? null :
//                remoteAddress.getAddress() == null ? remoteAddress.getHostName()
//                        : NetUtils.filterLocalHost(remoteAddress.getAddress().getHostAddress());
    }

    /**
     * get remote port.
     *
     * @return remote port
     */
    public int getRemotePort() {
        return remoteAddress == null ? 0 : remoteAddress.getPort();
    }

    /**
     * get attachment.
     *
     * @param key
     * @return attachment
     */
    public String getAttachment(String key) {
        return attachments.get(key);
    }

    /**
     * set attachment.
     *
     * @param key
     * @param value
     * @return context
     */
    public RequestContext setAttachment(String key, String value) {
        if (value == null) {
            attachments.remove(key);
        } else {
            attachments.put(key, value);
        }
        return this;
    }

    /**
     * remove attachment.
     *
     * @param key
     * @return context
     */
    public RequestContext removeAttachment(String key) {
        attachments.remove(key);
        return this;
    }

    /**
     * get attachments.
     *
     * @return attachments
     */
    public Map<String, String> getAttachments() {
        return attachments;
    }

    /**
     * set attachments
     *
     * @param attachment
     * @return context
     */
    public RequestContext setAttachments(Map<String, String> attachment) {
        this.attachments.clear();
        if (attachment != null && attachment.size() > 0) {
            this.attachments.putAll(attachment);
        }
        return this;
    }

    public void clearAttachments() {
        this.attachments.clear();
    }

    /**
     * get values.
     *
     * @return values
     */
    public Map<String, Object> get() {
        return values;
    }

    /**
     * set value.
     *
     * @param key
     * @param value
     * @return context
     */
    public RequestContext set(String key, Object value) {
        if (value == null) {
            values.remove(key);
        } else {
            values.put(key, value);
        }
        return this;
    }

    /**
     * remove value.
     *
     * @param key
     * @return value
     */
    public RequestContext remove(String key) {
        values.remove(key);
        return this;
    }
    
    public RequestContext clear() {
        values.clear();
        return this;
    }
    

    /**
     * get value.
     *
     * @param key
     * @return value
     */
    public Object get(String key) {
        return values.get(key);
    }
	
	

}
