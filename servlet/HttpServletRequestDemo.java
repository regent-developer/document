package HttpServletRequestDemo;
 
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
 
@WebServlet("/HttpServletRequestDemo")
public class HttpServletRequestDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        StringBuilder stringBuilder=new StringBuilder();
 
        //协议名称和版本
        stringBuilder.append(req.getProtocol());
        stringBuilder.append("<br>");
 
        //HTTP方法名称
        stringBuilder.append(req.getMethod());
        stringBuilder.append("<br>");
 
        //协议名称直到HTTP请求第一个查询字符串
        stringBuilder.append(req.getRequestURI());
        stringBuilder.append("<br>");
 
        //请求上下文的请求URL部分
        stringBuilder.append(req.getContextPath());
        stringBuilder.append("<br>");
 
        //URL中查询字符串
        stringBuilder.append(req.getQueryString());
        stringBuilder.append("<br>");
 
        //枚举
        //请求中的所有头名
        Enumeration<String> headerNames=req.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name=headerNames.nextElement();
            stringBuilder.append(name+": "+req.getHeader(name));
            stringBuilder.append("<br>");
        }
 
        resp.setContentType("text/html;charset=utf8");
        resp.getWriter().write(stringBuilder.toString());
    }
}