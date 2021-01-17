# Java8 处理日期

## 获取今天的日期

```java
LocalDate today = LocalDate.now();
```

## 获取年、月、日信息

```java
LocalDate today = LocalDate.now();
int year = today.getYear();
int month = today.getMonthValue();
int day = today.getDayOfMonth();
```

## 特定日期

```java
LocalDate date = LocalDate.of(2018,2,6);
```

## 判断两个日期是否相等

```java
LocalDate date1 = LocalDate.now();
LocalDate date2 = LocalDate.of(2018,2,5);

if(date1.equals(date2)){
    System.out.println("时间相等");
}else{
    System.out.println("时间不等");
}
```

## 检查周期性事件

```java
LocalDate date1 = LocalDate.now();

LocalDate date2 = LocalDate.of(2018,2,6);
MonthDay birthday = MonthDay.of(date2.getMonth(),date2.getDayOfMonth());
MonthDay currentMonthDay = MonthDay.from(date1);

if(currentMonthDay.equals(birthday)){
    System.out.println("是你的生日");
}else{
    System.out.println("你的生日还没有到");
}
```

## 获取当前时间

```java
LocalTime time = LocalTime.now();
```

## 获取当前时间

```java
LocalTime time = LocalTime.now();
LocalTime newTime = time.plusHours(3);
```

## 计算一周后的日期

```java
LocalDate today = LocalDate.now();
LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
```

## 计算一年前或一年后的日期

```java
LocalDate today = LocalDate.now();

LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);

LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);

```

## Clock 时钟类

```java
// Returns the current time based on your system clock and set to UTC.
Clock clock = Clock.systemUTC();
System.out.println("Clock : " + clock.millis());

// Returns time based on system clock zone
Clock defaultClock = Clock.systemDefaultZone();
System.out.println("Clock : " + defaultClock.millis());
```

## 判断日期是早于还是晚于另一个日期

```java
LocalDate today = LocalDate.now();

LocalDate tomorrow = LocalDate.of(2018,2,6);
if(tomorrow.isAfter(today)){
    System.out.println("之后的日期:"+tomorrow);
}

LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
if(yesterday.isBefore(today)){
    System.out.println("之前的日期:"+yesterday);
}
```

## 处理时区

```java
ZoneId america = ZoneId.of("America/New_York");
LocalDateTime localtDateAndTime = LocalDateTime.now();
ZonedDateTime dateAndTimeInNewYork  = ZonedDateTime.of(localtDateAndTime, america );
System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);
```

## 如何表示信用卡到期这类固定日期

```java
YearMonth currentYearMonth = YearMonth.now();
System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
YearMonth creditCardExpiry = YearMonth.of(2019, Month.FEBRUARY);
System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
```

## 检查闰年

```java
LocalDate today = LocalDate.now();
if(today.isLeapYear()){
    System.out.println("This year is Leap year");
}else {
    System.out.println("This year is not a Leap year");
}
```

## 计算两个日期之间的天数和月数

```java
LocalDate today = LocalDate.now();

LocalDate java8Release = LocalDate.of(2018, 12, 14);

Period periodToNextJavaRelease = Period.between(today, java8Release);
System.out.println("Months left between today and Java 8 release : "
        + periodToNextJavaRelease.getMonths() );
```

## 获取当前的时间戳

```java
Instant timestamp = Instant.now();
System.out.println("What is value of this instant " + timestamp.toEpochMilli());
```

## 如何使用预定义的格式化工具去解析或格式化日期

```java
String dayAfterTommorrow = "20180205";
LocalDate formatted = LocalDate.parse(dayAfterTommorrow, DateTimeFormatter.BASIC_ISO_DATE);
System.out.println(dayAfterTommorrow+"  格式化后的日期为:  "+formatted);
```

## 字符串互转日期类型

```java
LocalDateTime date = LocalDateTime.now();

DateTimeFormatter format1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//日期转字符串
String str = date.format(format1);

System.out.println("日期转换为字符串:"+str);

DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//字符串转日期
LocalDate date2 = LocalDate.parse(str,format2);
System.out.println("日期类型:"+date2);
```
