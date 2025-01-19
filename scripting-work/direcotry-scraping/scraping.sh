#! /bin/bash 


#finding all the .ogg file under this directory 
printf "Total Tracks: "
find . -iname '*ogg' -type f | wc -l | sed -e 's/^[ \t]*//'

#find all the artists' directory then sort and cout the unique ones by only the filename
printf "\nTotal Artists: "
find . -mindepth 3 -maxdepth 3 -type d | cut -d"/" -f$44- | sort | uniq | wc -l | sed -e 's/^[ \t]*//'

#find artists that have mutlti genre albums and print out using uniq -d
printf "\nMulti-Genre Artists:\n"
find . -mindepth 3 -maxdepth 3 -type d | cut -d"/" -f$44- | sort | uniq -d

#find multi-disk albums 
printf "\nMulti-Disk Album:\n"
find . -mindepth 5 -maxdepth 5 -type d | cut -d"/" -f$55- | sort -d | cut -d'/' -f 1 | uniq -d

