from spire.presentation.common import *
from spire.presentation import *

# 加载PPT文档
ppt = Presentation()
ppt.LoadFromFile("xxx.pptx")

# 遍历文档中所有图片
for i, image in enumerate(ppt.Images):

    # 提取图片并保存
    ImageName = "提取图片/图_"+str(i)+".png"
    image.Image.Save(ImageName)

ppt.Dispose()
