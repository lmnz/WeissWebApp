#!/bin/bash

read line
echo "open www.heartofthecards.com 80"
sleep 2
echo "GET $line HTTP/1.0"
echo "Host: www.heartofthecards.com"
echo
echo
sleep 2