#this file read the directory and create an input txt file
find . -type f | grep \.ogg | cut -f2-7  -d '/' |sort -u > inputfile.txt
