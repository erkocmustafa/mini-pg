export pid=`ps aux | grep mini-pg | awk 'NR==1{print $2}' | cut -d' ' -f1`;kill -9 $pid