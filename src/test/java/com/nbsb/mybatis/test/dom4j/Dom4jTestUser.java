package com.nbsb.mybatis.test.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import java.util.regex.Matcher;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: Wanghaonan @戏人看戏
 * @description: 测试解析xml
 * @create: 2024/3/22 09:59
 */
public class Dom4jTestUser {
    @Test
    public void testdom4j() throws DocumentException {
        //1创建解析器对象
        SAXReader saxReader = new SAXReader();
        //2解析成doc文档
        Document read = saxReader.read(Dom4jTestUser.class.getClassLoader().getResourceAsStream("mapper/User_Mapper.xml"));
//        Document read = saxReader.read("src/main/resources/mapper/User_Mapper.xml");
        //3根据doc文档获取元素信息
        Element rootElement = read.getRootElement();//获取根节点信息
        System.out.println("根节点名称： " + rootElement.getName());
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            System.out.println("子标签列表： " + element.getName());
            System.out.println("子标签的id属性值： " + element.attributeValue("id"));
            System.out.println("子标签的parameterType属性值： " + element.attributeValue("parameterType"));
            System.out.println("子标签的resultType属性值： " + element.attributeValue("resultType"));
            System.out.println("子标签中内容：" + element.getStringValue());
        }
    }

    @Test
    public void test() {
        // ? 匹配
        Pattern pattern = Pattern.compile("(#\\{(.*?)})"); //获取#{}中的内容
        String input = "#{nb} #{sb} #{ok} ";
        Matcher matcher = pattern.matcher(input);
        System.out.println(matcher);
        for (int i = 1; matcher.find(); i++) {
            String g1 = matcher.group(1);
            String g2 = matcher.group(2);
            System.out.println(g1);
            System.out.println(g2);
//            input = input.replace(g1, "?");
        }
//        System.out.println(input);
    }

}
