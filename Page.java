/**
 * Yuval Hering 204537955
 * Maya Oxenhandler 302257563
 */
public class Page {
    private String pageContent;
    int key;
    int next;
    int prev;
    public Page(String page, int key){
        this.key=key;
        this.pageContent=page;
        this.prev=-1;
        this.next=-1;

    }
    //add the char toe the page content O(1)
    public void EditContent(char c){
        this.pageContent=this.pageContent+c;
    }
    //after read/ write to page in the LRU queue, the indexes need to be change O(1)
    public void EditIndexes(PagesQueueLRU pages){
        int tempNext= this.next;
        int tempPrev=this.prev;
        this.next=-1;
        if(this.key != pages.last & this.key != pages.toDequeue){
            this.prev=pages.last;
            pages.getPage(tempNext).prev=tempPrev;
            pages.getPage(tempPrev).next=tempNext;
            pages.getPage(pages.last).next=this.key;
        }
        else if (this.key!=pages.last & this.key==pages.toDequeue){
            this.prev=pages.last;
            pages.getPage(pages.last).next=this.key;
            pages.getPage(tempNext).prev=-1;
            pages.setToDequeue(tempNext);
        }
        pages.last=this.key;
    }

    //returns the page content O(1).
    public String getPageContent(){
        return this.pageContent;
    }
}

