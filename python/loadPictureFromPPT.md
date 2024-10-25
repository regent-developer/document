from spire.presentation.common import *
from spire.presentation import *

# 加载PPT文档
ppt = Presentation()
ppt.LoadFromFile("xxx.pptx")

# 获取第一张幻灯片
slide = ppt.Slides[0]

i = 0
# 遍历幻灯片中所有形状
for s in slide.Shapes:

    # 判断形状是否为SlidePicture类型
    if isinstance(s, SlidePicture):

        # 提取该类型图片
        ps = s if isinstance(s, SlidePicture) else None
        ps.PictureFill.Picture.EmbedImage.Image.Save("幻灯片图片/幻灯片图_"+str(i)+".png")
        i += 1

    # 判断形状是否为PictureShape类型
    if isinstance(s, PictureShape):

        # 提取该类型图片
        ps = s if isinstance(s, PictureShape) else None
        ps.EmbedImage.Image.Save("幻灯片图_"+str(i)+".png")
        i += 1

ppt.Dispose()
