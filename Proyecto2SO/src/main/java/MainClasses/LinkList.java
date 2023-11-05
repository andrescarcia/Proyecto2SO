/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClasses;

/**
 *
 * @author Sebastián
 */
public class LinkList {
    private Node lFirst;
    private Node lLast;
    private int lSize = 0;

    /**
     * @return the lFirst
     */
    public Node getlFirst() {
        return lFirst;
    }

    /**
     * @param lFirst the lFirst to set
     */
    public void setlFirst(Node lFirst) {
        this.lFirst = lFirst;
    }

    /**
     * @return the lLast
     */
    public Node getlLast() {
        return lLast;
    }

    /**
     * @param lLast the lLast to set
     */
    public void setlLast(Node lLast) {
        this.lLast = lLast;
    }

    /**
     * @return the lSize
     */
    public int getlSize() {
        return lSize;
    }

    /**
     * @param lSize the lSize to set
     */
    public void setlSize(int lSize) {
        this.lSize = lSize;
    }
 
    
    public LinkList(){
        this.lFirst = null;
        this.lLast = null;
        this.lSize = 0;
    }
    
    public boolean isEmpty(){
        return this.lFirst == null;
    }
    
    
    public void insertStart(Node data){
    if(isEmpty()){
        this.lFirst = data;
        this.lLast = data;
    }else{
        data.setpNext(this.lFirst);
        this.lFirst = data;
        }
    this.lSize++;
    }
    
    public void insertEnd(Node data){
        if(isEmpty()){
            this.lFirst = data;
            this.lLast = data;
        }else{
            this.lLast.setpNext(data);
            this.lLast = data;
        }
        this.lSize++;
        }
    
    public Node delFirst(){
        if(isEmpty()){
        return null;
        }else{
            Node aux = this.lFirst;
            this.lFirst = aux.getpNext();
            this.lSize--;
            return aux;
        }
    }
    
    public Node delLast(){
        if(isEmpty()){
            return null;
        }else{
            Node aux = this.lFirst;
            Node previousLast = this.lLast;
            for(int i = 0; i < this.lSize - 1; i++){
                aux = aux.getpNext();
            }
            aux.setpNext(null);
            this.lLast = aux;
            this.lSize--;
            return previousLast;
        }   
    }
    
    public String printList(){
        if(isEmpty()){
            return null;
        }else{
            String fullList = "";
            Node aux = lFirst;
            for(int i = 0; i < lSize; i++){
                fullList += aux.getData() + "\n";
                aux = aux.getpNext();
                }
            return fullList;
            }
    }
    
}
