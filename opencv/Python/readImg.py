# coding=utf-8

import cv2

if __name__ == '__main__':
    # 灰度图加载一张彩色图
    img = cv2.imread('lena.jpg', 0)

    # 先定义窗口，后显示图片
    cv2.namedWindow('lena', cv2.WINDOW_NORMAL)
    cv2.imshow('lena', img)
    cv2.waitKey(0)

    # 保存图片
    cv2.imwrite('lena_gray.jpg', img)
