import shutil
import os
import time
 
source_directory = "path/to/source/directory"
backup_directory = "path/to/backup/directory"
 
def backup_files():
    timestamp = time.strftime("%Y%m%d%H%M%S")
    backup_folder = os.path.join(backup_directory, f"backup_{timestamp}")
    os.makedirs(backup_folder)
    for filename in os.listdir(source_directory):
        source_file = os.path.join(source_directory, filename)
        destination_file = os.path.join(backup_folder, filename)
        shutil.copy2(source_file, destination_file)
 
backup_files()