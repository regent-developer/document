import cv2
import numpy as np
from matplotlib import pyplot as plt

img_rgb = cv2.imread('SourceIMG.jpeg')
img_gray = cv2.cvtColor(img_rgb, cv2.COLOR_BGR2GRAY)
template = cv2.imread('TemplateIMG.jpeg', 0)

height, width = template.shape[::]

res = cv2.matchTemplate(img_gray, template, cv2.TM_CCOEFF_NORMED)
plt.imshow(res, cmap='gray')

threshold = 0.5 #For TM_CCOEFF_NORMED, larger values = good fit.

loc = np.where( res >= threshold)  

for pt in zip(loc[::-1]): 
    cv2.rectangle(img_rgb, pt, (pt[0] + width, pt[1] + height), (255, 0, 0), 1) 

cv2.imshow("Matched image", img_rgb)
cv2.waitKey()
cv2.destroyAllWindows() 
