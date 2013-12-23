#!/usr/bin/python

import sys
from structures import cardBean
        
def grabFileText(transText):
    file = open(transText, 'r')
    return file.read()

def separator():
    return "================================================================================"
    
def isValidCard(card):
    if card.find("Card No.") == -1 or card.find("Rarity:") == -1:
        return False
    if card.find("Color:") == -1 or card.find("Side:") == -1:
        return False
    if card.find("Level:") == -1 or card.find("Cost:") == -1:
        return False
    if card.find("Power:") == -1 or card.find("Soul:") == -1:
        return False
    if card.find("Trait 1:") == -1 or card.find("Trait 2:") == -1:
        return False
    if card.find("Triggers:") == -1 or card.find("Flavor:") == -1:
        return False
    if card.find("TEXT:") == -1:
        return False
    return True
        
"""
\n\n Yuri, Leader of the Battlefront
JTHANGS
Card No.: AB/W11-106  Rarity: PR
Color: Red   Side: Weiss  Character
Level: 1   Cost: 0   Power: 4500   Soul: 1
Trait 1: JTHANGS (Death)      Trait 2: JTHANGS (Weapon)
Triggers: None
Flavor: This might be a little abrupt, but would you join us?
TEXT: [S] [Rest 1 of your ::Death:: Characters] This gains +1500 Power for the
turn. \n\n

info
name: card no.: rarity:
color: side & type: level:
cost: power: soul:
trait: triggers: flavor:
text:
"""

# Parses the Japanese characters from traits
def parseTrait(trait):
    # This means that there's Japanese characters to get rid of and English to extract
    if trait.find("(") != -1:
        # Split string into English and Japanese pieces
        pieces = trait.split(" ", 1)
        return pieces[1].strip("()")
    else:
        return trait
        
def convertEmpties(string):
    if string == "":
        return "None"
    else:
        return string

# Return a filled in card bean to add to list
def parseCard(card):
    # Remove white space from both ends
    card = card.strip()
    
    pieces = card.split("Card No.:")
    name = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("Rarity:")
    cardNo = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Color:")
    rarity = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Side:")
    color = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Level:")
    side = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Cost:")
    level = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Power:")
    cost = convertEmpties((pieces[0].split("\n"))[0].strip())

    pieces = pieces[1].split("Soul:")
    power = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("Trait 1:")
    soul = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("Trait 2:")
    trait1 = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("Triggers:")
    trait2 = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("Flavor:")
    triggers = convertEmpties((pieces[0].split("\n"))[0].strip())
    
    pieces = pieces[1].split("TEXT:")
    flavor = convertEmpties((pieces[0].replace("\n", " ")).strip())
    text = pieces[1].replace("\n", " ")
    text = convertEmpties(text.replace("\r", " " ).strip())
    
    # (name, cardNo, rarity, color, sideType, level, cost, power, soul, trait1, trait2, triggers, flavor, text):
    return cardBean(name, cardNo, rarity, color, side, level, cost, power, soul, trait1, trait2, triggers, flavor, text)

def main():
    # get file as argument from command line
    if 2 != len(sys.argv):
        print('error: exactly one input file required')
        sys.exit(2)
    transText = sys.argv[1]
    fileContent = grabFileText(transText)
    cardList = fileContent.split(separator())
    processedCardList = list()
    for card in cardList:
        if isValidCard(card):
            processedCardList.append(parseCard(card))
    file = open('cards.csv', 'a+')
    for processedCard in processedCardList:
        file.write(processedCard.name + "~~|}" + processedCard.cardNo + "~~|}")
        file.write(processedCard.rarity + "~~|}" + processedCard.color + "~~|}")
        file.write(processedCard.sideType + "~~|}" + processedCard.level + "~~|}")
        file.write(processedCard.cost + "~~|}" + processedCard.power + "~~|}")
        file.write(processedCard.soul + "~~|}" + processedCard.trait1 + "~~|}")
        file.write(processedCard.trait2 + "~~|}" + processedCard.triggers + "~~|}")
        file.write(processedCard.flavor + "~~|}" + processedCard.text + "\n")
        
if __name__ == "__main__":
    main()
