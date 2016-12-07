import csv


agents = {}
with open('results') as f:
    rows = csv.reader(f)
    for row in rows:
        agent, certainty = row
        if agent in agents :
            agents[agent].append(certainty)
        else:
             agents[agent] = [certainty]

maxlen = 0
for agent in agents:
    # print (agent, len(agents[agent]))
    if len(agents[agent]) > maxlen :
        maxlen = len(agents[agent])

for agent in agents:
        for i in range(maxlen - len(agents[agent])):
            agents[agent].append(agents[agent][-1])

for agent in agents:
    print (agent, len(agents[agent]))


import matplotlib.pyplot as plt

t = range(maxlen)
for agent in agents:
    s = agents[agent]
    plt.plot(t, s, label=agent)

plt.xlabel('Time Steps')
plt.ylabel('Certainty')
plt.title('12 Angry Agents')
plt.grid(True)
plt.savefig("test.png")
plt.legend( loc='lower right')
plt.show()

