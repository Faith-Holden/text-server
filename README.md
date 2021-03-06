# text-server
My solution for Chapter 11 Exercise 3 of “Introduction to Programming Using Java”.

Note: This server works with the text-client program.

Problem Description:
For this exercise, you will write a network server program. The program is a simple file
server that makes a collection of files available for transmission to clients. When the server
starts up, it needs to know the name of the directory that contains the collection of files.
This information can be provided as a command-line argument. You can assume that the
directory contains only regular files (that is, it does not contain any sub-directories). You
can also assume that all the files are text files.
When a client connects to the server, the server first reads a one-line command from
the client. The command can be the string “INDEX”. In this case, the server responds by
sending a list of names of all the files that are available on the server. Or the command can
be of the form “GET <filename>”, where <filename> is a file name. The server checks
whether the requested file actually exists. If so, it first sends the word “OK” as a message
to the client. Then it sends the contents of the file and closes the connection. Otherwise,
it sends a line beginning with the word “ERROR” to the client and closes the connection.
(The error response can include an error message on the rest of the line.)
Your program should use a subroutine to handle each request that the server receives.
It should not stop after handling one request; it should remain open and continue to
accept new requests. See the DirectoryList example in Subsection 11.2.2 for help with the
problem of getting the list of files in the directory.
