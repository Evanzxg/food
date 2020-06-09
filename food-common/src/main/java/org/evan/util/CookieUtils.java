package org.evan.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Constant dropping wears the stone.
 * User: Evan
 * Date: 2020/5/26 0026
 * Time: 22:20
 * Description: 我们所有的努力所有的奋斗，都是为了拥有一个美好的未来。和遇见更好的自己。
 * 请把努力当成一种习惯，而不是三分钟热度。每一个你羡慕的收获，都是努力用心拼来的。
 * @author Administrator
 */
public class CookieUtils {

    /**
     * 添加cookie
     * @param res 返回cookie  response对象
     * @param name Cookie的key
     * @param value Cookie的value
     * @param maxAge Cookie的有效时长 默认-1
     */
    public static void addCookie(HttpServletResponse res,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        if(maxAge > 0){
            cookie.setMaxAge(maxAge);
        }
        res.addCookie(cookie);
    }

    /**
     * 根据cookie的key 获取Cookie对象
     * @param req
     * @param name
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest req,String name){
        Map<String, Cookie> cookieMap = ReadCookieMap(req);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }

        return null;
    }

    /**
     * 读取Cookie
     * @param req
     * @return 返回cookie的map集合
     */
    public static Map<String, Cookie> ReadCookieMap(HttpServletRequest req){
        Map<String, Cookie> map = new HashMap<>(15);
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie : cookies){
            map.put(cookie.getName(), cookie);
        }
        return map;
    }

    /**
     * 清除cookie
     * @param response
     * @param killcookie
     */
    public static void killCookie(HttpServletResponse response,Cookie killcookie) {
        killcookie.setValue(null);
        killcookie.setMaxAge(0);
        killcookie.setPath("/");
        response.addCookie(killcookie);
    }

    public static void setCookie(HttpServletRequest req, HttpServletResponse rep, String cookieName, String cookieValue) {
        setCookie(req,rep,cookieName,cookieValue,-1);
    }

    private static void setCookie(HttpServletRequest req, HttpServletResponse rep, String cookieName, String cookieValue, int maxAge) {
        setCookie(req,rep,cookieName,cookieValue,maxAge,true);
    }

    /**
     * 设置cookie的值,不设置生效时间,但编码
     * 在服务器被创建,返回给客户端,并且保存客户端
     * 如果设置了SETMAXAGE(int seconds),会把cookie保存在客户端的硬盘中
     * 如果没有设置,会默认把cookie保存在浏览器的内存中
     * 一旦设置setPath();只能通过设置的路径才能获取到当前的cookie信息
     * @param req
     * @param rep
     * @param cookieName
     * @param cookieValue
     * @param maxAge
     * @param isEncode
     */
    private static void setCookie(HttpServletRequest req, HttpServletResponse rep, String cookieName, String cookieValue, int maxAge, boolean isEncode) {
        doSetCookie(req,rep,cookieName,cookieValue,maxAge,isEncode);
    }

    private static void doSetCookie(HttpServletRequest req, HttpServletResponse rep, String cookieName, String cookieValue, int maxAge, boolean isEncode) {
        try {
            if(cookieValue == null){
                cookieValue = "";
            } else if(isEncode){
                cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            }
            Cookie cookie = new Cookie(cookieName,cookieValue);
            if(maxAge > 0){
                cookie.setMaxAge(maxAge);
            }
            if(null != req){
                //设置域名的cookie
                cookie.setDomain("localhost");
            }
            cookie.setPath("/");
            rep.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清楚cookie
     * @param request
     * @param response
     * @param cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        //获取cookie数组
        Cookie[] cookies = request.getCookies();
        //遍历cookie
        for (Cookie c:cookies ) {
            if (c.getName().equals(cookieName)) {
                //删除前必须要new 一个valu为空；
                Cookie cookie = new Cookie(c.getName(), null);
                //路径要相同
                cookie.setPath("/");
                //生命周期设置为0
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
    }
}
