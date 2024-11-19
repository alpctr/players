This repository is a solution for an interview request as follows: 


Please create a simple Player class that represents a Player that can communicate with other players.

The requirements are as follows:

1. create 2 Player instances in Java (Java 8+, 8 preferred) 
2. one of the players should send a message to second player (let's call this player "initiator"). The definition of "message sending" is up to you.
3. when a player receives a message, it should reply with a message that contains the received message concatenated with the value of a counter holding the number of messages this player already sent.
4. finalize the program (gracefully) after the initiator sent 10 messages and received back 10 messages (stop condition)
5. both players should run in the same java process (different threads)
6. document for every class the responsibilities it has.
7. additional challenge 1 (only a nice to have) - have Players running in different processes
8. additional challenge 2 (only a nice to have) - have one of the Players written with Python 3.x

