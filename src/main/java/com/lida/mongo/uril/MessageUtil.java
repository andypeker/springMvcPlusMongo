package com.lida.mongo.uril;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.lida.mongo.message.resp.Article;
import com.lida.mongo.message.resp.MusicMessage;
import com.lida.mongo.message.resp.NewsMessage;
import com.lida.mongo.message.resp.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��Ϣ������
 */
public class MessageUtil {
    /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * ������Ϣ���ͣ�ͼ��
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�ͼƬ
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * ������Ϣ���ͣ�����λ��
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * �¼����ͣ�subscribe(����)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * �¼����ͣ�unsubscribe(ȡ������)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * �¼����ͣ�CLICK(�Զ���˵�����¼�)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * ����΢�ŷ���������XML��
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request)
            throws Exception {
        // ����������洢��HashMap��
        Map<String, String> map = new HashMap<String, String>();

        // ��request��ȡ��������
        InputStream inputStream = request.getInputStream();
        // ��ȡ������
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // �õ�xml��Ԫ��
        Element root = document.getRootElement();
        // �õ���Ԫ�ص������ӽڵ�
        List<Element> elementList = root.elements();

        // ���������ӽڵ�
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // �ͷ���Դ
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * �ı���Ϣ����ת����xml
     *
     * @param textMessage �ı���Ϣ����
     * @return xml
     */
    public static String textMessageToXml(com.lida.mongo.message.resp.TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * ������Ϣ����ת����xml
     *
     * @param musicMessage ������Ϣ����
     * @return xml
     */
    public static String messageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }

    /**
     * ͼ����Ϣ����ת����xml
     *
     * @param newsMessage ͼ����Ϣ����
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * ��չxstream��ʹ��֧��CDATA��
     *
     * @date 2013-05-19
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ������xml�ڵ��ת��������CDATA���
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * ���˵�
     *
     * @return
     */
    public static String getMainMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("���ã�СС갻�����Ϊ��������ظ�����ѡ�����").append("\n\n");
        buffer.append("1  ��ʷ�ϵĽ���").append("\n");
        buffer.append("2  ������Ϸ").append("\n");
        buffer.append("3  �ܱ�����(������)").append("\n");
        buffer.append("4  ������ѯ(������)").append("\n");
        buffer.append("6  ��Ů��̨(������)").append("\n");
        buffer.append("7  ������ѯ(������)").append("\n");
        buffer.append("8  �������(������)").append("\n\n");
        buffer.append("�ظ���?����ʾ�˰����˵�");
        return buffer.toString();
    }

    public static String youxi() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("����ֱ�ӵ����").append("\n\n");
        buffer.append("1  <a href=\"http://www.duopao.com/games/info?game_code=g20140120233048400063\">�׵�</a>����").append("\n");
        buffer.append("2  <a href=\"http://www.duopao.com/games/info?game_code=g20140212153040377809\">Flappy Bird</a>����С��").append("\n");
        buffer.append("3  <a href=\"http://www.duopao.com/games/info?game_code=g20140324115109221580\">2048</a>������Ϸ").append("\n");
        return buffer.toString();
    }

    /**
     * �ı���Ϣ����ת����xml
     *
     * @param textMessage �ı���Ϣ����
     * @return xml
     */
    public static String messageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * ͼ����Ϣ����ת����xml
     *
     * @param newsMessage ͼ����Ϣ����
     * @return xml
     */
    public static String messageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }


}