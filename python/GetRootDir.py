import os
def get_item_path(item_name='python'):
    """
    :param item_name: 项目名称，如pythonProject
    :return:
    """
    # 获取当前所在文件的路径
    cur_path = os.path.abspath(os.path.dirname(__file__))

    # 获取根目录
    return cur_path[:cur_path.find(item_name)] + item_name

ROOT_DIR = get_item_path()
print(ROOT_DIR)