package org.evan.service;

import org.evan.service.pay.HttpUtil;
import org.evan.service.pay.MD5Util;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

/**
 * @program: api-demo
 * @description: 微信支付工具类
 * @author: Evan
 * @create: 2020-08-17 13:39
 **/
public class WechatPayUtil {

    private static final String APPID="wx632c8f211f8122c6";
    private static final String MCHID="1497984412";
    private static final String APPKEY="sbNCm1JnevqI36LrFwcaTOhkGxFnC";


    //统一下单                              https://api.mch.weixin.qq.com/pay/unifiedorder
    private static final String micropay= "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //查询订单
    private static final String orderquery= "https://api.mch.weixin.qq.com/pay/orderquery";
    //关闭订单
    private static final String reverse= "https://api.mch.weixin.qq.com/pay/closeorder";

    /**
     * 生成签名
     * @param map
     * @return
     */
    private static String createSign(TreeMap<String,Object> map){
        //1.所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        // 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA
        StringBuffer sb = new StringBuffer();
        for (String k : map.keySet()) {
            if(map.get(k) != null){
                sb.append(k+"="+map.get(k)+"&");
            }
        }
        if(sb.length() > 0){
            sb.delete(sb.length() - 1, sb.length());
        }
        //2.在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
        sb.append("&key=" + APPKEY);
        String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
        return sign;
    }


    public static String createNonce(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //Map-生成请求的xml
    public static String createXML(TreeMap<String,Object> map) throws IOException {
        //创建根节点
        Element root=new Element("xml");
        //创建文档对象
        Document document=new Document(root);
        //循环创建子节点 并添加
        for(String key:map.keySet()){
            Element child=new Element(key);
            child.setText(map.get(key).toString());
            document.getRootElement().addContent(child);
        }
        //创建xml输出器
        XMLOutputter xmlOutputter=new XMLOutputter();
        //创建输出内存流
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //把内容写出到内存流
        xmlOutputter.output(document,baos);
        return baos.toString();
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, Object> data) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key: data.keySet()) {
                String value = data.get(key).toString();
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
            writer.close();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String,Object> parseXml(String xml) throws JDOMException, IOException {
        HashMap<String, Object> map = new HashMap<>();
        //转换数据
        ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
        //创建解析器
        SAXBuilder builder = new SAXBuilder();
        //创建文档对象
        Document document = builder.build(bais);
        //获取根节点
        Element root = document.getRootElement();
        //遍历子节点
        List<Element> children = root.getChildren();
        for (Element child : children) {
            map.put(child.getName(),child.getValue());
        }
        return map;
    }

    private static TreeMap<String,Object> createParam(){
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("appid", APPID);
        treeMap.put("mch_id", MCHID);
        treeMap.put("nonce_str", createNonce());
        return treeMap;
    }

    //统一下单
    public static String micropay(WxPayDto dto) throws Exception {
        TreeMap<String,Object> map=createParam();
        map.put("trade_type","NATIVE");
        map.put("notify_url","http://localhost:8901/api/pay/wxchatnotify.do");
        map.put("body",dto.getBody());
        map.put("out_trade_no",dto.getOut_trade_no());
        map.put("total_fee",dto.getTotal_fee());
        map.put("spbill_create_ip","127.0.0.1");
        map.put("sign",createSign(map));
        String requestXml = createXML(map);

        System.out.println("XML:" + requestXml);
        String responseXml= HttpUtil.postData(micropay,requestXml);
        System.out.println(responseXml);
        return responseXml;
    }

    public static void main(String[] args) throws Exception {
        WxPayDto dto=new WxPayDto();
        dto.setBody("测试数据");
        dto.setOut_trade_no("202001010002");
        dto.setTotal_fee(1);
        System.out.println("预支付链接"+micropay(dto));
    }
}
