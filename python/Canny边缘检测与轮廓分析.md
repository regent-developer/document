# Canny边缘检测与轮廓分析

```python
image = cv2.imread('picture.png')
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 高斯模糊
blurred = cv2.GaussianBlur(gray, (5, 5), 0)

# Canny边缘检测
edges = cv2.Canny(blurred, 50, 150)

# 查找轮廓
contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

for contour in contours:
    # 逼近多边形
    epsilon = 0.04 * cv2.arcLength(contour, True)
    approx = cv2.approxPolyDP(contour, epsilon, True)

    # 如果轮廓有4个点且是矩形
    if len(approx) == 4:
        # 计算矩形的长宽比
        x, y, w, h = cv2.boundingRect(approx)
        aspect_ratio = float(w) / h
        if 0.8 < aspect_ratio < 1.2:  # 如果长宽比接近1，表示是矩形
            # 绘制矩形
            cv2.drawContours(image, [approx], -1, (0, 255, 0), 2)

# 显示结果
cv2.imshow("Detected Rectangles", image)
cv2.waitKey(0)
cv2.destroyAllWindows()

```

* 步骤说明：
1. 灰度化：通过cv2.cvtColor()将图像转换为灰度图。
2. 高斯模糊：使用cv2.GaussianBlur()进行模糊处理，减少噪声。
3. Canny边缘检测：通过cv2.Canny()检测图像中的边缘。
4. 查找轮廓：使用cv2.findContours()获取图像的外部轮廓。
5. 轮廓逼近：通过cv2.approxPolyDP()简化轮廓形状，逼近为多边形。
6. 筛选矩形：通过检测轮廓点数为4的多边形，计算长宽比并判断其是否接近正方形（长宽比介于0.8和1.2之间）。
7. 绘制矩形：如果符合条件，使用cv2.drawContours()绘制绿色矩形框。
