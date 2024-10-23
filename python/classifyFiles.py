import os
import shutil
 
def classify_files(directory):
    file_types = {}
    for filename in os.listdir(directory):
        file_path = os.path.join(directory, filename)
        if os.path.isfile(file_path):
            extension = os.path.splitext(filename)[1]
            if extension not in file_types:
                file_types[extension] = []
            file_types[extension].append(file_path)
    for extension, files in file_types.items():
        destination_folder = os.path.join(directory, extension[1:])
        os.makedirs(destination_folder, exist_ok=True)
        for file in files:
            shutil.move(file, destination_folder)
 
directory = "path/to/your/files/directory"
classify_files(directory)