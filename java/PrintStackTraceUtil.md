# Java异常日志堆栈信息工具类
```java
public class ExceptionUtil {
    //打印异常堆栈信息
    public static String getStackTraceString(Throwable ex){
        String result;
        String stackTrace = buildErrorMessage(ex);
        String exceptionType = ex.toString();
        String exceptionMessage = ex.getMessage();
 
        result = String.format("%s : %s \r\n %s", exceptionType, exceptionMessage, stackTrace);
 
        return result;
    }
 
    //构造异常堆栈信息
    private static String buildErrorMessage(Throwable ex) {
        StackTraceElement[] traceElements = ex.getStackTrace();
 
        StringBuilder traceBuilder = new StringBuilder();
 
        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }
 
        return traceBuilder.toString();
    }
}
```