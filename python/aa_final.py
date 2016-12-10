#!/usr/bin/env python3
import argparse
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
changes = { 1: 421, 2: 481, 3: 330, 4: 473, 5: 237, 6: 338,
          7: 406, 8: 0, 9: 166, 10: 475, 11: 348, 12: 421 }


class Agent():

    def __init__(self, id, certainty, receptiveness):
        self.id = id
        self.c = certainty
        self.r = receptiveness
        self.hist = []
        self.change = None

    def listen_to(self, speaker):
        diff = speaker.c - self.c
        diff /= 1 if diff > 0 else 10
        self.c += self.r * diff


def create_agents(rs):
    agents = [Agent(i, 0., rs[i]) for i in range(1, 13)]
    agents[8 - 1] = Agent(8, 1., 0.)
    return agents


def run(agents, simple_viz):
    for i in range(len(speakers)):
        speaker_id = speakers[i] - 1
        for a in agents:
            if speaker_id == a.id:
                a.hist.append(a.c if a.change is None or not simple_viz else 1)
                continue  # the speaker is not influenced by himself

            a.listen_to(agents[speaker_id])
            a.hist.append(a.c if a.change is None or not simple_viz else 1)
            if a.change is None and a.c > 0.7:
                a.change = i


rs = {  # receptiveness of the agents
    1: 0.009776229897073328,
    2: 0.008688596995049883,
    3: 0.014895304785649453,
    4: 0.008574696355808242,
    5: 0.026863331602626224,
    6: 0.014865976350887740,
    7: 0.028335545435983753,
    8: 0.0,
    9: 0.050877543350092536,
    10: 0.008048135957302294,
    11: 0.014370069735055852,
    12: 0.009796317235257474,
}


# optimize the receptiveness of the agents to match the script
def optimize_rs(verbosity):
    length = 1000
    for i in range(length):
        agents = create_agents(rs)
        run(agents, False)

        if verbosity:
            print("changes (movie):", ", ".join(str(changes[a.id]) for a in agents))
            print("changes (model):", ", ".join(str(a.change) for a in agents))
            print("receptivenesses:", ", ".join(str(a.r) for a in agents))
            print()
        else:
            print("\r" + str(i) + "/" + str(length), end="")

        for a in agents:
            rs[a.id] *= 1.001 if (a.change is None or a.change > changes[a.id]) else 0.999


if __name__ == "__main__":
    # argparse definitions
    parser = argparse.ArgumentParser()
    parser.add_argument('-o', action='store_true', help='Optimize receptiveness of the agents')
    parser.add_argument('-v', action='store_true', help='Verbosity of the optimizer')
    parser.add_argument('-s', action='store_true', help='Simplified visualization')

    # argparse values
    args = parser.parse_args()
    optimizer = args.o
    verbosity = args.v
    simple_viz = args.s

    # optimization of the receptiveness of the agents (if need be)
    if optimizer:
        optimize_rs(verbosity)

    # final plot
    agents = create_agents(rs)
    run(agents, simple_viz)
    xs = range(len(speakers))
    for a in agents:
        plt.plot(xs, a.hist, label=("agent " + str(a.id)))
    plt.plot(xs, [.7 for _ in range(len(speakers))])  # to visualoize the threshold
    plt.legend(loc="lower right")
    plt.show()
