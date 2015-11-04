__author__ = 'James Sinclair'

import re
import decimal
import os

normalDimens = 'values\\dimens.xml'

emptyFileString = '<?xml version="1.0" encoding="utf-8"?>\n<resources>\n</resources>\n'

'''
    Gets the default values and creates a new file in the specified directory using the given modifier.
    If no modifier is given, create a file with empty resources tags.
'''
def createDimensFile(parentDirectory, directory, file, modifier):

    if modifier == '':
        if not os.path.exists(parentDirectory+directory):
            os.makedirs(parentDirectory+directory)

        fileOut = open(parentDirectory+directory+file, 'w+')
        # fileOut.truncate()
        fileOut.write(emptyFileString)
        fileOut.close()
    else:
        deciMod = decimal.Decimal(modifier)
        # print(file)
        # print(deciMod)

        fileString = ''

        with open(parentDirectory+normalDimens,'r') as f:
            for x in f:
                # print(x)
                try:
                    found = re.search('<.*?>(.+?)[a-z][a-z]</dimen>', x).group(1)

                    #moddedNumber = decimal.Decimal(found) * deciMod
                    moddedString = str(decimal.Decimal(found) * deciMod).rstrip('0').rstrip('.')
                    #print(str(moddedNumber))
                    #print(moddedString)

                    line = x[:x.index('">')+2] + moddedString + x[x.index('</')-2:]
                    # print(line)

                    '''print('match: '+found)
                    print('matchMod: ', (decimal.Decimal(found)*deciMod).normalize())
                    print('index1: ', x.index('">'))
                    print('index2: ', x.index('</'))'''
                    fileString+=line
                except AttributeError:
                    fileString+=x
            '''with open(file,'w') as g:
                for x in f:
                    x = x.rstrip()
                    if not x: continue
                    print >> g, int(x, 16)'''

        # print(fileString)

        if not os.path.exists(parentDirectory+directory):
            os.makedirs(parentDirectory+directory)

        fileOut = open(parentDirectory+directory+file, 'w+')
        # fileOut.truncate()
        fileOut.write(fileString)
        fileOut.close()
    return

if __name__ == '__main__':

    a = [['values-hdpi\\',''],['values-large\\','1.2'],['values-ldpi\\',''],['values-mdpi\\',''],['values-normal\\','1'],['values-normal-hdpi\\','0.85'],['values-normal-ldpi\\','0.8'],['values-normal-mdpi\\','0.8'],['values-normal-xhdpi\\','1'],['values-small\\','0.8'],['values-sw600dp\\','1.6'],['values-sw720dp\\','1.6'],['values-xlarge\\','1.6'],['values-xxhdpi\\',''],['values-xxxhdpi\\','']]

    for size in a:
        createDimensFile('C:\\temp\\python\\', size[0], 'dimens.xml', size[1])

