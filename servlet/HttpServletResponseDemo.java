package HttpServletResponseDemo;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
 
@WebServlet("/HttpServletResponseDemo")
public class HttpServletResponseDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置响应状态码
        resp.setStatus(200);
 
        //设置响应的内容类型
        //这两个都是常用的
        //第二个是json类型
        resp.setContentType("text/html;charset=utf8");
//        resp.setContentType("application/json;charset=utf8");
 
        //告诉浏览器响应的字符集
        resp.setCharacterEncoding("utf8");
        //告诉服务器body中该用utf8字符集解析
//        req.setCharacterEncoding("utf8");
        //这两是不一样的，注意区分
 
        resp.getWriter().write("往body内写入文本格式数据");
 
        //强制跳转到指定页面
        resp.sendRedirect("Demo.html");
    }
}