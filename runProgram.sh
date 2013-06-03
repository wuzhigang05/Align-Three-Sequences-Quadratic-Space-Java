#!/bin/bash

#let "v =  $# % 3"
#
#if [[ "$v" != 0 ]]
#then
#  echo "# of args has to be multiples of 3"
#  echo "current #args: ", $#
#  exit 1
#fi



for i in {4..5}
do
  screen -S "T$i" -d -m run"$i".sh
done 
