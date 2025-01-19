BEGIN {FS="/";}

{
 
directorys[$NF] = $0;

++ntracks[$4]
tracks[$4][$NF] = 1;

++nalbums[$3];
albums[$3][$4] = 1;

}
END {
print "<html>"
print " <meta http-equiv=\"content-type\" content =\"text/html;charset=utf-8\" />"
print "<body>"
print "<table border=\"1\">"
print " <tr>"
print "   <th>Artists</th>"
print "   <th>Album</th>"
print "   <th>Tracks</th>"
print "  </tr>"

        nartists = asorti(nalbums, artists);
        for (i = 1; i <= nartists; i++) {
       	 a = artists[i]
         print "  <tr>"
         m = asorti(albums[a], album)
         print "   <td rowspan=\""m"\">"a"</td>"

#loop for album
                for(j=1; j<= m;j++){
                        b = album[j]
                        print "   <td>"b"</td>"
                        print "   <td>"
                        print "    <table border=\"0\">"
                        c = asorti(tracks[b],track);                       
#loop for tracks
			for(k = 1; k <= c; k++){
                                print "     <tr><td><a href="directorys[track[k]]">"track[k] "</a></td></tr>"
			}

                        print "    </table>"
                        print "   </td>"
                        print "  </tr>"
                }
        }
	print "</table>"
	print "</body>"
}

