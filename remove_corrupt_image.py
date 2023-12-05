import os
from PIL import Image
root_dir = './data_fix'
extensions = []
for fldr in os.listdir(root_dir):
    sub_folder_path = os.path.join(root_dir, fldr)
    for filee in os.listdir(sub_folder_path):
        file_path = os.path.join(sub_folder_path, filee)
        
        # if is_image(file_path):
        try:
            with Image.open(file_path) as im:
                rgb_im = im.convert('RGB')
            if filee.split('.')[1] not in extensions:
                extensions.append(filee.split('.')[1])
        except (IOError, OSError) as e:
            print(f"Error opening image at {file_path}: {e}. Removing the corrupt image.")
            os.remove(file_path)
        except Exception as e:
            print(f"An unexpected error occurred while processing {file_path}: {e}")