#!/bin/bash

grep 'a href="/translation' | sed 's/^.*a href="//' | sed 's/".*//' | sed 's/^/www.heartofthecards.com/'
