import os
 
def batch_rename(directory, prefix):
    for count, filename in enumerate(os.listdir(directory)):
        dst = f"{prefix}{str(count)}.txt"
        src = os.path.join(directory, filename)
        dst = os.path.join(directory, dst)
        os.rename(src, dst)
 
directory = "path/to/your/directory"
prefix = "new_file_"
batch_rename(directory, prefix)