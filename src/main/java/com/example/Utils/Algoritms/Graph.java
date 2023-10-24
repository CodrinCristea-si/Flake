package com.example.Utils.Algoritms;

import com.example.Domain.Relationship;
import com.example.Repository.RelationshipRepo;

import java.util.*;

public class Graph {
    int numberOfVertices;
    int numberOfEdges;
    List<Pair<Integer,Integer>> listEdges;

    public Graph(int numberOfVertices, int numberOfEdges) {
        this.numberOfVertices = numberOfVertices;
        this.numberOfEdges = numberOfEdges;
        listEdges=new ArrayList<>();
    }

    public Graph() {
        listEdges=new ArrayList<>();
    }

    /**
     * Updates the number of vertices
     * @param numberOfVertices the number to be updated with
     */
    public void setNumberOfVertices(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
    }
    /**
     * Updates the number of edges
     * @param numberOfEdges the number to be updated with
     */
    public void setNumberOfEdges(int numberOfEdges) {
        this.numberOfEdges = numberOfEdges;
    }

    /**
     * Adds an edge to the list
     * @param f the first node of the edge
     * @param s the second node of the edge
     */
    public void addEdge(int f, int s){
        listEdges.add(new Pair<>(f,s));
    }

    /**
     * Removes an edge from the list
     * @param f the first node of the edge
     * @param s the second node of the edge
     */
    public void removeEdge(int f,int s){
        listEdges.remove(new Pair<>(f,s));
    }

    /**
     * Gives the number of vertices
     * @return the current number of vertices
     */
    public int getNumberOfVertices() {
        return numberOfVertices;
    }

    /**
     * Gives the number of edges
     * @return the current number of edges
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * Gives the list of the edges
     * @return a list of pairs with the first and the second node of an edge
     */
    public List<Pair<Integer, Integer>> getListEdges() {
        return listEdges;
    }

    /**
     * Gives the number of communities from a repository
     * @param repo the network to be searched
     * @return the number of communities
     */
    public int numberOfCommunities(RelationshipRepo repo){
        convertRepo(repo);
        return numberOfConexComponents();
    }

    /**
     * Gives the most sociable network from repository
     * @param repo the network to be searched
     * @return a list of usernames tahat take part of that community
     */
    public List<String> theMostSociableCommunity(RelationshipRepo repo){
        Map<String,Integer> listOfVer=convertRepo(repo);
        List<Integer> listOfParticipants=theLongestElementeryRoad();
        return convertIntToUsername(listOfParticipants,listOfVer);
    }

    /**
     * Gives the corespondent a list of strings to a list of int
     * @param listParticipants the list to be to be converted
     * @param listOfVer the list to be searched
     * @return the corespondent a list of strings to a list of int
     */
    public List<String>convertIntToUsername(List<Integer>listParticipants,Map<String,Integer>listOfVer){
        List<String>list= new ArrayList<>();
        for(Integer elem:listParticipants){
            for(Map.Entry<String,Integer> el: listOfVer.entrySet()){
                if(el.getValue().equals(elem))
                    list.add(el.getKey());
            }
        }
        return list;
    }

    /**
     * Search for the longest elementery road from a graph
     * @return a list of nodes that makes the path of that road
     */
    public List<Integer> theLongestElementeryRoad(){
        int maxim=0;
        List<Integer> listParticipants= new ArrayList<>();
        for(Integer i=0;i<=numberOfVertices;i++)
            listParticipants.add(0);
        Map<Integer,List<Integer>> adiacencyList=getAdiacencyList();
        for (int i=1;i<=numberOfVertices;i++){
            List<Integer> listColours= new ArrayList<>(numberOfVertices);
            List<Integer> listPath= new ArrayList<>(numberOfVertices);
            List<Integer> listParent= new ArrayList<>(numberOfVertices);
            for(Integer j=0;j<=numberOfVertices;j++) {
                listColours.add(0);
                listPath.add(0);
                listParent.add(0);
            }
            if(adiacencyList.get((Integer) i)!=null && adiacencyList.get((Integer) i).size()==1){
                bfs((Integer)i,adiacencyList,listColours,listPath,listParent);
                int max=Collections.max(listPath);
                if(max>maxim){
                    maxim=max;
                    //System.out.println(maxim);
                    listParticipants=listColours;
                    //System.out.println(listParticipants);
                    //System.out.println(listParent);
                }
            }
        }
        List<Integer> listOfElems= new ArrayList<>();
        for (Integer i=0;i<listParticipants.size();i++){
            if(listParticipants.get(i)==1){
                listOfElems.add(i);
            }
        }
        return listOfElems;
    }

    /**
     * Does bfs in a graph
     * @param node the node from where the search is started
     * @param adiacencyList the adiacency list of all nodes of the graph
     * @param listColours list of visited nodes
     * @param listPath list of distances from the start node to the current node
     */
    public void bfs(Integer node,Map<Integer,List<Integer>> adiacencyList,List<Integer> listColours,List<Integer> listPath){
        Queue<Integer> queue= new LinkedList<>();
        queue.add(node);
        listPath.set(node,0);
        while(!queue.isEmpty()){
            Integer currentNode=queue.remove();
            for(Integer neighb: adiacencyList.get(currentNode)){
                if(listColours.get(neighb)==0){
                    queue.add(neighb);
                }
                if(listPath.get(currentNode)+1>listPath.get(neighb)) listPath.set(neighb,listPath.get(currentNode)+1);
            }
            listColours.set(currentNode,1);
        }
    }

    public void bfs(Integer node,Map<Integer,List<Integer>> adiacencyList,List<Integer> listColours,List<Integer> listPath,List<Integer>listParent){
        Queue<Integer> queue= new LinkedList<>();
        queue.add(node);
        listPath.set(node,0);
        while(!queue.isEmpty()){
            Integer currentNode=queue.remove();
            for(Integer neighb: adiacencyList.get(currentNode)){
                if(listColours.get(neighb)==0){
                    queue.add(neighb);
                }
                if(listPath.get(currentNode)+1>listPath.get(neighb)){
                    listPath.set(neighb,listPath.get(currentNode)+1);
                    if(listColours.get(neighb)==0)listParent.set(neighb,currentNode);
                }
            }
            listColours.set(currentNode,1);
        }
    }

    /**
     * Gives the number of the conex components of the graph
     * @return the number of the conex components of the graph
     */
    public int numberOfConexComponents(){
        Map<Integer,List<Integer>> adiacencyList=getAdiacencyList();
        List<Integer> listVisited= new ArrayList<>();
        for(Integer i=0;i<=numberOfVertices;i++)
            listVisited.add(null);
        int numberOfComponents=0;
        for(int i=1;i<=numberOfVertices;i++){
            if(listVisited.get(i)==null){
                numberOfComponents++;
                dfs((Integer) i,listVisited,adiacencyList);
            }
        }
        return numberOfComponents;
    }

    /**
     * Does dfs on a graph
     * @param node the node from where the search is started
     * @param listVisited list of visited nodes
     * @param adiacencyList the adiacency list of all nodes of the graph
     */
    public void dfs(Integer node,List<Integer> listVisited,Map<Integer,List<Integer>> adiacencyList){
        if(listVisited.get(node)==null){
            listVisited.set(node,1);
            if(adiacencyList.get(node)!=null)
                for(Integer el:adiacencyList.get(node)){
                    dfs(el,listVisited,adiacencyList);
                }
        }
    }

    /**
     * Gives the adiacency list of all nodes from a graph
     * @return a map that contains the node and the list of neighbours of that node
     */
    public Map<Integer,List<Integer>> getAdiacencyList(){
        Map<Integer,List<Integer>> adLs=new HashMap<>();
        for(Pair el: listEdges){
            if(adLs.get(el.getFirst())==null)
                adLs.put((Integer) el.getFirst(),new ArrayList<>());
            if(adLs.get(el.getSecond())==null)
                adLs.put((Integer) el.getSecond(),new ArrayList<>());
            adLs.get(el.getFirst()).add((Integer) el.getSecond());
            adLs.get(el.getSecond()).add((Integer) el.getFirst());
        }
        return adLs;
    }

    /**
     * Gives to each username a corespondent id
     * @param repo the repository to extract the usernames
     * @return a map that contains the username and the corespondent id
     */
    public Map<String,Integer> convertRepo(RelationshipRepo repo){
        Map<String,Integer> listOfVer=convertUsernameToInt(repo);
        fillEdges(repo,listOfVer);
        return listOfVer;
    }

    /**
     * Gives the corespondent a list of strings to a list of int
     * @param repo the repository to extract the usernames
     * @return the corespondent a list of strings to a list of int
     */
    public Map<String,Integer> convertUsernameToInt(RelationshipRepo repo){
        Map<String,Integer> listOfVer=new HashMap();
        for(Relationship rel: repo.getAll()){
            if(listOfVer.get(rel.getFirstUserName())==null){
                listOfVer.put(rel.getFirstUserName(), listOfVer.size()+1);
            }
            if(listOfVer.get(rel.getSecondUserName())==null){
                listOfVer.put(rel.getSecondUserName(), listOfVer.size()+1);
            }
        }
        return listOfVer;
    }

    /**
     * fills the list of edges with the relationships from the repo
     * @param repo the repository to check the usernames
     * @param listOfVer map that contains the username and the corespondent id
     */
    public void fillEdges(RelationshipRepo repo, Map<String,Integer> listOfVer){
        for(Relationship rel: repo.getAll()){
            addEdge(listOfVer.get(rel.getFirstUserName()),listOfVer.get(rel.getSecondUserName()));
        }
    }
}
