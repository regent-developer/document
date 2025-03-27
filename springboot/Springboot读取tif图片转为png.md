# springboot读取tif图片转为png


```java
@Override
public List<YbglSetYbSPlitListVo> ybglSetYbSPlitList(String outPath) {
    if (outPath == null || outPath.equals("")) {
        return null;
    }
    List<YbglSetYbSPlitListVo> ybSPlitListVos = new ArrayList<>();
    // 目录
    String directory = uploadConfig.getLocalStorageDirectory();
    File outDir = new File(directory + outPath);
    // 遍历目录下的文件夹
    for (File file : Objects.requireNonNull(outDir.listFiles())) {
        if (file.isDirectory()) {
            // 只遍历文件夹
            // 文件夹名用作返回数据的type,在前端区分切片的类型
            String fileName = file.getName();
            System.out.println("文件夹: " + fileName);
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                System.out.println(subFile);
                String[] names = subFile.getName().split("\\.");
                String[] names2 = subFile.getName().split("_");
                if (names.length > 1 && "tif".equalsIgnoreCase(names[names.length - 1])) {
                    // 目前只返回tif文件
                    YbglSetYbSPlitListVo ybglSetYbSPlitListVo = new YbglSetYbSPlitListVo();
                    ybglSetYbSPlitListVo.setType(fileName);
                    ybglSetYbSPlitListVo.setYbId(names2[0]);
                    ybglSetYbSPlitListVo.setName(names2[1]);

                    try {
                        // 读取 TIFF 文件
                        BufferedImage image = ImageIO.read(subFile);
                        // 将 BufferedImage 转为 PNG 格式的字节数组
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", baos);
                        baos.flush();
                        byte[] imageBytes = baos.toByteArray();
                        baos.close();

                        // 将字节数组编码为 Base64 字符串
                        String base64String = Base64.getEncoder().encodeToString(imageBytes);
                        // 转化为base64
                        String thumbBase64 = "data:image/png;base64," + base64String;
                        ybglSetYbSPlitListVo.setThumb(thumbBase64);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ybSPlitListVos.add(ybglSetYbSPlitListVo);
                }
            }
        }
    }
    return ybSPlitListVos;
}

```
