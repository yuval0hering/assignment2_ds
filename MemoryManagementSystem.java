/**
 * Yuval Hering 204537955
 * Maya Oxenhandler 302257563
 */
import java.util.Arrays;


public class MemoryManagementSystem{
	public String[] secondaryMemory;
	private boolean useLRU;
	private PagesQueueFIFO FIFOPagesQueue;
	private PagesQueueLRU LRUPagesQueue;
	private int[] locationInMain;
	 
	public MemoryManagementSystem(int mainMemorySize, int secondaryMemorySize, boolean useLRU) {
		this.secondaryMemory = new String[secondaryMemorySize];
		this.useLRU = useLRU;
		for (int i = 0; i < secondaryMemorySize; i++) { //initials the second memory O(m)
			secondaryMemory[i] = "";
		}

		if (!this.useLRU) {
			this.FIFOPagesQueue = new PagesQueueFIFO(mainMemorySize);//initial the FIFO queue-main memory O(n)
			this.locationInMain = new int[secondaryMemorySize];
			for (int i = 0; i < mainMemorySize; i++) {
				Page page = new Page(secondaryMemory[i], i);
				FIFOPagesQueue.enqueue(page);
				locationInMain[i] = i;
			}
			//initials the rest of the boolean array O(m-n)=O(m)
			for (int i = mainMemorySize; i < secondaryMemorySize; i++) {
				locationInMain[i] = -1;
			}
		}
		else{
			this.LRUPagesQueue=new PagesQueueLRU(secondaryMemorySize);
			for(int i=0;i<mainMemorySize;i++){// initials the LRU queue O(n)
				Page page=new Page(secondaryMemory[i],i);
				LRUPagesQueue.enqueue(page);
			}
			LRUPagesQueue.setSpecials(mainMemorySize);

		}
	}
	@Override
	public String toString() {
		return "secondaryMemory=" + Arrays.toString(secondaryMemory);
	}
	
	public String read(int index) {
		if (isExist(index)) { //checks if the page is in the main memory. if it does it returns it from the chosen queue.
			if (!useLRU) {
				return FIFOPagesQueue.get(locationInMain[index]).getPageContent();
			}
			else{
				String pageContent=LRUPagesQueue.getPage(index).getPageContent();
				LRUPagesQueue.getPage(index).EditIndexes(LRUPagesQueue);
				return pageContent;
			}
		}
		else {//if the page isn't in the main memory it pulls it, replaces and returns it with the "oldest"
			// page by the chosen method.
			Page toMain = new Page(secondaryMemory[index], index);
			if (!useLRU) {
				Page toSecondary = FIFOPagesQueue.dequeue();
				locationInMain[toSecondary.key] = -1;
				int currentHead = FIFOPagesQueue.getHead();
				FIFOPagesQueue.enqueue(toMain);
				locationInMain[toMain.key] = currentHead;
				secondaryMemory[toSecondary.key] = toSecondary.getPageContent();
				return toMain.getPageContent();

			}
			else{
				Page toSecondary=LRUPagesQueue.dequeue();
				LRUPagesQueue.enqueue(toMain);
				secondaryMemory[toSecondary.key]=toSecondary.getPageContent();
				return toMain.getPageContent();
			}
		}

	}

	public void write(int index, char c) {
		if (isExist(index)) {//checks if the page is in the main memory. if it does it edits and
			// returns in the chosen queue.
			if (!useLRU) {
				FIFOPagesQueue.get(locationInMain[index]).EditContent(c);
			}
			else{
				LRUPagesQueue.getPage(index).EditContent(c);
				LRUPagesQueue.getPage(index).EditIndexes(LRUPagesQueue);
			}
		}

		else { //if the page isn't in the main memory it pulls it, replaces and edits it with the "oldest"
			// page by the chosen method.
			Page toMain = new Page(secondaryMemory[index], index);
			if (!useLRU) {
				Page toSecondary = FIFOPagesQueue.dequeue();
				locationInMain[toSecondary.key] = -1;
				int currentHead = FIFOPagesQueue.getHead();
				FIFOPagesQueue.enqueue(toMain);
				locationInMain[toMain.key] = currentHead;
				toMain.EditContent(c);
				secondaryMemory[toSecondary.key] = toSecondary.getPageContent();

			}
			else{
				Page toSecondary=LRUPagesQueue.dequeue();
				LRUPagesQueue.enqueue(toMain);
				toMain.EditContent(c);
				secondaryMemory[toSecondary.key]=toSecondary.getPageContent();
			}
		}

	}
	//get page from secondary memory (O(1))
	public String getSecondary(int key){
		return secondaryMemory[key];
	}

	//checks if the page appears in the main memory- O(1)
	public boolean isExist (int key){
		if (!useLRU)
			return locationInMain[key]!=-1;
		else
			return LRUPagesQueue.getPage(key)!=null;

	}
}
