
start = False

with open("script12AM") as data_file:
    for line in data_file:
        line = line.split(".")

        # THE BEGGINING OF THE DEBATE
        if not start:
            if line[0] == "SEVEN (to SIX)":
                start = True
            else :
                continue

        talk = line[0].split(" ")
        print talk
        speaker = talk[0]
        if len(talk)>2:
            receiver = talk[2][:-1]
        else :
            receiver = ""

        if not (speaker.isupper() and speaker.isalpha()):
            continue

        print speaker+"   TO   "+receiver


