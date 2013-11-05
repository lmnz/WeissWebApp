#!/bin/bash

grep 'a href' | sed 's/^.*="//' | sed 's/".*//' | sed 's/^/www.heartofthecards.com/'
