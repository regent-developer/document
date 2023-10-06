# 提前下载ffmpeg，并配置环境变量
import os  # 获取目录下的所有文件列表
import fnmatch  # 文件格式筛选模块，筛选指定格式文件

# 遍历
def dirlist(path, allfile):
	filelist = os.listdir(path)
	for filename in filelist:
		filepath = os.path.join(path, filename)
		if os.path.isdir(filepath):
			dirlist(filepath, allfile)
		elif fnmatch.fnmatch(filepath, '*.mov'):  # 判断文件格式
			allfile.append(filepath)
	return allfile

# 格式转换
def RunScript(fileList):
	count = 0
	code = "ffmpeg -i "
	for filename in fileList:
		input = filename
		output = "D:/temp/" + str(count) + ".mp4"
		count += 1
		finishcode = code + input + " " + output
		os.system(finishcode)

# 主程序运行
if __name__ == '__main__':
	fileDir = r'D:/temp/'
	allfile = []
	dirlist(fileDir, allfile)
	for name in allfile:
		RunScript(allfile)
		

