#!/usr/bin/python -B
import x, sys

def readTransText(transText):
	for line in open(transText, 'r'):
		print line

def main():
    # get arguments
    if 2 != len(sys.argv):
        print('error: exactly one input file required')
        sys.exit(2)
    transText = sys.argv[1]
    readTransText(transText)

if __name__ == "__main__":
	main()