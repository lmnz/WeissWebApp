#!/bin/bash

grep 'a href' | sed 's/^.*="//' | sed 's/".*//'