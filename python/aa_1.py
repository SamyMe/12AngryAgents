#!/usr/bin/env python3
import matplotlib.pyplot as plt
import random


speakers = [7, 5, 10, 5, 3, 2, 3, 7, 10, 7, 10, 1, 7, 1, 1, 8, 10, 1, 12, 1,
            9, 1, 1, 7, 10, 3, 1, 3, 8, 3, 8, 3, 8, 10, 8, 7, 8, 7, 8, 7,
            8, 10, 9, 3, 9, 4, 7, 4, 1, 11, 12, 1, 7, 1, 2, 8, 2, 3, 1, 3,
            4, 3, 10, 12, 1, 10, 8, 10, 8, 10, 1, 3, 1, 5, 1, 6, 11, 8, 6, 1,
            6, 1, 7, 8, 7, 3, 4, 10, 5, 10, 5, 1, 5, 3, 11, 1, 8, 3, 8, 3,      # 100
            4, 3, 4, 8, 4, 8, 4, 8, 3, 4, 4, 8, 4, 4, 8, 3, 3, 10, 5, 1,
            4, 8, 3, 8, 3, 8, 10, 8, 4, 8, 5, 7, 8, 7, 9, 3, 6, 10, 11, 5,
            12, 2, 7, 4, 1, 3, 8, 8, 7, 1, 1, 1, 10, 7, 11, 3, 1, 3, 5, 11,
            10, 7, 9, 3, 1, 9, 10, 3, 7, 8, 9, 3, 9, 10, 3, 12, 8, 7, 4, 3,
            8, 3, 3, 3, 8, 3, 7, 3, 1, 3, 10, 6, 3, 8, 4, 8, 4, 8, 5, 8,        # 200
            11, 2, 4, 8, 10, 8, 2, 8, 10, 8, 3, 8, 3, 9, 2, 3, 5, 3, 9, 3,
            8, 9, 12, 9, 3, 9, 1, 2, 1, 8, 8, 3, 10, 8, 10, 11, 5, 1, 5, 1,
            7, 5, 7, 5, 7, 5, 4, 8, 10, 8, 7, 8, 1, 3, 5, 3, 4, 8, 3, 2,
            3, 9, 3, 1, 8, 7, 8, 3, 8, 11, 8, 10, 11, 3, 9, 3, 8, 3, 11, 3,
            6, 8, 6, 2, 8, 9, 10, 8, 2, 8, 2, 8, 10, 11, 8, 8, 2, 8, 2, 11,     # 300
            8, 6, 3, 3, 8, 3, 8, 3, 8, 3, 8, 3, 8, 3, 8, 3, 3, 8, 1, 3,
            4, 11, 12, 6, 1, 7, 3, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7,
            1, 8, 1, 9, 1, 10, 1, 11, 1, 12, 4, 10, 1, 3, 7, 5, 7, 11, 7, 5,
            7, 5, 11, 5, 7, 5, 1, 2, 3, 2, 3, 3, 12, 3, 2, 6, 5, 3, 8, 3,
            6, 8, 6, 8, 3, 8, 3, 8, 3, 8, 12, 8, 3, 5, 8, 5, 8, 5, 8, 5,        # 400
            3, 10, 8, 12, 8, 7, 3, 7, 3, 11, 7, 11, 7, 11, 7, 11, 7, 11, 7, 8,
            1, 1, 1, 10, 10, 10, 10, 10, 4, 10, 4, 4, 3, 8, 4, 3, 4, 12, 3, 4,
            2, 11, 2, 5, 6, 2, 6, 2, 6, 2, 12, 6, 3, 8, 6, 1, 11, 9, 4, 8,
            3, 3, 8, 3, 8, 4, 8, 3, 8, 3, 8, 3, 8, 3, 4, 8, 3, 8, 5, 9, 3]      # 481


class Agent():

    def __init__(self, id, certainty, receptiveness):
        self.id = id
        self.c = certainty
        self.r = receptiveness
        self.hist = []
        self.change = None

    def __str__(self):
        return ("{0:02d}".format(self.id)
                + " = r:" + "{0:.2f}".format(self.r)
                + ", c:" + "{0:.3f}".format(self.c))

    def listen_to(self, speaker):
        self.c += self.r * (speaker.c - self.c)


def run():
    for a in agents:
        a.c = .0
        a.hist = []
        a.change = None
    agents[7].c = .9
    agents[7].r = .0

    for i in range(len(speakers)):
        speaker = speakers[i] - 1
        for a in agents:
            if speaker == a.id:
                a.hist.append(a.c)
                continue  # the speaker is not influenced by himself
            a.listen_to(agents[speaker])
            a.hist.append(a.c)
            if a.change is None and a.c > 0.8:
                a.change = i


order = { 1: 421, 2: 481, 3: 330, 4: 473, 5: 237, 6: 338,
          7: 406, 8: 0, 9: 166, 10: 475, 11: 348, 12: 421 }

agents = [
    Agent(1, .0, 0.020925179050838755),
    Agent(2, .0, 0.012974820702034059),
    Agent(3, .0, 0.048040549685964176),
    Agent(4, .0, 0.014358717570566327),
    Agent(5, .0, 0.410658565792318600),
    Agent(6, .0, 0.048041947105693330),
    Agent(7, .0, 0.024360378701018214),
    Agent(8, .9, 0.000000000000000000),
    Agent(9, .0, 0.531881871067327600),
    Agent(10, .0, 0.014252868676840832),
    Agent(11, .0, 0.048045426849967510),
    Agent(12, .0, 0.020947206095848575)
]

for i in range(500):
    run()

    for a in agents:
        if a.change is None or a.change > order[a.id]:
            a.r *= 1.001
        elif a.change < order[a.id]:
            a.r *= 0.999

    for a in agents:
        print(order[a.id], end=" | ")
    print()
    for a in agents:
        print(a.change, end=" | ")
    print()
    for a in agents:
        print(a.r, end=" | ")
    print()
    print()


run()
xs = range(len(speakers))
six = [.8 for _ in range(len(speakers))]
for a in agents:
    plt.plot(xs, a.hist)
plt.plot(xs, six)
plt.show()
