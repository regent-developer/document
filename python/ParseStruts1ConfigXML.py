import openpyxl
import xml.etree.ElementTree as ET
import os
import glob
import re

# 获取当前文件夹路径
current_dir = os.path.dirname(os.path.abspath(__file__))

# 获取当前文件夹中的所有XML文件
xml_files = glob.glob(os.path.join(current_dir, '*.xml'))

# 创建一个新的工作簿
workbook = openpyxl.Workbook()

# 获取当前活动的工作表
#worksheet = workbook.active

# 遍历所有XML文件
for xml_file in xml_files:
    tree = ET.parse(os.path.basename(xml_file))
    root = tree.getroot()

    worksheet = workbook.create_sheet(re.split(r'_', os.path.splitext(xml_file)[0])[1])

    data = []

    # 根据元素路径查找特定元素
    for elem in root.iter('action'):
        # 处理找到的元素
        # 处理action节点的属性
        path = elem.attrib['path']
        type = elem.attrib['type']
        name = elem.attrib['name']
        inData = elem.attrib.get('input', '无')
        validate = elem.attrib.get('validate', '无')

        # 处理action节点下的forward节点
        forward = '无'
        pNode = elem.find('forward')
        if pNode is not None:
            forward = pNode.get('path', '无')

        print(path + ',' + type + ',' + name + ',' + inData + ',' + validate + ',' + forward)
        # 向工作表中添加数据
        tmp = [path, type, name, inData, validate, forward ]
        data.insert(1, tmp)
   
    for row in data:
        worksheet.append(row)
    
# 保存工作簿
workbook.save(r'Action结果.xlsx')