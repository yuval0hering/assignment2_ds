/**
 * Yuval Hering 204537955
 * Maya Oxenhandler 302257563
 */
public class PagesQueueLRU {
    private Page[] pages;
    int toDequeue;
    int last;
    int numOfElements;

    public PagesQueueLRU(int SecmemorySize){
        pages=new Page[SecmemorySize];
        numOfElements=0;
    }

    public void setSpecials(int mainMemorySize){ //set special indexes, after initial the queue.
        toDequeue=0;
        last=mainMemorySize-1;
    }

    //adds page to the queue changes the relevant indexes O(1)
    public void enqueue(Page page){
        pages[page.key]=page;
        page.next=-1;
        int currLast=last;
        last=page.key;
        if (toDequeue==-1){
            toDequeue=page.key;
        }
        if(last==-1){
            last=page.key;
        }
        if(last!=toDequeue){
            page.prev=currLast;
            getPage(page.prev).next=page.key;
        }
        else{
            page.prev=-1;
        }
        numOfElements++;

    }
    //dequeue page, returns it and change relevant indexes O(1)
    public Page dequeue(){
        Page output;
        if(toDequeue!=-1)
            output=pages[toDequeue];
        else
            output= pages[0];
        pages[output.key]=null;
        if(output.next!=-1)
            getPage(output.next).prev=-1;
        if(toDequeue!=last)
            toDequeue=output.next;
        else{
            toDequeue=-1;
            last=-1;
        }
        numOfElements--;
        return output;
    }

    //returns page by index- without dequeue it (only for inner use) O(1)
    public Page getPage(int key){
        return pages[key];
    }

    //changes the toDequeueu index of the queue O(1)
    public void setToDequeue(int key){
        toDequeue=key;
    }


}
