import sys
from py2neo import Graph, Node, Relationship
import json
import requests

'''
    Run this script with the argument -d to delete all
    nodes and edges from database.
'''

graph = Graph("http://neo4j:movieme@localhost:7474/db/data")

def insertNode(data):
    moviePropertiesDict = dict()

    try:
        moviePropertiesDict["title"] = data["Title"].encode("utf-8")
        moviePropertiesDict["plot"] = data["Plot"].encode("utf-8")
        moviePropertiesDict["rated"] = data["Rated"].encode("utf-8")
        moviePropertiesDict["rating"] = data["imdbRating"].encode("utf-8")
        moviePropertiesDict["writer"] = data["Writer"].encode("utf-8")
        moviePropertiesDict["director"] = data["Director"].encode("utf-8")
        moviePropertiesDict["released"] = data["Released"].encode("utf-8")
        moviePropertiesDict["actors"] = data["Actors"].encode("utf-8")
        moviePropertiesDict["genre"] = data["Genre"].encode("utf-8")
        moviePropertiesDict["runtime"] = data["Runtime"].encode("utf-8")
        moviePropertiesDict["poster"] = data["Poster"].encode("utf-8")
        moviePropertiesDict["imdbid"] = data["imdbID"].encode("utf-8")
    except:
        return

    movieNode = Node("Movie", **moviePropertiesDict)

    graph.create(movieNode)

def movieRequest(baseURL, idList):
    for movieId in idList:
        parameters = {"i" : movieId}
        try:
            response = requests.get(baseURL, params=parameters)
            data = response.json()
        except:
            continue

        # print data

        insertNode(data)

    # parameters = {"i" : idList[0]}
    #
    # response = requests.get(baseURL, params=parameters)
    #
    # data = response.json()

    # print data

def getIdList():
    idFile = open("idFile.txt", "r")

    lines = idFile.readlines()

    idList = list()

    for line in lines:
        line = line.rstrip("\n")
        idList.append(line)

    return idList

def main():
    graph.delete_all()

    baseURL = "http://www.omdbapi.com/"

    idList = getIdList()

    movieRequest(baseURL, idList)

main()
