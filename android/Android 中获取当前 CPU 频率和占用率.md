# Android 中获取当前 CPU 频率和占用率

## 获取系统 CPU 核心数

```java
 val cpuCoreNum = Runtime.getRuntime().availableProcessors()

```

## 获取指定 CPU 当前频率

```java
private fun getAllCpuCoreFrequency() : Long {

        var frequency = 0L

        for (index in 0 until  cpuCoreNum){
            frequency += readFile("/sys/devices/system/cpu/cpu$index/cpufreq/scaling_cur_freq")
        }

        BLog.d("frequency : $frequency")

        return frequency / cpuCoreNum
    }


  private fun readFile(filePath: String): Long{
        try {
            val file = RandomAccessFile(filePath, "r")
            val content = file.readLine()
            file.close()

            if (TextUtils.isEmpty(content)){
                return 0L
            }

            BLog.d("readFile content : $content")

            return content.trim().toLong()

        }catch (e : Exception){
            e.printStackTrace()

           return 0L
        }
    }


```


## 获取 CPU 频率&获取 CPU 占用率

```java
object CPUUtils {

    private var cpuCoreNum = 0
    private var cpuMaxFrequency = 0L
    private var cpuMinFrequency = 0L

    fun initCpuCoreNum(){
        if (cpuCoreNum <= 0 || cpuMaxFrequency <= 0L || cpuMinFrequency <= 0L){

            cpuCoreNum = Runtime.getRuntime().availableProcessors()
            initMaxAndMinFrequency()

            if (cpuCoreNum > 0 && cpuMaxFrequency > 0L && cpuMinFrequency > 0L){
               SpManager.getInstance().setCanUseCPUFrequency(true)
            }
        }

        BLog.d("cpuCoreNum : $cpuCoreNum")
    }

    private fun initMaxAndMinFrequency()  {
        if (cpuCoreNum <= 0){
            return
        }

        cpuMaxFrequency = 0L
        cpuMinFrequency = 0L

        for (index in 0 until cpuCoreNum){
            cpuMaxFrequency += readFile("/sys/devices/system/cpu/cpu${index}/cpufreq/cpuinfo_max_freq")
            cpuMinFrequency += readFile("/sys/devices/system/cpu/cpu${index}/cpufreq/cpuinfo_min_freq")
        }


        BLog.d("cpuMaxFrequency : $cpuMaxFrequency, cpuMinFrequency : $cpuMinFrequency")
    }


    private fun readFile(filePath: String): Long{
        try {
            val file = RandomAccessFile(filePath, "r")
            val content = file.readLine()
            file.close()

            if (TextUtils.isEmpty(content)){
                return 0L
            }

            BLog.d("readFile content : $content")

            return content.trim().toLong()

        }catch (e : Exception){
           ExceptionHandler.recordException(e)

           return 0L
        }
    }
    
    private fun getAllCpuCoreFrequency() : Long {
        initCpuCoreNum()

        if (cpuCoreNum <=0){
            return 0L
        }

        var frequency = 0L

        for (index in 0 until  cpuCoreNum){
            frequency += readFile("/sys/devices/system/cpu/cpu$index/cpufreq/scaling_cur_freq")
        }

        BLog.d("frequency : $frequency")

        return frequency
    }

    // 获取CPU 频率
    fun findCurrentFrequencyPercent() : Long {

        val currentFrequency = getAllCpuCoreFrequency()

        BLog.d("currentFrequency : $currentFrequency, cpuMinFrequency : $cpuMinFrequency, cpuMaxFrequency : $cpuMaxFrequency")

        if (cpuMaxFrequency - cpuMinFrequency <= 0L || currentFrequency - cpuMinFrequency < 0L || cpuMaxFrequency - currentFrequency < 0L){
            return 0L
        }

        return (currentFrequency - cpuMinFrequency) * 100 / (cpuMaxFrequency - cpuMinFrequency)
    }

    // 获取 CPU 占用率
    fun getCpuCoreFrequency() : Long {
        initCpuCoreNum()

        if (cpuCoreNum <=0){
            return 0L
        }

        return getAllCpuCoreFrequency() / cpuCoreNum
    }

}

```

