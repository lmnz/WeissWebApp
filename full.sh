#!/bin/bash

while read line
do
	echo "$line" | tr -d '\n' > templink.txt
	cat templink.txt | ./loop.sh | telnet | tee temp.txt
	cat temp.txt | ./extract_transLink.sh >> link.txt
	rm temp.txt templink.txt
done