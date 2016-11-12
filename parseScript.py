# -*- coding: utf-8 -*-
# Removing all \(.*\)
# Replacing ’  with  '
# Removing Guard's part
import json

debateData = []

with open("script12AM-noparentheses") as data_file:
    for line in data_file:
        line = line.split(".")

        talk = line[0].split(" ")
        speaker = line[0]
        says = ".".join(line[1:])

        if not (speaker.isupper() and speaker.isalpha()):
            continue

        print speaker+"   SAID   "+says
        debateData.append([speaker, says])

print "Creating JSON file"
with open("debate.json", 'w') as debateFile:
    json.dump(debateData, debateFile, indent=4)
