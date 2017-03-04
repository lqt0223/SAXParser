# SAXParser

这是一个SAX风格的XML解析器。使用方式和Java的标准SAX库`org.xml.sax.`相近。

与Java的标准SAX库的不同之处

- 取消工厂方法，可直接使用`SAXParser.getInstance()`取得解析器实例。
- 目前在解析上还有较多需要改进之处：(TODO)
  - XML语法错误检测
  - CDATA, 评论行等的支持



## 示例代码

```java
import com.lqt0223.SAXParser;
import com.lqt0223.SAXParserHandler;
import sun.tools.java.SyntaxError;

import java.io.IOException;
import java.util.HashMap;

public class SAXParserDemo{
    public static void main(String[] args) throws IOException, SyntaxError {
        //创建实例
        SAXParser parser = SAXParser.getInstance();
        //为解析器设置好各个事件的回调函数
        parser.setHandler(new SAXParserHandler() {
            //创建好自定义变量，用以记录XML文档中需要的数据
            String resultName = "";
            String className = "";
            HashMap<String, String> resultJspMap = new HashMap<>();

            boolean foundClass = false;
		   
          	//当解析开始时调用
            @Override
            public void startDocument() {
                System.out.println("Start parsing");
            }

          	//当完成一个XML开始标签（例如<XXX>）的解析时调用
            @Override
            public void startElement(String tagName, HashMap<String, String> attributes) {
                if(tagName.equals("action")){
                    if(attributes.get("name").equals("login")){
                        className = attributes.get("class");
                        foundClass = true;
                    }else{
                        foundClass = false;
                    }
                }else if(tagName.equals("result") && foundClass){
                    resultName = attributes.get("name");
                }
            }
          
		   //当完成一个XML结束标签（例如</XXX>）的解析时调用
            @Override
            public void endElement(String tagName) {

            }
          
		   //当一段XML标签之间的内容被解析完成时调用
            @Override
            public void innerText(String innerText) {
                if(foundClass){
                    String jsp = innerText;
                    resultJspMap.put(resultName,jsp);

                }
            }

            @Override
          	//当解析器读到XML文档结尾时调用
            public void endDocument() {
                System.out.println(className);
                System.out.println(resultJspMap);
                System.out.println("End parsing");
            }
        });
	
	//调用此方式，开始解析	
        parser.parse("src/test.xml");
    }
}
```



