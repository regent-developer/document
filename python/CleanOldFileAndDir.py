import os
import datetime
import shutil

def clean_old_files_and_folders(directory_path, days_threshold=2):

    """
    清理目录中超过指定天数阈值的文件和文件夹。

    参数：
    directory_path (str): 要清理的目录路径。
    days_threshold (int): 天数阈值，文件和文件夹超过此阈值将被删除。默认值为2天。
    """
    current_datetime = datetime.datetime.now()

    for item in os.listdir(directory_path):
        item_path = os.path.join(directory_path, item)

        if os.path.isfile(item_path):
            file_created_time = datetime.datetime.fromtimestamp(os.path.getctime(item_path))
            days_since_creation = (current_datetime - file_created_time).days

            if days_since_creation > days_threshold:
                os.remove(item_path)
                print(f"Deleted file: {item_path}")

        elif os.path.isdir(item_path):
            folder_created_time = datetime.datetime.fromtimestamp(os.path.getctime(item_path))
            days_since_creation = (current_datetime - folder_created_time).days

            if days_since_creation > days_threshold:
                shutil.rmtree(item_path)
                print(f"Deleted folder: {item_path}")


if __name__ == '__main__':
    directory_to_clean = r'清理的路径'
    clean_old_files_and_folders(directory_to_clean)
