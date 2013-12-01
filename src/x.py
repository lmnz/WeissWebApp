#!/usr/bin/python

def spam():
	print "goin haaaam"

class Team:
	def __init__(self):
		self.__name = None
		self.__logo = None
		self.__members = 0
	def getName(self):
		print self.__name
	def setName(self, name):
		self.__name = name
	def wut(self):
		print __name__
