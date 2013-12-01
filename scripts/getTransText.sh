#!/bin/bash

while read line
do
	curl "www.heartofthecards.com$line" > ".$line"
done
