import argparse
import random
import sys
import time

parser = argparse.ArgumentParser(description='Generate n files with random numbers divided by comma.')
parser.add_argument('files_amount', metavar='n', type=int, help='number of files needed (example: 20)')

parser.add_argument('files_size', metavar='size', type=int, help='size of file (example: 1024)')

args = parser.parse_args()
for n in range(0, args.files_amount):
    start_time = int(time.time())
    filename = str(int(time.time())) + '.txt'
    f = open(filename, 'w')
    text = ''
    while len(text) <= args.files_size:
        text += str(random.randint(0, 1000000)) + ','
    text += str(random.randint(0, 1000000))
    f.write(text)
    f.close()
    print(filename + ' created')
    if start_time == int(time.time()):
        time.sleep(1)
