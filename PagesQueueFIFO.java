/**
 * Yuval Hering 204537955
 * Maya Oxenhandler 302257563
 */
public class PagesQueueFIFO {

    private int head;
    private int numOfElements;
    private Page[] pages;

    public PagesQueueFIFO(int memorySize){
        this.head=0;
        this.numOfElements=0;
        this.pages=new Page[memorySize];
    }


    //adds page to the queue changes the relevant indexes O(1)
    public void enqueue (Page page){
        pages[head]=page;
        if(head==pages.length-1)
            head=0;
        else
            head++;
        numOfElements++;
    }

    //dequeue page, returns it and change relevant indexes O(1)
    public Page dequeue(){
        Page output=pages[head];
        pages[head]=null;
        numOfElements--;
        return output;
    }

    //returns page by index- without dequeue it (only for inner use) O(1)
    public Page get(int key){
        return pages[key];
    }

    public int getHead(){
        return head;
    }


}
